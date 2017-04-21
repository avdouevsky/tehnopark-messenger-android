package com.mshvdvskgmail.technoparkmessenger.network;


import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.mshvdvskgmail.technoparkmessenger.events.ErrorEvent;
import com.mshvdvskgmail.technoparkmessenger.network.model.Attachment;
import com.mshvdvskgmail.technoparkmessenger.network.model.Message;
import com.mshvdvskgmail.technoparkmessenger.network.model.Token;
import com.mshvdvskgmail.technoparkmessenger.network.model.User;

import org.greenrobot.eventbus.EventBus;

import java.io.UnsupportedEncodingException;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;
import rx.subjects.Subject;
//import su.bnet.rmqchat.model.Attachment;
//import su.bnet.rmqchat.model.Message;
//import su.bnet.rmqchat.model.Token;
//import su.bnet.rmqchat.model.User;

/**
 * Created by andrey on 24.03.2017.
 *
 * Клас для работы именно с нашей системой чатов
 */
public class RMQChat {
    private final static String TAG = RMQChat.class.toString();

    private RabbitMQ rabbitMQ = new RabbitMQ();
    private String uri;
    private String queue;
    private Gson gson = new Gson();
    private int prefetchCount = 10;
    private User user;

    private IMessage listener;

    private Subject<String, String> subject;
    private Subscription subscription;

    /**
     *
     * @param uri ссылка подключения
     * @param prefetchCount частота выборки
     * @param queue имя очереди клиента
     */
    public RMQChat(String uri, int prefetchCount, String queue) {
        this.uri = uri;
        this.prefetchCount = prefetchCount;
        this.queue = queue;
    }

    /**
     * @param listener Как реагировать не сообщения
     */
    public void setListener(IMessage listener) {
        this.listener = listener;
    }

    /**
     * Сеть в бекграунде, результат в мейне
     * @param <T>
     * @return
     */
    private <T> Observable.Transformer<T, T> setup() {
        return new Observable.Transformer<T, T>() {
            @Override
            public Observable<T> call(Observable<T> tObservable) {
                return tObservable.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    /**
     * Перед выполнением поерации - проверяем подключение
     * @param <T>
     * @return
     */
    private <T> Observable.Transformer<T, T> checkConnection() {
        return new Observable.Transformer<T, T>() {
            @Override
            public Observable<T> call(final Observable<T> tObservable) {
                if(rabbitMQ.isConnected()){
                    return tObservable;
                }else{
                    if(subscription != null) subscription.unsubscribe();    //unsubsctibe if any
                    return connectAndSubscribe()                            //reconnect
                            .flatMap(new Func1<RabbitMQ, Observable<T>>() {
                                @Override
                                public Observable<T> call(RabbitMQ rabbitMQ) {
                                    return tObservable;
                                }
                            });
                }
            }
        };
    }

    /**
     * Подписываемся на нашу очередь
     * @return
     */
    public Observable<RabbitMQ> subscribe(){
        subject = PublishSubject.create();

        subscription = subject.subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {
                Log.d(TAG, "onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                Log.w(TAG, e);
                if(listener != null) listener.onError(e);
            }

            @Override
            public void onNext(String s) {
                try {
                    Message message = gson.fromJson(s, Message.class);
                    if(user != null && message.sender != null && message.getType().equals(Message.Type.DIALOG) && !user.equals(message.sender)){
                        // return status read
                        sendMessageStatus(user.token, user.id, message.room, message.uuid, "delivered", Message.Status.DELIVERED);
                    }
                    if(listener != null) listener.onMessage(message);
                } catch (JsonSyntaxException e) {
                    Log.w(TAG, e.getMessage());
                    e.printStackTrace();
                    //e.printStackTrace();
                }
            }
        });

        return rabbitMQ.basicConsume(queue, prefetchCount, new RabbitMQ.RxConsumer<String>(subject){
            @Override
            protected String convert(byte[] body){
                try {
                    return new String(body, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    return null;
                }
            }
        });
    }

    /**
     * Подключиться, подписаться на очередь
     * @return
     */
    public Observable<RabbitMQ> connectAndSubscribe(){
        return rabbitMQ.connect(uri)
                .flatMap(new Func1<RabbitMQ, Observable<RabbitMQ>>() {
                    @Override
                    public Observable<RabbitMQ> call(RabbitMQ rabbitMQ) {
                        return subscribe();
                    }
                }).compose(this.<RabbitMQ>setup());
    }

    /**
     * Отправить сообщение
     * @param token
     * @param meId
     * @param room_uuid
     * @param text
     * @param localId
     * @param attachments
     * @return
     */
    public Observable<RabbitMQ> sendMessage(Token token, String meId, String room_uuid, String text, String localId, List<Attachment> attachments){
        Message message = new Message();
        message.user_token = token;
        message.local_id = localId;
        message.message = text;
        message.attachments = attachments;

        String route = "dialog." + meId + "." + room_uuid;
        String json = gson.toJson(message);

        return rabbitMQ.basicPublish("conversation.outgoing", route, json, queue).compose(this.<RabbitMQ>setup()).compose(this.<RabbitMQ>checkConnection());

        //basicPublish(, message);
    }

    /**
     * Отправить статус по сообщению
     * @param token
     * @param meId
     * @param room_uuid
     * @param uuid
     * @param localId
     * @param status
     * @return
     */
    public Observable<RabbitMQ> sendMessageStatus(Token token, String meId, String room_uuid, String uuid, String localId, Message.Status status){
        Message message = new Message();
        message.user_token = token;
        message.uuid = uuid;
        message.setStatus(status);
        message.local_id = localId;

        String route = "message-status." + meId + "." + room_uuid;
        String json = gson.toJson(message);

        return rabbitMQ.basicPublish("conversation.outgoing", route, json, queue).compose(this.<RabbitMQ>setup()).compose(this.<RabbitMQ>checkConnection());
    }

    /**
     * Отправить статус пользователя (пользователь х набирает сообщение...)
     * @param token
     * @param meId
     * @param room_uuid
     * @param localId
     * @param status
     * @return
     */
    public Observable<RabbitMQ> sendUserStatus(Token token, String meId, String room_uuid, String localId, String status){
        Message message = new Message();
        message.user_token = token;
        message.local_id = localId;
        message.message = status;

        String route = "user-status." + meId + "." + room_uuid;
        String json = gson.toJson(message);

        return rabbitMQ.basicPublish("conversation.outgoing", route, json, queue).compose(this.<RabbitMQ>setup()).compose(this.<RabbitMQ>checkConnection());
    }

    public static class LogSubscriber<T> extends Subscriber<T>{
        private String name;

        public LogSubscriber(String name) {
            this.name = name;
        }

        @Override
        public void onCompleted() {
            Log.v(TAG, "onCompleted " + name);
        }

        @Override
        public void onError(Throwable e) {
            Log.w(TAG, e);
            EventBus.getDefault().postSticky(new ErrorEvent(e));
        }

        @Override
        public void onNext(T t) {
            Log.v(TAG, "onNext " + name);
        }
    }

    public interface IMessage{
        public void onMessage(Message message);
        public void onError(Throwable throwable);
    }
}