package su.bnet.phone.model;

import org.pjsip.pjsua2.Endpoint;
import org.pjsip.pjsua2.OnTransportStateParam;

import su.bnet.phone.network.SipPhoneRx;

/**
 * Created by andrey on 15.05.2017.
 */

public class MyEndpoint extends Endpoint {
    @Override
    public void onTransportState(OnTransportStateParam prm) {
        SipPhoneRx.observer.notifyTransportState(prm.getState(), prm.getType(), prm.getLastError());
    }
}
