package com.mshvdvskgmail.technoparkmessenger.fragments;

import android.content.DialogInterface;
import android.content.Intent;
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
import com.mshvdvskgmail.technoparkmessenger.TechnoparkApp;
import com.mshvdvskgmail.technoparkmessenger.events.SipServiceEvent;
import com.mshvdvskgmail.technoparkmessenger.events.SwitchFragmentEvent;
import com.mshvdvskgmail.technoparkmessenger.network.model.User;
import com.mshvdvskgmail.technoparkmessenger.phone.CallActivity;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

/**
 * Created by mshvdvsk on 09/03/2017.
 */

public class FragmentDeniedCall extends BaseFragment {
    private final static String USER = "user";

    private View mRootView;
    private AlertDialog alert;
    private FrameLayout frameCancel;
    private FrameLayout frameCallback;
    private FrameLayout frameMessage;

    private User user;

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

        frameCancel = (FrameLayout) mRootView.findViewById(R.id.fragment_call_denied_fl_cancel);
        frameCallback = (FrameLayout) mRootView.findViewById(R.id.fragment_call_denied_fl_callback);
        frameMessage = (FrameLayout) mRootView.findViewById(R.id.fragment_call_denied_fl_message);

        Bundle args = getArguments();
        if(args != null){
            user = (User)args.getSerializable(USER);
        }

        frameCallback.setVisibility(user == null ? View.INVISIBLE : View.VISIBLE);
        frameMessage.setVisibility(user == null ? View.INVISIBLE : View.VISIBLE);

        inflatePicture(mRootView);
        addListeners(mRootView);
        return mRootView;
    }

    private void inflatePicture(View mRootView) {
        ImageView profileIcon = (ImageView) mRootView.findViewById(R.id.fragment_call_denied_image_picture);
        Picasso.with(getContext()).load(R.drawable.pushkin).transform(new RoundedCornersTransformation(360,0)).into(profileIcon);
    }

    private void addListeners(View mRootView) {

        frameCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //EventBus.getDefault().postSticky(new SwitchFragmentEvent(Fragments.MAIN_FOUR_TAB_SCREEN, null));
                getActivity().finish();
            }
        });


        frameCallback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO доделать логику получения пользовател
                Intent intent = new Intent(TechnoparkApp.getContext(), CallActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra(CallActivity.ACTION, CallActivity.Action.OUTGOING);
                intent.putExtra(CallActivity.USER, user);
//                TechnoparkApp.getContext().startActivity(intent);
            }
        });


        frameMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO
            }
        });

    }
}
