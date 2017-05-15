package su.bnet.phone.model;

import android.util.Log;

import org.pjsip.pjsua2.Account;
import org.pjsip.pjsua2.AudioMedia;
import org.pjsip.pjsua2.Call;
import org.pjsip.pjsua2.CallInfo;
import org.pjsip.pjsua2.CallMediaInfo;
import org.pjsip.pjsua2.CallMediaInfoVector;
import org.pjsip.pjsua2.Endpoint;
import org.pjsip.pjsua2.Media;
import org.pjsip.pjsua2.OnCallMediaEventParam;
import org.pjsip.pjsua2.OnCallMediaStateParam;
import org.pjsip.pjsua2.OnCallMediaTransportStateParam;
import org.pjsip.pjsua2.OnCallRedirectedParam;
import org.pjsip.pjsua2.OnCallReplaceRequestParam;
import org.pjsip.pjsua2.OnCallReplacedParam;
import org.pjsip.pjsua2.OnCallRxOfferParam;
import org.pjsip.pjsua2.OnCallSdpCreatedParam;
import org.pjsip.pjsua2.OnCallStateParam;
import org.pjsip.pjsua2.OnCallTransferRequestParam;
import org.pjsip.pjsua2.OnCallTransferStatusParam;
import org.pjsip.pjsua2.OnCallTsxStateParam;
import org.pjsip.pjsua2.OnCallTxOfferParam;
import org.pjsip.pjsua2.OnCreateMediaTransportParam;
import org.pjsip.pjsua2.OnCreateMediaTransportSrtpParam;
import org.pjsip.pjsua2.OnDtmfDigitParam;
import org.pjsip.pjsua2.OnInstantMessageParam;
import org.pjsip.pjsua2.OnInstantMessageStatusParam;
import org.pjsip.pjsua2.OnStreamCreatedParam;
import org.pjsip.pjsua2.OnStreamDestroyedParam;
import org.pjsip.pjsua2.OnTypingIndicationParam;
import org.pjsip.pjsua2.pjmedia_type;
import org.pjsip.pjsua2.pjsip_inv_state;
import org.pjsip.pjsua2.pjsip_redirect_op;
import org.pjsip.pjsua2.pjsua_call_media_status;

import su.bnet.phone.network.SipPhoneRx;

/**
 * Created by andrey on 11.05.2017.
 */

public class MyCall extends Call {
    private final static String TAG = MyCall.class.toString();

    private Endpoint endpoint;

    protected MyCall(long cPtr, boolean cMemoryOwn) {
        super(cPtr, cMemoryOwn);

        Log.d(TAG, "construct 1");
    }

//    public MyCall(Account acc, int call_id) {
//        super(acc, call_id);
//
//        Log.d(TAG, "construct 2");
//    }


    public MyCall(Endpoint endpoint, Account acc, int call_id) {
        super(acc, call_id);
        this.endpoint = endpoint;

        Log.d(TAG, "construct 2");
    }

    public MyCall(Endpoint endpoint, Account acc) {
        super(acc);

        Log.d(TAG, "construct 3");

        this.endpoint = endpoint;
    }

    @Override
    public void onCallState(OnCallStateParam prm) {
        Log.d(TAG, "onCallState");

        SipPhoneRx.observer.notifyCallState(this);

        CallInfo ci = null;
        try {
            ci = getInfo();
            if(ci.getState() == pjsip_inv_state.PJSIP_INV_STATE_DISCONNECTED){
                Log.d(TAG, "terminate call");
                delete();
            }
        } catch (Exception e) {
            Log.w(TAG, e);
        }
    }

    @Override
    public void onCallTsxState(OnCallTsxStateParam prm) {
        super.onCallTsxState(prm);

        Log.d(TAG, "onCallTsxState");
    }

    @Override
    public void onCallMediaState(OnCallMediaStateParam prm) {
        Log.d(TAG, "onCallMediaState");

        CallInfo ci;
        try {
            ci = getInfo();
        } catch (Exception e) {
            return;
        }

        CallMediaInfoVector cmiv = ci.getMedia();

        for (int i = 0; i < cmiv.size(); i++) {
            CallMediaInfo cmi = cmiv.get(i);
            if (cmi.getType() == pjmedia_type.PJMEDIA_TYPE_AUDIO &&
                    (cmi.getStatus() ==
                            pjsua_call_media_status.PJSUA_CALL_MEDIA_ACTIVE ||
                            cmi.getStatus() ==
                                    pjsua_call_media_status.PJSUA_CALL_MEDIA_REMOTE_HOLD)) {
                Log.w(TAG, "PJMEDIA_TYPE_AUDIO");
                // unfortunately, on Java too, the returned Media cannot be
                // downcasted to AudioMedia
                Media m = getMedia(i);
                AudioMedia am = AudioMedia.typecastFromMedia(m);

                // connect ports
                try {
                    endpoint.audDevManager().getCaptureDevMedia().
                            startTransmit(am);
                    am.startTransmit(endpoint.audDevManager().
                            getPlaybackDevMedia());
                } catch (Exception e) {
                    Log.w(TAG, e);
                    continue;
                }
            } /*else if (cmi.getType() == pjmedia_type.PJMEDIA_TYPE_VIDEO &&
                    cmi.getStatus() ==
                            pjsua_call_media_status.PJSUA_CALL_MEDIA_ACTIVE &&
                    cmi.getVideoIncomingWindowId() != pjsua2.INVALID_ID) {
                vidWin = new VideoWindow(cmi.getVideoIncomingWindowId());
                vidPrev = new VideoPreview(cmi.getVideoCapDev());
            }*/
            else{
                Log.w(TAG, "Unsupported CallMediaInfo");
            }
        }

        SipPhoneRx.observer.notifyCallMediaState(this);
    }

    @Override
    public void onCallSdpCreated(OnCallSdpCreatedParam prm) {
        super.onCallSdpCreated(prm);

        Log.d(TAG, "onCallSdpCreated");
    }

    @Override
    public void onStreamCreated(OnStreamCreatedParam prm) {
        super.onStreamCreated(prm);

        Log.d(TAG, "onStreamCreated");
    }

    @Override
    public void onStreamDestroyed(OnStreamDestroyedParam prm) {
        super.onStreamDestroyed(prm);

        Log.d(TAG, "onStreamDestroyed");
    }

    @Override
    public void onDtmfDigit(OnDtmfDigitParam prm) {
        super.onDtmfDigit(prm);

        Log.d(TAG, "onDtmfDigit");
    }

    @Override
    public void onCallTransferRequest(OnCallTransferRequestParam prm) {
        super.onCallTransferRequest(prm);

        Log.d(TAG, "onCallTransferRequest");
    }

    @Override
    public void onCallTransferStatus(OnCallTransferStatusParam prm) {
        super.onCallTransferStatus(prm);

        Log.d(TAG, "onCallTransferStatus");
    }

    @Override
    public void onCallReplaceRequest(OnCallReplaceRequestParam prm) {
        super.onCallReplaceRequest(prm);

        Log.d(TAG, "onCallReplaceRequest");
    }

    @Override
    public void onCallReplaced(OnCallReplacedParam prm) {
        super.onCallReplaced(prm);

        Log.d(TAG, "onCallReplaced");
    }

    @Override
    public void onCallRxOffer(OnCallRxOfferParam prm) {
        super.onCallRxOffer(prm);

        Log.d(TAG, "onCallRxOffer");
    }

    @Override
    public void onCallTxOffer(OnCallTxOfferParam prm) {
        super.onCallTxOffer(prm);

        Log.d(TAG, "onCallTxOffer");
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
    public pjsip_redirect_op onCallRedirected(OnCallRedirectedParam prm) {
        Log.d(TAG, "onCallRedirected");

        return super.onCallRedirected(prm);
    }

    @Override
    public void onCallMediaTransportState(OnCallMediaTransportStateParam prm) {
        super.onCallMediaTransportState(prm);

        Log.d(TAG, "onCallMediaTransportState");
    }

    @Override
    public void onCallMediaEvent(OnCallMediaEventParam prm) {
        super.onCallMediaEvent(prm);

        Log.d(TAG, "onCallMediaEvent");
    }

    @Override
    public void onCreateMediaTransport(OnCreateMediaTransportParam prm) {
        super.onCreateMediaTransport(prm);

        Log.d(TAG, "onCreateMediaTransport");
    }

    @Override
    public void onCreateMediaTransportSrtp(OnCreateMediaTransportSrtpParam prm) {
        super.onCreateMediaTransportSrtp(prm);

        Log.d(TAG, "onCreateMediaTransportSrtp");
    }
}
