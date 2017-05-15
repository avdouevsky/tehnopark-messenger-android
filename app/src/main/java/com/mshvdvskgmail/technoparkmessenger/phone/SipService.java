package com.mshvdvskgmail.technoparkmessenger.phone;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.View;

import com.mshvdvskgmail.technoparkmessenger.Controller;
import com.mshvdvskgmail.technoparkmessenger.Fragments;
import com.mshvdvskgmail.technoparkmessenger.R;
import com.mshvdvskgmail.technoparkmessenger.TechnoparkApp;
import com.mshvdvskgmail.technoparkmessenger.network.model.User;

import org.greenrobot.eventbus.EventBus;
import org.pjsip.pjsua2.CallInfo;
import org.pjsip.pjsua2.CallOpParam;
import org.pjsip.pjsua2.pjsip_status_code;
import org.pjsip.pjsua2.pjsip_transport_state;

import su.bnet.flowcontrol.Command;
import su.bnet.phone.helpers.SipObserver;
import su.bnet.phone.model.MyBuddy;
import su.bnet.phone.model.MyCall;
import su.bnet.phone.network.SipPhoneRx;

public class SipService extends Service {
    private final static String TAG = SipService.class.toString();

    public static String COMMAND = "command";
    public static String USER = "user";

    private final IBinder mBinder = new ServiceBinder();
    private Controller controller = Controller.getInstance();

    public SipService() {
    }

    public enum Commands{
        CONNECT, CALL
    }

    public static void command(Commands command, User user){
        Intent intent = new Intent(TechnoparkApp.getContext(), SipService.class);
        intent.putExtra(SipService.COMMAND, command);
        intent.putExtra(SipService.USER, user);
        TechnoparkApp.getContext().startService(intent);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand");

        //String command = intent.getStringExtra("command");
        Commands command = (Commands)intent.getSerializableExtra(COMMAND);
        User user = (User)intent.getSerializableExtra(USER);

        if(command != null){
            switch (command){
                case CONNECT:
                    String ipphone = user.ipphone;
                    String s[] = ipphone.split(":");
                    String number = s[0];
                    String secret = s[1];
                    connect(number, secret);
                    break;
                case CALL:
                    makeCall(user.id);
                    break;
            }
        }

        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        //throw new UnsupportedOperationException("Not yet implemented");
        return mBinder;
    }

    private void someTask() {}

    public class ServiceBinder extends Binder {
        public SipService getService(){
            return SipService.this;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();

        SipPhoneRx.getInstance().setObserver(new Observer());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public void connect(String user, String secret){
        SipPhoneRx.getInstance().connect("213.247.249.83", 25060, user, secret, false, false, false)
                .subscribe(new SipPhoneRx.SipSubscriber<SipPhoneRx>() {
                    @Override
                    public void onNext(SipPhoneRx sipPhoneRx) {
                        Log.d(TAG, "connected");

                        //textView.setText("Connected");
                        //layoutConnect.setVisibility(View.GONE);
                        //buttonDisconnect.setVisibility(View.VISIBLE);
                    }
                });
    }

    public void disconnect(){
        SipPhoneRx.getInstance().disconnect().subscribe(new SipPhoneRx.SipSubscriber<SipPhoneRx>() {
            @Override
            public void onNext(SipPhoneRx sipPhoneRx) {
//                buttonDisconnect.setVisibility(View.GONE);
//                layoutConnect.setVisibility(View.VISIBLE);
            }
        });
    }

    public void makeCall(String number){
        SipPhoneRx.getInstance().call(number)
                .subscribe(new SipPhoneRx.SipSubscriber<MyCall>() {
                    @Override
                    public void onNext(MyCall myCall) {
                        Log.d(TAG, "Call in progress");
                    }
                });
    }

    public void acceptCall() {
        CallOpParam prm = new CallOpParam();
        try {
            prm.setStatusCode(pjsip_status_code.PJSIP_SC_OK);
            SipPhoneRx.currentCall.answer(prm);
        } catch (Exception e) {
            Log.w(TAG, e);
        }
    }

    public void hangupCall() {
        if (SipPhoneRx.currentCall != null) {
            CallOpParam prm = new CallOpParam();
            prm.setStatusCode(pjsip_status_code.PJSIP_SC_DECLINE);
            try {
                SipPhoneRx.currentCall.hangup(prm);
            } catch (Exception e) {
                Log.w(TAG, e);
            }
        }
    }

    private class Observer implements SipObserver {
        private int SIP = 2001;
        private int CALL = 2002;
        private int ACCOUNT = 2003;

        private void showDebugNotify(int id, String title, String body){
            NotificationCompat.Builder mBuilder =
                    new NotificationCompat.Builder(SipService.this)
                            .setSmallIcon(R.mipmap.ic_launcher)
                            .setColor(getResources().getColor(R.color.colorPrimary))
                            .setContentTitle(title)
                            .setContentText(body)
                            .setAutoCancel(true)
                            //.setContentIntent(intent)
                    ;
            //mBuilder.setSound(alarmSound);

            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notificationManager.notify(id, mBuilder.build());
        }

        @Override
        public void notifyRegState(pjsip_status_code code, String reason, int expiration) {
            String msg_str = "";
            if (expiration == 0)
                msg_str += "Unregistration";
            else
                msg_str += "Registration";

            if (code.swigValue() / 100 == 2)
                msg_str += " successful";
            else
                msg_str += " failed: " + reason;

            final String s = msg_str;

            showDebugNotify(ACCOUNT, "acc", s);

//            runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    textView.setText(s);
//                }
//            });


//            Message m = Message.obtain(handler, MSG_TYPE.REG_STATE, msg_str);
//            m.sendToTarget();

//            String msg_str = (String) m.obj;
//            lastRegStatus = msg_str;
        }

        @Override
        public void notifyIncomingCall(MyCall call) {
            Log.v(TAG, "notifyIncomingCall");
            Intent intent = new Intent(SipService.this, CallActivity.class);
            //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra(CallActivity.ACTION, CallActivity.Action.INCOMING);
            startActivity(intent);
        }

        @Override
        public void notifyCallState(MyCall call) {
            Log.v(TAG, "notifyCallState");
            CallInfo ci;
            try {
                ci = call.getInfo();
                showDebugNotify(CALL, "call", ci.getLastReason() + "(" + ci.getLastStatusCode() + ")");
            } catch (Exception e) {
                ci = null;
            }

            EventBus.getDefault().postSticky(new CallActivity.CallStateEvent(ci));
        }

        @Override
        public void notifyCallTerminate() {
            Log.v(TAG, "notifyCallTerminate");
            Intent intent = new Intent(SipService.this, CallActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra(CallActivity.ACTION, CallActivity.Action.TERMINATE);
            startActivity(intent);
        }

        @Override
        public void notifyCallMediaState(MyCall call) {
//            Message m = Message.obtain(handler, MSG_TYPE.CALL_MEDIA_STATE, null);
//            m.sendToTarget();

//            if (CallActivity.handler_ != null) {
//                Message m2 = Message.obtain(CallActivity.handler_,
//                        MSG_TYPE.CALL_MEDIA_STATE,
//                        null);
//                m2.sendToTarget();
//            }
        }

        @Override
        public void notifyBuddyState(MyBuddy buddy) {
//            Message m = Message.obtain(handler, MSG_TYPE.BUDDY_STATE, buddy);
//            m.sendToTarget();
        }

        @Override
        public void notifyTransportState(pjsip_transport_state state, String type, int lastError) {
            showDebugNotify(SIP, type, state.toString() + "(" + lastError + ")");
        }
    }
}
