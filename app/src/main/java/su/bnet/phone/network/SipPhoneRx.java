package su.bnet.phone.network;

import android.util.Log;

import org.pjsip.pjsua2.AccountConfig;
import org.pjsip.pjsua2.AuthCredInfo;
import org.pjsip.pjsua2.Call;
import org.pjsip.pjsua2.CallInfo;
import org.pjsip.pjsua2.CallOpParam;
import org.pjsip.pjsua2.Endpoint;
import org.pjsip.pjsua2.EpConfig;
import org.pjsip.pjsua2.TransportConfig;
import org.pjsip.pjsua2.pjsip_inv_state;
import org.pjsip.pjsua2.pjsip_status_code;
import org.pjsip.pjsua2.pjsip_transport_state;
import org.pjsip.pjsua2.pjsip_transport_type_e;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import su.bnet.phone.helpers.SipObserver;
import su.bnet.phone.model.MyAccount;
import su.bnet.phone.model.MyBuddy;
import su.bnet.phone.model.MyCall;
import su.bnet.phone.model.MyEndpoint;

/**
 * Created by andrey on 11.05.2017.
 */

public class SipPhoneRx {
    private final static String TAG = SipPhoneRx.class.toString();

    private final static SipPhoneRx instance = new SipPhoneRx();
    private static boolean loaded = false;

    public static SipObserver observer = new Observer();
    public static MyCall currentCall;
    private SipObserver observer2;

    private MyEndpoint endpoint;
    private EpConfig epConfig;
    private TransportConfig sipTpConfig;
    private AccountConfig acfg;
    private MyAccount account;

    private String server;
    private int port;

    private Subscriber<MyCall> callSubscriber;

    static {
        try {
            Log.d(TAG, "load library");
            System.loadLibrary("pjsua2");
            loaded = true;

        }catch (Exception e){
            Log.w(TAG, e);
        }
    }

    public static SipPhoneRx getInstance() {
        return instance;
    }

    private SipPhoneRx() {
    }

    public void setObserver(SipObserver observer) {
        this.observer2 = observer;
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

    public boolean isLoaded(){
        return loaded;
    }

    public Observable<SipPhoneRx> connect(final String server, final int port, final String number, final String secret, final boolean tcp, final boolean tls, boolean srtp){
        return Observable.create(new Observable.OnSubscribe<SipPhoneRx>() {
            @Override
            public void call(Subscriber<? super SipPhoneRx> subscriber) {
                try{
                    // Create endpoint
                    Log.d(TAG, "Create endpoint");
                    endpoint = new MyEndpoint();
                    endpoint.libCreate();   //TODO ?? надо ли это выносить отдельно?
                    // Initialize endpoint
                    epConfig = new EpConfig();
                    epConfig.getLogConfig().setConsoleLevel(5);
                    epConfig.getLogConfig().setMsgLogging(5);
                    epConfig.getLogConfig().setLevel(5);
                    //epConfig.getLogConfig().setWriter();
                    endpoint.libInit( epConfig );
                    endpoint.codecSetPriority("PCMU/8000/1", (short) 250);
                    // Create SIP transport. Error handling sample is shown
                    Log.d(TAG, "Create SIP transport.");
                    sipTpConfig = new TransportConfig();
                    //sipTpConfig.setPort(port);
                    long port2 = Math.round(Math.random() * 20000) + 20000;
                    sipTpConfig.setPort(port2);
                    if(tls){
                        endpoint.transportCreate(
                                pjsip_transport_type_e.PJSIP_TRANSPORT_TLS,
                                sipTpConfig);
                    }else{
                        endpoint.transportCreate(
                                tcp ? pjsip_transport_type_e.PJSIP_TRANSPORT_TCP: pjsip_transport_type_e.PJSIP_TRANSPORT_UDP,
                                sipTpConfig);
                    }

                    // Start the library
                    endpoint.libStart();

                    endpoint.codecSetPriority("PCMU/8000", (short)200);
                    endpoint.codecSetPriority("PCMA/8000", (short)199);
                    //endpoint.codecSetPriority("speex/16000", (short)0);
                    //endpoint.codecSetPriority("speex/8000", (short)0);
                    //endpoint.codecSetPriority("G722/8000", (short)0);
                    //endpoint.codecSetPriority("telephone-event/8000", (short)0);

                    Log.d(TAG, "AccountConfig");
                    acfg = new AccountConfig();
                    acfg.setIdUri("sip:" + number + "@" + server + ":" +port2);
                    acfg.getRegConfig().setRegistrarUri("sip:" + server + ":" + port);
                    acfg.getRegConfig().setDelayBeforeRefreshSec(60);
                    AuthCredInfo cred = new AuthCredInfo("digest", "*", number, 0, secret);
                    acfg.getSipConfig().getAuthCreds().add(cred);


                    // Create the account
                    Log.d(TAG, "Create the account");
                    account = new MyAccount(endpoint);
                    account.create(acfg);

                    SipPhoneRx.this.server = server;
                    SipPhoneRx.this.port = port;

                    subscriber.onNext(instance);
                    subscriber.onCompleted();
                }catch (Exception e){
                    subscriber.onError(e);
                }
            }
        }).compose(this.<SipPhoneRx>setup());
    }

    public Observable<SipPhoneRx> disconnect(){
        return Observable.create(new Observable.OnSubscribe<SipPhoneRx>() {
            @Override
            public void call(Subscriber<? super SipPhoneRx> subscriber) {
                try{
                    account.delete();
                    endpoint.libDestroy();
                    endpoint.delete();
                    subscriber.onNext(instance);
                    subscriber.onCompleted();
                }catch (Exception e){
                    subscriber.onError(e);
                }
            }
        }).compose(this.<SipPhoneRx>setup());
    }

    public Observable<MyCall> call(final String number){
        return Observable.create(new Observable.OnSubscribe<MyCall>() {
            @Override
            public void call(Subscriber<? super MyCall> subscriber) {
                //todo нужно сделать проверку на жизнеспособность аккаунта
                MyCall call = new MyCall(endpoint, account);
                CallOpParam prm = new CallOpParam(true);

                try{
                    call.makeCall("sip:" + number + "@" + server + ":" + port, prm);
                    currentCall = call;
                    subscriber.onNext(call);
                    subscriber.onCompleted();
                }catch (Exception e){
                    subscriber.onError(e);
                }
            }
        });
    }

    public void setSubscriber(Subscriber<MyCall> callSubscriber){
        this.callSubscriber = callSubscriber;
    }

    abstract public static class SipSubscriber<T> extends Subscriber<T>{
        @Override
        public void onCompleted() {}

        @Override
        public void onError(Throwable e) {
            Log.w(TAG, e);
        }
    }

    private static class Observer implements SipObserver{
        @Override
        public void notifyRegState(pjsip_status_code code, String reason, int expiration) {
            if(instance.observer2 != null) instance.observer2.notifyRegState(code, reason, expiration);
        }

        @Override
        public void notifyIncomingCall(MyCall call) {
            Log.v(TAG, "notifyIncomingCall");
            CallOpParam prm = new CallOpParam();
            /* Only one call at anytime */
            if (currentCall != null) {
                prm.setStatusCode(pjsip_status_code.PJSIP_SC_BUSY_HERE);
                try {
                    call.hangup(prm);
                } catch (Exception e) {
                    Log.w(TAG, e);
                }

                call.delete();
            }
            else{
                /* Answer with ringing */
                prm.setStatusCode(pjsip_status_code.PJSIP_SC_RINGING);
                try {
                    call.answer(prm);
                } catch (Exception e) {
                    Log.w(TAG, e);
                }

                currentCall = call;

                if(instance.observer2 != null) instance.observer2.notifyIncomingCall(call);
            }
        }

        @Override
        public void notifyCallState(MyCall call) {
            Log.v(TAG, "notifyCallState");
            if (currentCall == null || call.getId() != currentCall.getId())
                return;

            if(instance.observer2 != null) instance.observer2.notifyCallState(call);

            try {
                CallInfo ci;
                ci = call.getInfo();
                if (ci != null && ci.getState() == pjsip_inv_state.PJSIP_INV_STATE_DISCONNECTED)
                    currentCall = null;
            } catch (Exception e) {
                Log.w(TAG, e);
            }
        }

        @Override
        public void notifyCallTerminate() {
            if(instance.observer2 != null) instance.observer2.notifyCallTerminate();
        }

        @Override
        public void notifyCallMediaState(MyCall call) {
            if(instance.observer2 != null) instance.observer2.notifyCallMediaState(call);
        }

        @Override
        public void notifyBuddyState(MyBuddy buddy) {
            if(instance.observer2 != null) instance.observer2.notifyBuddyState(buddy);
        }

        @Override
        public void notifyTransportState(pjsip_transport_state state, String type, int lastError) {
            if(instance.observer2 != null) instance.observer2.notifyTransportState(state, type, lastError);
        }
    }
}
