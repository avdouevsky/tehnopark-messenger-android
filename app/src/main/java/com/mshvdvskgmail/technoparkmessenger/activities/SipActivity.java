//package com.mshvdvskgmail.technoparkmessenger.activities;
//
//import android.app.Activity;
//import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
//import android.widget.FrameLayout;
//import android.widget.Toast;
//
//import com.mshvdvskgmail.technoparkmessenger.R;
//
//import java.util.ArrayList;
//
//import sipservice.src.main.java.net.gotev.sipservice.BroadcastEventReceiver;
//import sipservice.src.main.java.net.gotev.sipservice.CodecPriority;
//import sipservice.src.main.java.net.gotev.sipservice.Logger;
//import sipservice.src.main.java.net.gotev.sipservice.SipAccountData;
//import sipservice.src.main.java.net.gotev.sipservice.SipServiceCommand;
//import sipservice.src.main.java.org.pjsip.pjsua2.pjsip_status_code;
//
///**
// * Created by vlad on 04.04.17.
// */
//
//public class SipActivity extends AppCompatActivity {
//
//    private static final String KEY_SIP_ACCOUNT = "sip_account";
//    private SipAccountData mSipAccount;
//
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.fragment_call_incoming);
//
//        Logger.setLogLevel(Logger.LogLevel.DEBUG);
//
//        if (savedInstanceState != null && savedInstanceState.containsKey(KEY_SIP_ACCOUNT)) {
//            mSipAccount = savedInstanceState.getParcelable(KEY_SIP_ACCOUNT);
//        }
//        onRegister();
////        FrameLayout pushToTalkButton = (FrameLayout) findViewById(R.id.button_answer);
////        pushToTalkButton.setOnTouchListener(this);
//    }
//
//    private BroadcastEventReceiver sipEvents = new BroadcastEventReceiver() {
//
////        @Override
//        public void onRegistration(String accountID, pjsip_status_code registrationStateCode) {
//            if (registrationStateCode == pjsip_status_code.PJSIP_SC_OK) {
//                Toast.makeText(SipActivity.this, "Registered", Toast.LENGTH_SHORT).show();
//            } else {
//                Toast.makeText(SipActivity.this, "Unregistered", Toast.LENGTH_SHORT).show();
//            }
//        }
//
//        @Override
//        public void onReceivedCodecPriorities(ArrayList<CodecPriority> codecPriorities) {
//            for (CodecPriority codec : codecPriorities) {
//                if (codec.getCodecName().startsWith("PCM")) {
//                    codec.setPriority(CodecPriority.PRIORITY_MAX);
//                } else {
//                    codec.setPriority(CodecPriority.PRIORITY_DISABLED);
//                }
//            }
//            SipServiceCommand.setCodecPriorities(SipActivity.this, codecPriorities);
//        }
//    };
//
//    @Override
//    protected void onSaveInstanceState(Bundle outState) {
//        if (mSipAccount != null) {
//            outState.putParcelable(KEY_SIP_ACCOUNT, mSipAccount);
//        }
//
//        super.onSaveInstanceState(outState);
//    }
//
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        sipEvents.unregister(this);
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        sipEvents.register(this);
//    }
//
//    public void onRegister() {
//        mSipAccount = new SipAccountData();
//
////        if (!mSipServer.getText().toString().isEmpty()) {
////            mSipAccount.setHost(mSipServer.getText().toString())
////                    .setPort(Integer.valueOf(mSipPort.getText().toString()))
////                    .setTcpTransport(true)
////                    .setUsername(mUsername.getText().toString())
////                    .setPassword(mPassword.getText().toString())
////                    .setRealm(mRealm.getText().toString());
////        } else {
////            mSipAccount.setHost("192.168.1.154")
////                    .setPort(5060)
////                    .setTcpTransport(true)
////                    .setUsername("200")
////                    .setPassword("password200")
////                    .setRealm("devel.it");
////        }String username = "10007";
////        String domain = "213.247.249.83";
////        String password = "fbf56f5264805fbee4399754a6544dc7";
//            mSipAccount.setHost("213.247.249.83")
//                    .setPort(25060)
////                    .setTcpTransport(true)
//                    .setUsername("10007")
//                    .setPassword("fbf56f5264805fbee4399754a6544dc7");
////                    .setRealm("devel.it");
//
//        SipServiceCommand.setAccount(this, mSipAccount);
//        SipServiceCommand.getCodecPriorities(this);
//    }
//
//}
