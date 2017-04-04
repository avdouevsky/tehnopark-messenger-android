package com.mshvdvskgmail.technoparkmessenger.fragments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mshvdvskgmail.technoparkmessenger.R;

/**
 * Created by mshvdvsk on 07/03/2017.
 */

public class FragmentAuthorization extends Fragment{

   // private View mRootView;
    private AlertDialog alert;
    private EditText emailField;
    private EditText passwordField;
    private TextView mAuthorizationButton;
    private ImageView cancelCross1;
    private ImageView cancelCross2;
    private String s1;
    private String s2;

    public FragmentAuthorization() {}

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View mRootView = inflater.inflate(R.layout.fragment_athorization, container, false);

//        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        setButtonListeners(mRootView);

        /* setting the watcher */
        emailField = (EditText) mRootView.findViewById(R.id.fieldEmail);
        passwordField = (EditText) mRootView.findViewById(R.id.fieldPassword);
        emailField.addTextChangedListener(textWatcher);
        passwordField.addTextChangedListener(textWatcher);

        return mRootView;
    }

    private void setButtonListeners(View mRootView) {

        /* the "ВОЙТИ" button */

        mAuthorizationButton = (TextView) mRootView.findViewById(R.id.button_authorization_enter);
        mAuthorizationButton.setEnabled(false);
        mAuthorizationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentIncomingCall incomingCall = new FragmentIncomingCall();
                getFragmentManager()
                        .beginTransaction()
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .replace(R.id.container, incomingCall)
                        .addToBackStack(null)
                        .commit();
            }
        });

        /* the "ВОССТАНОВЛЕНИЕ ПАРОЛЯ" button */

        TextView mResetPassword = (TextView) mRootView.findViewById(R.id.button_reset_password);
        mResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentResetPassword authorization = new FragmentResetPassword();
                getFragmentManager()
                        .beginTransaction()
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .replace(R.id.container, authorization)
                        .addToBackStack(null)
                        .commit();
            }
        });

        /* the username input delete button */

        cancelCross1 = (ImageView) mRootView.findViewById(R.id.cancel_cross_one);
        cancelCross1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emailField.setText("");
                cancelCross1.setVisibility(View.GONE);
            }
        });

        /* the username input delete button */

        cancelCross2 = (ImageView) mRootView.findViewById(R.id.cancel_cross_two);
        cancelCross2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                passwordField.setText("");
                cancelCross2.setVisibility(View.GONE);
            }
        });

    }

    private TextWatcher textWatcher = new TextWatcher() {

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3)
        {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            checkFieldsForEnteredValues();
            checkFieldsForEmptyValues();
        }

        @Override
        public void afterTextChanged(Editable editable) {
        }
    };

    private void checkFieldsForEnteredValues() {

        s1 = emailField.getText().toString();
        s2 = passwordField.getText().toString();

        if (s1.equals("") && s2.equals("")) {
            mAuthorizationButton.setEnabled(false);
        } else if (!s1.equals("")&&s2.equals("")) {
            mAuthorizationButton.setEnabled(false);
        } else if (!s2.equals("")&&s1.equals("")) {
            mAuthorizationButton.setEnabled(false);
        } else {
            mAuthorizationButton.setEnabled(true);
        }
    }

    private void checkFieldsForEmptyValues() {

        s1 = emailField.getText().toString();
        s2 = passwordField.getText().toString();

        if (s1.length()>0){
            cancelCross1.setVisibility(View.VISIBLE);
        }

        if (s2.length()>0){
            cancelCross2.setVisibility(View.VISIBLE);
        }

    }


}
