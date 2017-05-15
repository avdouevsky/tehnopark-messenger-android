package su.bnet.phone.model;

import android.content.Context;
import android.util.Log;

import com.mshvdvskgmail.technoparkmessenger.TechnoparkApp;

import org.pjsip.pjsua2.Account;
import org.pjsip.pjsua2.Endpoint;
import org.pjsip.pjsua2.OnIncomingCallParam;
import org.pjsip.pjsua2.OnIncomingSubscribeParam;
import org.pjsip.pjsua2.OnInstantMessageParam;
import org.pjsip.pjsua2.OnInstantMessageStatusParam;
import org.pjsip.pjsua2.OnMwiInfoParam;
import org.pjsip.pjsua2.OnRegStartedParam;
import org.pjsip.pjsua2.OnRegStateParam;
import org.pjsip.pjsua2.OnTypingIndicationParam;

import su.bnet.phone.network.SipPhoneRx;

/**
 * Created by andrey on 11.05.2017.
 */

public class MyAccount extends Account {
    private final static String TAG = MyAccount.class.toString();

    private Endpoint endpoint;

    public MyAccount(Endpoint endpoint) {
        this.endpoint = endpoint;
    }

    @Override
    public void onIncomingCall(OnIncomingCallParam prm) {
        Log.d(TAG, "onIncomingCall");
        MyCall call = new MyCall(endpoint, this, prm.getCallId());
        SipPhoneRx.observer.notifyIncomingCall(call);
    }

    @Override
    public void onRegStarted(OnRegStartedParam prm) {
        super.onRegStarted(prm);

        Log.d(TAG, "onRegStarted");
    }

    @Override
    public void onRegState(OnRegStateParam prm) {
        Log.d(TAG, "onRegState");
        SipPhoneRx.observer.notifyRegState(prm.getCode(), prm.getReason(), prm.getExpiration());
    }

    @Override
    public void onIncomingSubscribe(OnIncomingSubscribeParam prm) {
        super.onIncomingSubscribe(prm);

        Log.d(TAG, "onIncomingSubscribe");
    }

    @Override
    public void onInstantMessage(OnInstantMessageParam prm) {
        super.onInstantMessage(prm);

        Log.d(TAG, "onInstantMessage");
    }

    @Override
    public void onInstantMessageStatus(OnInstantMessageStatusParam prm) {
        super.onInstantMessageStatus(prm);

        Log.d(TAG, "onInstantMessageStatus");
    }

    @Override
    public void onTypingIndication(OnTypingIndicationParam prm) {
        super.onTypingIndication(prm);

        Log.d(TAG, "onTypingIndication");
    }

    @Override
    public void onMwiInfo(OnMwiInfoParam prm) {
        super.onMwiInfo(prm);

        Log.d(TAG, "onMwiInfo");
    }
}
