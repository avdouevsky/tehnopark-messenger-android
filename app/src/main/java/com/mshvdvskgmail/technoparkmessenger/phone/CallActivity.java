package com.mshvdvskgmail.technoparkmessenger.phone;

import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.ActivityInfo;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.mshvdvskgmail.technoparkmessenger.Controller;
import com.mshvdvskgmail.technoparkmessenger.Fragments;
import com.mshvdvskgmail.technoparkmessenger.R;
import com.mshvdvskgmail.technoparkmessenger.activities.BaseActivity;
import com.mshvdvskgmail.technoparkmessenger.activities.MainActivity;
import com.mshvdvskgmail.technoparkmessenger.events.SipServiceEvent;
import com.mshvdvskgmail.technoparkmessenger.fragments.BaseFragment;
import com.mshvdvskgmail.technoparkmessenger.network.REST;
import com.mshvdvskgmail.technoparkmessenger.network.model.User;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.pjsip.pjsua2.CallInfo;
import org.pjsip.pjsua2.CallOpParam;
import org.pjsip.pjsua2.pjsip_inv_state;
import org.pjsip.pjsua2.pjsip_role_e;
import org.pjsip.pjsua2.pjsip_status_code;

import java.util.List;

import su.bnet.flowcontrol.BundleCommand;
import su.bnet.flowcontrol.FragmentNavigator;
import su.bnet.flowcontrol.Router;
import su.bnet.phone.network.SipPhoneRx;

public class CallActivity extends BaseActivity {
    private final static String TAG = CallActivity.class.toString();

    public final static String ACTION = "action";
    public final static String USER = "user";

    private FrameLayout frame;

    private Router<Fragments, BundleCommand> router;
    private FragmentNavigator<Fragments, BundleCommand> navigator;

    private static CallInfo lastCallInfo;

    private void initRoute(){
        Log.v(TAG, "initRoute");
        router = new Router<>();
        //clear stack
        if(getFragmentManager().getBackStackEntryCount() != 0){
            Log.w(TAG, "we have a stack!");
            for (int i = 0; i < getFragmentManager().getBackStackEntryCount(); i++) {
                getFragmentManager().popBackStack();
            }
        }
        navigator = new FragmentNavigator<Fragments, BundleCommand>(router, getSupportFragmentManager(), R.id.frame) {
            @Override
            protected Fragment createFragment(Fragments screenKey, Bundle data) {
                return BaseFragment.newInstance(screenKey, data);
            }
        };

        for(Fragments s : Fragments.values()){
            router.add(s, new CallActivity.CallCommand(s));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call);

        initRoute();

        frame = (FrameLayout) findViewById(R.id.frame);
        onNewIntent(getIntent());

        //navigator.forwardTo();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        //Fragments f = (Fragments)intent.getSerializableExtra("type");
        Action action = (Action)intent.getSerializableExtra(ACTION);
        User user = (User) intent.getSerializableExtra(USER);


        switch (action){
            case OUTGOING:
                SipService.command(SipService.Commands.CALL, user);
                navigator.replaceTo(Fragments.OUTGOING_CALL, intent.getExtras());
                break;
            case INCOMING:
                navigator.replaceTo(Fragments.INCOMING_CALL, intent.getExtras());
                break;
            case TERMINATE:
                navigator.replaceTo(Fragments.DENIED_CALL, intent.getExtras());
                break;
        }

        if (SipPhoneRx.currentCall != null) {
            try {
                lastCallInfo = SipPhoneRx.currentCall.getInfo();
//                updateCallState(lastCallInfo);
            } catch (Exception e) {
                Log.w(TAG, e);
            }
        } else {
//            updateCallState(lastCallInfo);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    //    @Override
//    protected void onStart() {
//        super.onStart();
//        EventBus.getDefault().register(this);
//    }
//
//    @Override
//    protected void onStop() {
//        super.onStop();
//        EventBus.getDefault().unregister(this);
//    }

    private class CallCommand extends BundleCommand{
        private Fragments state;

        public CallCommand(Fragments state) {
            this.state = state;
        }

        @Override
        public void forward(Bundle data) {
//            if(toolbar != null){
//                //set title
//                String title = ArgsBuilder.create(data).title();
//                if(title != null) toolbar.setTitle(title);
//                else toolbar.setTitle(state.getTitle());
//            }
//            if(navigationView != null){
//                frameNavigationBar.setVisibility(state.isNavigationBar() ? View.VISIBLE : View.GONE);
//                toggle.setDrawerIndicatorEnabled(state.isNavigationBar());
//            }

            super.forward(data);
        }

        @Override
        public void rollback() {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//            toolbar.getTitleView().setState(ActiveTextView.State.PASSIVE);
            invalidateOptionsMenu();
//            toggle.setDrawerIndicatorEnabled(false);
        }
    }

    private void updateCallState(CallInfo ci) {
//        TextView tvPeer = (TextView) findViewById(R.id.textViewPeer);
//        TextView tvState = (TextView) findViewById(R.id.textViewCallState);
//        Button buttonHangup = (Button) findViewById(R.id.buttonHangup);
//        Button buttonAccept = (Button) findViewById(R.id.buttonAccept);
//        String call_state = "";
//
//        if (ci.getRole() == pjsip_role_e.PJSIP_ROLE_UAC) {
//            buttonAccept.setVisibility(View.GONE);
//        }
//
//        if (ci.getState().swigValue() <
//                pjsip_inv_state.PJSIP_INV_STATE_CONFIRMED.swigValue()) {
//            if (ci.getRole() == pjsip_role_e.PJSIP_ROLE_UAS) {
//                call_state = "Incoming call..";
//		/* Default button texts are already 'Accept' & 'Reject' */
//            } else {
//                buttonHangup.setText("Cancel");
//                call_state = ci.getStateText();
//            }
//        } else if (ci.getState().swigValue() >=
//                pjsip_inv_state.PJSIP_INV_STATE_CONFIRMED.swigValue()) {
//            buttonAccept.setVisibility(View.GONE);
//            call_state = ci.getStateText();
//            if (ci.getState() == pjsip_inv_state.PJSIP_INV_STATE_CONFIRMED) {
//                buttonHangup.setText("Hangup");
//            } else if (ci.getState() ==
//                    pjsip_inv_state.PJSIP_INV_STATE_DISCONNECTED) {
//                buttonHangup.setText("OK");
//                call_state = "Call disconnected: " + ci.getLastReason();
//            }
//        }
//
//        tvPeer.setText(ci.getRemoteUri());
//        tvState.setText(call_state);
    }

    @Override
    public void onBackPressed() {
        if(!navigator.back()) {

        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public final void onEvent(CallStateEvent event) {
        updateCallState(event.getLastCallInfo());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public final void onEvent(SipServiceEvent event) {
        switch (event.getType()){
            case ANSWER:
                Log.d(TAG, "ANSWER");
                service.acceptCall();
                navigator.replaceTo(Fragments.OUTGOING_CALL); //change face
                //TODO костыль, большой костыль
//                String usr = null;
//                if(SipPhoneRx.currentCall != null){
//                    try {
//                        CallInfo ci = null;
//                        ci = SipPhoneRx.currentCall.getInfo();
//                        usr = ci.getRemoteUri();
//                        if(usr != null){
//                            String s[] = usr.split(" ");
//                            if(s.length>0){
//                                usr = s[0].trim();
//                            }
//
//                        }
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//                if(usr == null){
//                    navigator.replaceTo(Fragments.OUTGOING_CALL); //change face
//                }else{
//                    REST.getInstance().get_by_sip(Controller.getInstance().getAuth().getUser().token, usr)
//                            .subscribe(new REST.DataSubscriber<List<User>>() {
//                                @Override
//                                public void onData(List<User> data) {
//                                    if(data.size() != 0){
//                                        Bundle b = new Bundle();
//                                        b.putSerializable(CallActivity.USER, data.get(0));
//                                        navigator.replaceTo(Fragments.OUTGOING_CALL); //change face
//                                    }
//                                }
//
//                                @Override
//                                public void onError(Throwable e) {
//                                    navigator.replaceTo(Fragments.OUTGOING_CALL); //change face
//                                }
//                            });
//                }
                break;
            case HANGUP:
                Log.d(TAG, "DENIED_CALL");
                navigator.replaceTo(Fragments.DENIED_CALL);
                service.hangupCall();
                break;
            case CALL:
                Log.d(TAG, "CALL to " + event.getData());
                navigator.replaceTo(Fragments.OUTGOING_CALL);
                service.makeCall(event.getData());
                break;
        }
    }

    public static class CallStateEvent{
        private CallInfo lastCallInfo;

        public CallStateEvent(CallInfo lastCallInfo) {
            this.lastCallInfo = lastCallInfo;
        }

        public CallInfo getLastCallInfo() {
            return lastCallInfo;
        }
    }

    public enum Action{
        INCOMING, OUTGOING, TERMINATE;
    }
}
