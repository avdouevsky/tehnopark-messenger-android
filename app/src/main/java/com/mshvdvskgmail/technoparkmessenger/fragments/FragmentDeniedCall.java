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

import com.mshvdvskgmail.technoparkmessenger.R;
import com.squareup.picasso.Picasso;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

/**
 * Created by mshvdvsk on 09/03/2017.
 */

public class FragmentDeniedCall extends Fragment {
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
                FragmentMainFourTabScreen mainScreen = new FragmentMainFourTabScreen();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .replace(R.id.container, mainScreen)
                        .addToBackStack(null)
                        .commit();
            }
        });

        frameCallback = (FrameLayout) mRootView.findViewById(R.id.fragment_call_denied_fl_callback);
        frameCallback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentOutgoingCall outgoingCall = new FragmentOutgoingCall();
                getFragmentManager()
                        .beginTransaction()
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .replace(R.id.container, outgoingCall)
                        .addToBackStack(null)
                        .commit();
            }
        });

        frameMessage = (FrameLayout) mRootView.findViewById(R.id.fragment_call_denied_fl_message);
        frameMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*  show toast reaction */

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
                alertDialog.setTitle("ОК, СПАСИБО");
                alertDialog.setMessage("Все работает ок, не так ли?");
                alertDialog.setPositiveButton("Да", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alertDialog.setNegativeButton("Не знаю", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alert = alertDialog.create();
                alert.show();
//                Intent myIntent = new Intent(MainActivity.this, ActivityProfile.class);
//                startActivity(myIntent);
            }
        });

    }

}
