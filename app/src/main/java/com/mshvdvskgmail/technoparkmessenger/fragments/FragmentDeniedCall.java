package com.mshvdvskgmail.technoparkmessenger.fragments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.mshvdvskgmail.technoparkmessenger.Fragments;
import com.mshvdvskgmail.technoparkmessenger.R;
import com.mshvdvskgmail.technoparkmessenger.events.SipServiceEvent;
import com.mshvdvskgmail.technoparkmessenger.events.SwitchFragmentEvent;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

/**
 * Created by mshvdvsk on 09/03/2017.
 */

public class FragmentDeniedCall extends BaseFragment {
    private View mRootView;
    private AlertDialog alert;
    private FrameLayout frameCancel;
    private FrameLayout frameCallback;
    private FrameLayout frameMessage;


    public FragmentDeniedCall() {}

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_call_denied, container, false);
        inflatePicture(mRootView);
        addListeners(mRootView);
        return mRootView;
    }

    private void inflatePicture(View mRootView) {
        ImageView profileIcon = (ImageView) mRootView.findViewById(R.id.fragment_call_denied_image_picture);
        Picasso.with(getContext()).load(R.drawable.pushkin).transform(new RoundedCornersTransformation(360,0)).into(profileIcon);
    }

    private void addListeners(View mRootView) {
        frameCancel = (FrameLayout) mRootView.findViewById(R.id.fragment_call_denied_fl_cancel);
        frameCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //EventBus.getDefault().postSticky(new SwitchFragmentEvent(Fragments.MAIN_FOUR_TAB_SCREEN, null));
                getActivity().finish();
            }
        });

        frameCallback = (FrameLayout) mRootView.findViewById(R.id.fragment_call_denied_fl_callback);
        frameCallback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //EventBus.getDefault().postSticky(new SipServiceEvent(SipServiceEvent.Type.CALL, "arman_dev")); //TODO
                //EventBus.getDefault().postSticky(new SwitchFragmentEvent(Fragments.OUTGOING_CALL, null));
                //TODO FragmentOutgoingCall outgoingCall = new FragmentOutgoingCall();
//                getFragmentManager()
//                        .beginTransaction()
//                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
//                        .replace(R.id.container, outgoingCall)
//                        .addToBackStack(null)
//                        .commit();
            }
        });

        frameMessage = (FrameLayout) mRootView.findViewById(R.id.fragment_call_denied_fl_message);
        frameMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            //TODO
            }
        });

    }

}
