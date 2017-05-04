package com.mshvdvskgmail.technoparkmessenger.fragments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mshvdvskgmail.technoparkmessenger.R;

/**
 * Created by mshvdvsk on 07/03/2017.
 */

public class FragmentResetPassword extends BaseFragment{
    private View mRootView;
    private AlertDialog alert;
    private TextView acceptButton;
    private TextView backButton;

    public FragmentResetPassword() {}

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_reset_password, container, false);

        setButtonListeners(mRootView);

        return mRootView;
    }

    private void setButtonListeners(View mRootView) {

        /* the "ОК, СПАСИБО" button */

        acceptButton = (TextView) mRootView.findViewById(R.id.fragment_reset_password_tv_accept);
        acceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        /* the "НАЗАД" button */

        backButton = (TextView) mRootView.findViewById(R.id.fragment_reset_password_tv_back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
    }


}
