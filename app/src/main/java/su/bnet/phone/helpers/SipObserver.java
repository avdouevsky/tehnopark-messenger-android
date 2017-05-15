package su.bnet.phone.helpers;

import org.pjsip.pjsua2.pjsip_status_code;
import org.pjsip.pjsua2.pjsip_transport_state;

import su.bnet.phone.model.MyBuddy;
import su.bnet.phone.model.MyCall;

/**
 * Created by andrey on 12.05.2017.
 */

public interface SipObserver {
    abstract void notifyRegState(pjsip_status_code code, String reason,
                                 int expiration);

    abstract void notifyIncomingCall(MyCall call);

    abstract void notifyCallState(MyCall call);

    abstract void notifyCallMediaState(MyCall call);

    abstract void notifyBuddyState(MyBuddy buddy);

    void notifyTransportState(pjsip_transport_state state, String type, int lastError);
}
