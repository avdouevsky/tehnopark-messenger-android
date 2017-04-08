package com.mshvdvskgmail.technoparkmessenger.network;

import android.util.Log;

import com.google.gson.Gson;
import com.mshvdvskgmail.technoparkmessenger.events.DataLoadEvent;
import com.mshvdvskgmail.technoparkmessenger.events.MessageEvent;
import com.mshvdvskgmail.technoparkmessenger.network.model.Message;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.ExceptionHandler;
import com.rabbitmq.client.Recoverable;
import com.rabbitmq.client.RecoverableChannel;
import com.rabbitmq.client.RecoverableConnection;
import com.rabbitmq.client.RecoveryListener;
import com.rabbitmq.client.ShutdownListener;
import com.rabbitmq.client.ShutdownSignalException;
import com.rabbitmq.client.TopologyRecoveryException;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;

import rx.Observable;
import rx.Subscriber;
import rx.subjects.Subject;

/**
 * Created by andrey on 24.03.2017.
 *
 * Класс для работы с RabbitMQ, просто обертка для основных методов
 */
public class RabbitMQ {
    private final static String TAG = RabbitMQ.class.toString();

    private RecoverableConnection connection;
    private RecoverableChannel channel;

    private Gson gson = new Gson();

    public Observable<RabbitMQ> connect(final String uri){
        return Observable.create(new Observable.OnSubscribe<RabbitMQ>() {
            @Override
            public void call(Subscriber<? super RabbitMQ> subscriber) {
                try{
                    try {
                        if(connection != null && connection.isOpen()) connection.close();
                    } catch (IOException e) {}

                    ConnectionFactory factory = new ConnectionFactory();
                    factory.setUri(uri);
                    factory.setAutomaticRecoveryEnabled(true);
                    factory.setRequestedHeartbeat(40);  //1/2 = 20s
                    factory.setTopologyRecoveryEnabled(true);
                    factory.setExceptionHandler(new ExceptionHandler() {
                        @Override
                        public void handleUnexpectedConnectionDriverException(Connection conn, Throwable exception) {
                            Log.w(TAG, "handleUnexpectedConnectionDriverException " + exception.getMessage());
                            //TODO
                        }

                        @Override
                        public void handleReturnListenerException(Channel channel, Throwable exception) {
                            Log.w(TAG, "handleReturnListenerException " + exception.getMessage());
                            //TODO
                        }

                        @Override
                        public void handleFlowListenerException(Channel channel, Throwable exception) {
                            Log.w(TAG, "handleFlowListenerException " + exception.getMessage());
                            //TODO
                        }

                        @Override
                        public void handleConfirmListenerException(Channel channel, Throwable exception) {
                            Log.w(TAG, "handleConfirmListenerException " + exception.getMessage());
                            //TODO
                        }

                        @Override
                        public void handleBlockedListenerException(Connection connection, Throwable exception) {
                            Log.w(TAG, "handleBlockedListenerException " + exception.getMessage());
                            //TODO
                        }

                        @Override
                        public void handleConsumerException(Channel channel, Throwable exception, Consumer consumer, String consumerTag, String methodName) {
                            Log.w(TAG, "handleConsumerException " + exception.getMessage());
                            //TODO
                        }

                        @Override
                        public void handleConnectionRecoveryException(Connection conn, Throwable exception) {
                            Log.w(TAG, "handleConnectionRecoveryException " + exception.getMessage());
                            //TODO
                        }

                        @Override
                        public void handleChannelRecoveryException(Channel ch, Throwable exception) {
                            Log.w(TAG, "handleChannelRecoveryException " + exception.getMessage());
                            //TODO
                        }

                        @Override
                        public void handleTopologyRecoveryException(Connection conn, Channel ch, TopologyRecoveryException exception) {
                            Log.w(TAG, "handleTopologyRecoveryException " + exception.getMessage());
                            //TODO
                        }
                    });

                    connection = (RecoverableConnection) factory.newConnection();
                    connection.addRecoveryListener(new RecoveryListener() {
                        @Override
                        public void handleRecovery(Recoverable recoverable) {
                            Log.v(TAG, "connection handleRecovery");
                            //TODO
                        }

                        @Override
                        public void handleRecoveryStarted(Recoverable recoverable) {
                            Log.v(TAG, "connection handleRecoveryStarted");
                            //TODO
                        }
                    });
                    connection.addShutdownListener(new ShutdownListener() {
                        @Override
                        public void shutdownCompleted(ShutdownSignalException cause) {
                            Log.w(TAG, "connection shutdownCompleted: " + cause.getMessage());
                            //TODO
                        }
                    });
                    channel = (RecoverableChannel) connection.createChannel();
                    channel.addRecoveryListener(new RecoveryListener() {
                        @Override
                        public void handleRecovery(Recoverable recoverable) {
                            Log.v(TAG, "channel handleRecovery");
                            //TODO
                        }

                        @Override
                        public void handleRecoveryStarted(Recoverable recoverable) {
                            Log.v(TAG, "channel handleRecoveryStarted");
                            //TODO
                        }
                    });
                    channel.addShutdownListener(new ShutdownListener() {
                        @Override
                        public void shutdownCompleted(ShutdownSignalException cause) {
                            Log.w(TAG, "channel shutdownCompleted: " + cause.getMessage());
                            //TODO
                        }
                    });

                    Log.i(TAG, "connected");

                    subscriber.onNext(RabbitMQ.this);
                    subscriber.onCompleted();
                }catch (Exception e){
                    subscriber.onError(e);
                }
            }
        });
    }

//    @Deprecated
//    public Observable<RabbitMQ> basicConsume(final String queue, final int prefetchCount){
//        return Observable.create(new Observable.OnSubscribe<RabbitMQ>() {
//            @Override
//            public void call(Subscriber<? super RabbitMQ> subscriber) {
//                try{
//                    channel.basicQos(prefetchCount);
//                    channel.basicConsume(queue, true, new MessageConsumer(channel));
//                    subscriber.onNext(RabbitMQ.this);
//                    subscriber.onCompleted();
//                }catch (Exception e){
//                    subscriber.onError(e);
//                }
//            }
//        });
//    }

    /**
     *
     * @param queue имя очереди для прослушки
     * @param prefetchCount частота выборки без ack
     * @param consumer слушает постоянно все входящие сообщения.
     * @param <T>
     * @return
     */
    public <T> Observable<RabbitMQ> basicConsume(final String queue, final int prefetchCount, final RxConsumer<T> consumer){
        return Observable.create(new Observable.OnSubscribe<RabbitMQ>() {
            @Override
            public void call(Subscriber<? super RabbitMQ> subscriber) {
                try{
                    channel.basicQos(prefetchCount);
                    channel.basicConsume(queue, true, consumer);
                    subscriber.onNext(RabbitMQ.this);
                    subscriber.onCompleted();
                }catch (Exception e){
                    subscriber.onError(e);
                }
            }
        });
    }

    public Observable<RabbitMQ> basicPublish(final String exchange, final String route, final String message, final String queue){
        return Observable.create(new Observable.OnSubscribe<RabbitMQ>() {
            @Override
            public void call(Subscriber<? super RabbitMQ> subscriber) {
                Log.d(TAG, "basicPublish");

                AMQP.BasicProperties properties = new AMQP.BasicProperties.Builder()
                        .replyTo(queue)
                        .build();
                subscriber.onNext(RabbitMQ.this);
                subscriber.onCompleted();
                try {
                    channel.basicPublish(exchange, route, properties, message.getBytes());
                } catch (IOException e) {
                    subscriber.onError(e);
                }
            }
        });
    }

    public boolean isConnected() {
        return connection != null && connection.isOpen() && channel != null && channel.isOpen();
    }

    abstract static public class RxConsumer<T> extends DefaultConsumer {
        private Subject<T, T> subject;

        public RxConsumer(Subject<T, T> subject) {
            super(null);
            this.subject = subject;
        }

        abstract protected T convert(byte[] body);

        @Override
        public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body){
            try {
                Gson gson = new Gson();
                String msg = new String(body, "UTF-8");
                Log.d(TAG, msg);
                Message message = gson.fromJson(msg, Message.class);
                Log.d(TAG, "???");
                EventBus.getDefault().postSticky(new MessageEvent(message));
//                EventBus.getDefault().postSticky(new DataLoadEvent("test"));

                subject.onNext(convert(body));
            }catch (Exception e){
                subject.onError(e);
            } finally {
                Log.d(TAG, " [x] Done.");
            }
        }

        @Override
        public void handleShutdownSignal(String consumerTag, ShutdownSignalException sig) {
            subject.onError(new RuntimeException(sig.getMessage()));
        }
    }

    @Deprecated
    private class MessageConsumer extends DefaultConsumer {
        public MessageConsumer(Channel channel) {
            super(channel);
        }

        @Override
        public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
            String msg = new String(body, "UTF-8");
            Log.d(TAG, msg);
            Message message = gson.fromJson(msg, Message.class);
            Log.d(TAG, "!!!");
            EventBus.getDefault().post(new MessageEvent(message));
            try {
                //TODO do somethinf
            } catch (Exception e){
                //todo send error
            } finally {
                Log.d(TAG, " [x] Done.");
            }
        }
    }
}