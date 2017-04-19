package com.mshvdvskgmail.technoparkmessenger.services;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

//import com.facebook.AccessToken;
//import com.facebook.FacebookException;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;
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
//import com.vk.sdk.VKAccessToken;
//import com.vk.sdk.VKAccessTokenTracker;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;
import java.util.concurrent.TimeoutException;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import com.mshvdvskgmail.technoparkmessenger.Consts;
import com.mshvdvskgmail.technoparkmessenger.Controller;
import com.mshvdvskgmail.technoparkmessenger.TechnoparkApp;
import com.mshvdvskgmail.technoparkmessenger.Preferences;
//import com.mshvdvskgmail.technoparkmessenger.Social;
import com.mshvdvskgmail.technoparkmessenger.events.MessageEvent;
//import com.mshvdvskgmail.technoparkmessenger.event.WiFiChangedEvent;
//import com.mshvdvskgmail.technoparkmessenger.helpers.ICommand;
import com.mshvdvskgmail.technoparkmessenger.network.REST;
import com.mshvdvskgmail.technoparkmessenger.network.RabbitMQ;
import com.mshvdvskgmail.technoparkmessenger.network.model.Message;
import com.mshvdvskgmail.technoparkmessenger.network.model.Result;
import com.mshvdvskgmail.technoparkmessenger.network.model.Session;
import com.mshvdvskgmail.technoparkmessenger.network.model.Settings;
import com.mshvdvskgmail.technoparkmessenger.network.model.User;
import com.mshvdvskgmail.technoparkmessenger.network.model.WifiToken;

import static android.R.attr.data;

/**
 * Created by andrey on 06.02.2017.
 */
public class RabbitMQService extends Service {
    private final static String TAG = RabbitMQService.class.toString();

    private final IBinder mBinder = new ServiceBinder();

    private final static String DEVICE = "android";
    private final static String DEVICE_ID = "device_id";
    private String deviceId = "";
    private SharedPreferences preferences;

    private RecoverableConnection connection;
    private RecoverableChannel channel;
    private String queue;
    private Gson gson = new Gson();

    private Settings settings;

    private String uri;
    private String unique_id;

    private int prefetchCount = 20;

    private Timer timer;
    private Status status = Status.STARTED;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate ");
//        Log.d(TAG, "onCreate " + TechnoparkApp.getInstance().rand);
        Log.d(TAG, "mBinder "+mBinder);
/*        preferences = this.getSharedPreferences(RabbitMQService.class.getSimpleName(), MODE_PRIVATE);
        deviceId = preferences.getString(DEVICE_ID, null);
        if(deviceId == null){
            deviceId = UUID.randomUUID().toString();
            preferences.edit().putString(DEVICE_ID, deviceId).commit();
        }
        queue = DEVICE + "-" + deviceId;

//        EventBus.getDefault().register(this);
        loadSettings();*/
    }

    public void auth(User user){
        Log.w("Rabbit", "User.queue "+user.queue);

        ConnectionFactory factory = new ConnectionFactory();
        uri = Controller.getInstance().getSettings().getMessagingServerUri();
        Log.w("Rabbit", "Url "+uri);
        try {
            factory.setUri(uri);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
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

        Log.d(TAG, "try connect");
        try {
            connection = (RecoverableConnection) factory.newConnection();
            Log.d(TAG, "1");
        } catch (IOException e) {
            Log.d(TAG, "2");
            e.printStackTrace();
        } catch (TimeoutException e) {
            Log.d(TAG, "3");
            e.printStackTrace();
        }
        Log.d(TAG, "connection "+connection);
        try {
            channel = (RecoverableChannel) connection.createChannel();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "channel "+channel);

//        try {
//            connect();
//            channel.basicQos(prefetchCount);
//            channel.basicConsume(user.queue, true, new MessageConsumer(channel));
//
//        } catch (TimeoutException e) {
//            Log.w(TAG, "timeexception");
//            e.printStackTrace();
//        } catch (NoSuchAlgorithmException e) {
//            Log.w(TAG, "nosuchalgorithm");
//            e.printStackTrace();
//        } catch (KeyManagementException e) {
//            Log.w(TAG, "keymanagment");
//            e.printStackTrace();
//        } catch (URISyntaxException e) {
//            Log.w(TAG, "urisyntax");
//            e.printStackTrace();
//        } catch (IOException e) {
//            Log.w(TAG, "ioexception");
//            e.printStackTrace();
//        }

    }

    private void connect() throws TimeoutException, NoSuchAlgorithmException, KeyManagementException, URISyntaxException {
        Log.d(TAG, "connect");
        uri = Controller.getInstance().getSettings().getMessagingServerUri();
//        unique_id = Controller.getInstance().getAuth().getUser().unique_id;
        Log.d(TAG, "uri "+uri);
        try {
            if(connection != null && connection.isOpen()) connection.close();
        } catch (IOException e) {
            Log.w(TAG, "connect failed");
            e.printStackTrace();
        }

        ConnectionFactory factory = new ConnectionFactory();
        Log.d(TAG, "connection factory "+factory);
        factory.setUri(uri);
        factory.setAutomaticRecoveryEnabled(true);
        factory.setRequestedHeartbeat(40);  //1/2 = 20s
        factory.setTopologyRecoveryEnabled(true);
        factory.setExceptionHandler(new ExceptionHandler() {
            @Override
            public void handleUnexpectedConnectionDriverException(Connection conn, Throwable exception) {
                Log.w(TAG, "handleUnexpectedConnectionDriverException " + exception.getMessage());
            }

            @Override
            public void handleReturnListenerException(Channel channel, Throwable exception) {
                Log.w(TAG, "handleReturnListenerException " + exception.getMessage());
            }

            @Override
            public void handleFlowListenerException(Channel channel, Throwable exception) {
                Log.w(TAG, "handleFlowListenerException " + exception.getMessage());
            }

            @Override
            public void handleConfirmListenerException(Channel channel, Throwable exception) {
                Log.w(TAG, "handleConfirmListenerException " + exception.getMessage());
            }

            @Override
            public void handleBlockedListenerException(Connection connection, Throwable exception) {
                Log.w(TAG, "handleBlockedListenerException " + exception.getMessage());
            }

            @Override
            public void handleConsumerException(Channel channel, Throwable exception, Consumer consumer, String consumerTag, String methodName) {
                Log.w(TAG, "handleConsumerException " + exception.getMessage());
            }

            @Override
            public void handleConnectionRecoveryException(Connection conn, Throwable exception) {
                Log.w(TAG, "handleConnectionRecoveryException " + exception.getMessage());
            }

            @Override
            public void handleChannelRecoveryException(Channel ch, Throwable exception) {
                Log.w(TAG, "handleChannelRecoveryException " + exception.getMessage());
            }

            @Override
            public void handleTopologyRecoveryException(Connection conn, Channel ch, TopologyRecoveryException exception) {
                Log.w(TAG, "handleTopologyRecoveryException " + exception.getMessage());
            }
        });

        try {
            Log.d(TAG, "try connect");
            connection = (RecoverableConnection) factory.newConnection();
            Log.d(TAG, "connection "+connection);
            connection.addRecoveryListener(new RecoveryListener() {
                @Override
                public void handleRecovery(Recoverable recoverable) {
                    Log.v(TAG, "connection handleRecovery");
                }

                @Override
                public void handleRecoveryStarted(Recoverable recoverable) {
                    Log.v(TAG, "connection handleRecoveryStarted");
                }
            });
            connection.addShutdownListener(new ShutdownListener() {
                @Override
                public void shutdownCompleted(ShutdownSignalException cause) {
                    Log.w(TAG, "connection shutdownCompleted: " + cause.getMessage());
                }
            });
            channel = (RecoverableChannel) connection.createChannel();
            Log.d(TAG, "channel "+channel);
            channel.addRecoveryListener(new RecoveryListener() {
                @Override
                public void handleRecovery(Recoverable recoverable) {
                    Log.v(TAG, "channel handleRecovery");
                }

                @Override
                public void handleRecoveryStarted(Recoverable recoverable) {
                    Log.v(TAG, "channel handleRecoveryStarted");
                }
            });
            channel.addShutdownListener(new ShutdownListener() {
                @Override
                public void shutdownCompleted(ShutdownSignalException cause) {
                    Log.w(TAG, "channel shutdownCompleted: " + cause.getMessage());
                }
            });

            Log.i(TAG, "connected");

//            Map<String, Object> arguments = new HashMap<>();
//            arguments.put("x-expires", 60000);
//            arguments.put("x-message-ttl", 60000);
//            arguments.put("x-dead-letter-exchange", "conversation.dead");
//            arguments.put("x-dead-letter-routing-key", Controller.getInstance().getAuth().user.unique_id + "." + "my_device_id");
//            queue = channel.queueDeclare(queue, false, false, false, arguments).getQueue();
//            channel.queueBind(queue, unique_id, "");

//            Log.d(TAG, "queue created: " + queue);

//            channel.basicQos(prefetchCount);
//            channel.basicConsume(queue, false, new MessageConsumer(channel));

        } catch (IOException e) {
            Log.w(TAG, "ioerror "+e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand");
        someTask();
        // если пришла связь wifi, проверить на wifi бара и запустить сессию
        if(intent != null && intent.getBooleanExtra("isWifi", false)){
            Log.d(TAG, "Сообщение и ресивера о наличии wifi");
//            isWifi(true);
        }

//        return super.onStartCommand(intent, flags, startId);
        return START_STICKY;
    }

    private void someTask() {}

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy");
//        EventBus.getDefault().unregister(this);
        if(connection != null && connection.isOpen()){
            try {
                connection.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind");
        return mBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(TAG, "onUnbind");
        return super.onUnbind(intent);
    }

//    private void authorizationMock(@Nullable ICommand<Boolean> loginListener, int additional){
//        Log.d(TAG, "authorizationMock");
//        login(Social.MOCK, "mock_dev", "mock_dev", additional, loginListener);
//    }
//
//    /**
//     *
//     * @return false - нет данных для авторизации
//     */
//    public void autoLogin(@Nullable ICommand<Boolean> loginListener, int additional){
//        Log.d(TAG, "autoLogin");
//        Preferences p = new Preferences.Builder(TechnoparkApp.getContext()).load();
//        if(p.getSocial() == null){
//            if(loginListener != null) loginListener.exec(false);
//            return;
//        }
//        Social social = Social.fromString(p.getSocial());
//        if(social == null){
//            if(loginListener != null) loginListener.exec(false);
//            return;
//        }
//
//        status = Status.LOGIN_IN_PROCESS;
//
//        switch (social){
//            case FB:
//                authorizationFacebook(loginListener, additional);
//                break;
//            case VK:
//                authorizationVK(loginListener, additional);
//                break;
//            default:
//                authorizationMock(loginListener, additional);
//        }
//    }
//
//    public void authorizationFacebook(@Nullable final ICommand<Boolean> loginListener, final int additional){
//        Log.d(TAG, "authorizationFacebook");
//        AccessToken accessToken = AccessToken.getCurrentAccessToken();
//        if(accessToken != null)
//        {
//            Log.d(TAG, "FB accessToken: " + accessToken.getToken());
//            AccessToken.refreshCurrentAccessTokenAsync(new AccessToken.AccessTokenRefreshCallback() {
//                @Override
//                public void OnTokenRefreshed(AccessToken accessToken) {
//                    Log.d(TAG, "FB OnTokenRefreshed: " + accessToken.getToken());
//                    login(Social.FB, accessToken.getUserId(), accessToken.getToken(), additional, loginListener);
//                }
//
//                @Override
//                public void OnTokenRefreshFailed(FacebookException exception) {
//                    Log.d(TAG, "FB OnTokenRefreshFailed: " + exception.getMessage());
//                    if(loginListener != null) loginListener.exec(false);
//                }
//            });
//        }else{
//            if(loginListener != null) loginListener.exec(false);
//        }
//    }
//
//    public void authorizationVK(final ICommand<Boolean> loginListener, int additional){
//        Log.d(TAG, "authorizationVK");
//        VKAccessToken accessToken = VKAccessToken.currentToken();
//        if(accessToken != null){
//            Log.d(TAG, "VK accessToken: " + accessToken.accessToken);
//            VKAccessTokenTracker vkAccessTokenTracker = new VKAccessTokenTracker() {
//                @Override
//                public void onVKAccessTokenChanged(@Nullable VKAccessToken oldToken, @Nullable VKAccessToken newToken) {
//                    if(newToken == null){
//                        Log.d(TAG, "VK refresh token is null");
//                        if(loginListener != null) loginListener.exec(false);
//                    }else{
//                        Log.d(TAG, "VK refresh token " + newToken.accessToken);
//                    }
//                    if(loginListener != null) loginListener.exec(true);
//                }
//            };
//            vkAccessTokenTracker.startTracking();
//            login(Social.VK, accessToken.userId, accessToken.accessToken, additional, loginListener);
//        }else{
//            if(loginListener != null) loginListener.exec(false);
//        }
//    }
//
//    public void login(final Social social, final String social_id, final String social_token, int additional, @Nullable final ICommand<Boolean> loginListener){
//        Log.d(TAG, "login");
//        REST.getInstance().loginSocial(
//                additional == 0 ? null : Controller.getInstance().getAuth().user,
//                "1", social.toString(), social_id, social_token, additional)
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new REST.DataSubscriber<User>() {
//                    @Override
//                    public void onData(User data) {
//                        Log.d(TAG, "socialLogin");
//                        Controller.getInstance().getAuth().user = data;
//
//                        //запуск таймера проверки wifi, только при успешном логине
//                        if(timer == null){
//                            timer = new Timer();
//                            timer.schedule(new TimerTask() {
//                                @Override
//                                public void run() {
//                                    Log.d(TAG, "Timer task tick");
//                                    isWifi(Controller.getInstance().getAuth().wifiToken == null || Controller.getInstance().getAuth().wifiToken.expired());
//                                }
//                            }, 0, Consts.WIFI_TEST_TIMER);
//                        }
//                        if(loginListener != null) loginListener.exec(true);
//                        //save autoLogin settings
//                        new Preferences.Builder(RabbitMQService.this).getPreferences()
//                                .setSettings(settings)
//                                .setLogin(social.toString(), social_id, social_token)
//                                .save();
//                    }
//                });
//    }
//
//    private void isWifi(final boolean start){
//        Log.d(TAG, "isWifi");
//        if(!Controller.getInstance().isAuth()){
//            Log.w(TAG, "not authorized, try to autologin");
//            loadSettings();
//            return;
//        }
//
//        REST.getInstance().isWifi(Controller.getInstance().getAuth().user)
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new REST.DataSubscriber<WifiToken>() {
//                    @Override
//                    public void onData(WifiToken data) {
//                        if(start && data != null) startSession();
//                        if(data != null){
//                            Controller.getInstance().getAuth().wifiToken = data;
//                            EventBus.getDefault().postSticky(new WiFiChangedEvent(true));
//                        }
//                    }
//                });
//    }

//    private void startSession(){    //todo нужно сделать переконнект при разрыве соединения
//        Log.d(TAG, "startSession");
//        /*Observable<Result<Session>> or = */
//        Observable.create(new Observable.OnSubscribe<Void>() {
//            @Override
//            public void call(Subscriber<? super Void> subscriber) {
//                try {
//                    /*if(connection == null || !connection.isOpen())*/ connect();
//                    subscriber.onNext(null);
//                } catch (TimeoutException e) {
//                    e.printStackTrace();
//                } catch (NoSuchAlgorithmException e) {
//                    e.printStackTrace();
//                } catch (KeyManagementException e) {
//                    e.printStackTrace();
//                } catch (URISyntaxException e) {
//                    e.printStackTrace();
//                }
//            }
//        }).subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .flatMap(new Func1<Void, Observable<Result<Session>>>() {
//                    @Override
//                    public Observable<Result<Session>> call(Void v) {
//                        Log.d(TAG, "Rabbit flat map");
//                        return REST.getInstance().sessionStart(Controller.getInstance().getAuth().user,
//                                Controller.getInstance().getAuth().wifiToken, RabbitMQService.this.queue, DEVICE, deviceId, FirebaseInstanceId.getInstance().getToken(), 1)
//                                .observeOn(AndroidSchedulers.mainThread());
//                    }
//                })
//                //.observeOn(AndroidSchedulers.mainThread())
//                .observeOn(Schedulers.io())
//                .subscribe(new REST.DataSubscriber<Session>() {
//                    @Override
//                    public void onData(Session data) {
//                        Log.d(TAG, "startSession ok load bar");
//                        status = Status.WORK;
//                        try {
//                            channel.basicQos(prefetchCount);
//                            channel.basicConsume(data.queue, true, new MessageConsumer(channel));
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
////                        if(timer == null){
////                            timer = new Timer();
////                            timer.schedule(new TimerTask() {
////                                @Override
////                                public void run() {
////                                    isWifi(Controller.getInstance().getAuth().wifiToken == null || Controller.getInstance().getAuth().wifiToken.expired());
////                                }
////                            }, Consts.WIFI_TEST_TIMER, Consts.WIFI_TEST_TIMER);
////                        }
////                        EventBus.getDefault().postSticky(new AuthorizedEvent());
////                        loadBars(SmartLocation.with(MainActivity.this).location().getLastLocation());
//                    }
//                });
//    }

    public Status getStatus() {
        return status;
    }

    private void loadSettings() {
        Log.d(TAG, "loadSettings");
        REST.getInstance().settings()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new REST.DataSubscriber<Settings>() {
                    @Override
                    public void onData(Settings data) {
                        settings = data;
                        /*autoLogin(new ICommand<Boolean>() {
                            @Override
                            public void exec(Boolean val) {
                                if(val){
                                    status = Status.WORK;
                                }else{
                                    status = Status.STARTED;
                                }
                                EventBus.getDefault().postSticky(new AuthorizedEvent(Controller.getInstance().isAuth()));
                            }
                        }, 0);*/
                    }
                });
    }

    private String genLocalId(){
        return "id_" + Math.round(Math.random() * 500000);
    }

    public String sendUserStatus(String room_uid, String status) throws IOException {
        Log.d(TAG, "sendUserStatus");
        Message message = new Message();
        message.user_token = Controller.getInstance().getAuth().user.token;
//        message.wifi_token = Controller.getInstance().getAuth().wifiToken;
        message.local_id = genLocalId();
        message.message = status;

        String userId = Controller.getInstance().getAuth().user.id;
        basicPublish("user-status." + userId + "." + room_uid, message);
        return message.local_id;
    }

    public String sendMessageStatus(String uuid, String room_uid, Message.Status status) throws IOException {
        Log.d(TAG, "sendMessageStatus");
        Message message = new Message();
        message.user_token = Controller.getInstance().getAuth().user.token;
//        message.wifi_token = Controller.getInstance().getAuth().wifiToken;
        message.uuid = uuid;
        message.setStatus(status);
        message.local_id = genLocalId();

        String userId = Controller.getInstance().getAuth().user.id;
        basicPublish("message-status." + userId + "." + room_uid, message);
        return message.local_id;
    }

    public String sendMessage(User other, String text) throws IOException {
        Log.d(TAG, "sendMessage");
        Message message = new Message();
        message.user_token = Controller.getInstance().getAuth().user.token;
//        message.wifi_token = Controller.getInstance().getAuth().wifiToken;
        message.local_id = genLocalId();
        message.message = text;

        String userId = Controller.getInstance().getAuth().user.id;
        basicPublish("dialog." + userId + "." + other.unique_id, message);
        return message.local_id;
    }

    /*public String sendChinChin(User other) throws IOException {
        Log.d(TAG, "sendChinChin");
        Message message = new Message();
        message.user_token = Controller.getInstance().getAuth().user.token;
        message.wifi_token = Controller.getInstance().getAuth().wifiToken;
        message.local_id = genLocalId();

        int userId = Controller.getInstance().getAuth().user.id;
        basicPublish("chin-chin." + userId + "." + other.unique_id, message);
        return message.local_id;
    }

    public String sendChinAccept(User other, int result) throws IOException {
        Log.d(TAG, "sendChinAccept ");
        Message message = new Message();
        message.user_token = Controller.getInstance().getAuth().user.token;
        message.wifi_token = Controller.getInstance().getAuth().wifiToken;
        message.local_id = genLocalId();
        message.message = Integer.toString(result);

        int userId = Controller.getInstance().getAuth().user.id;
        basicPublish("chin-accept." + userId + "." + other.unique_id, message);
        return message.local_id;
    }

    public String sendChinFriend(User other) throws IOException {
        Log.d(TAG, "sendChinFriend");
        Message message = new Message();
        message.user_token = Controller.getInstance().getAuth().user.token;
        message.wifi_token = Controller.getInstance().getAuth().wifiToken;
        message.local_id = genLocalId();

        int userId = Controller.getInstance().getAuth().user.id;
        basicPublish("chin-friend." + userId + "." + other.unique_id, message);
        return message.local_id;
    }*/

    private void basicPublish(final String route, Message message) throws IOException{
        Log.d(TAG, "basicPublish");
        final String json = gson.toJson(message);
        Log.d(TAG, json);
        final AMQP.BasicProperties properties = new AMQP.BasicProperties.Builder()
                .replyTo(queue)
                .build();
        if(!channel.isOpen()){
            //reconnect
//            try {
                //connect();
//                startSession();
//            } catch (TimeoutException e) {
//                e.printStackTrace();
//            } catch (NoSuchAlgorithmException e) {
//                e.printStackTrace();
//            } catch (KeyManagementException e) {
//                e.printStackTrace();
//            } catch (URISyntaxException e) {
//                e.printStackTrace();
//            }
        }
        if(channel.isOpen()){
            Observable.create(new Observable.OnSubscribe<Void>() {
                @Override
                public void call(Subscriber<? super Void> subscriber) {
                    try {
                        channel.basicPublish("conversation.outgoing", route, properties, json.getBytes());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).subscribeOn(Schedulers.io())
            .subscribe();
        }
    }

    private class MessageConsumer extends DefaultConsumer {
        public MessageConsumer(Channel channel) {
            super(channel);
        }

        @Override
        public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
            String msg = new String(body, "UTF-8");
            Log.d(TAG, msg);

            try {
                Message message = gson.fromJson(msg, Message.class);
                Controller.getInstance().messages.income(message, false);
                EventBus.getDefault().post(new MessageEvent(message));

                String routingKey = envelope.getRoutingKey();
                Log.d(TAG, "Routing key " + routingKey);
                Log.d(TAG, "msg: " + msg);

//                channel.basicAck(envelope.getDeliveryTag(), false);
            } catch (Exception e){
//                channel.basicAck(envelope.getDeliveryTag(), false);
                Log.e(TAG, e.getMessage());
                e.printStackTrace();
            } finally {
                Log.d(TAG, " [x] Done.");
            }
        }
    }

    public class ServiceBinder extends Binder{
        public RabbitMQService getService(){
            return RabbitMQService.this;
        }
    }

    public static class AuthorizedEvent {
        private boolean isAuth;

        public AuthorizedEvent(boolean isAuth) {
            this.isAuth = isAuth;
        }

        public boolean isAuth() {
            return isAuth;
        }
    }

    public enum Status{
        STARTED, // ??
        LOGIN_IN_PROCESS,
//        ERROR,
//        NO_WIFI,
//        NO_AUTH,
        WORK;
    }
}
