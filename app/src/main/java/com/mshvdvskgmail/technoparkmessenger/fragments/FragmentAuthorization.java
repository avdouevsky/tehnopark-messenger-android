package com.mshvdvskgmail.technoparkmessenger.fragments;

import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mshvdvskgmail.technoparkmessenger.BuildConfig;
import com.mshvdvskgmail.technoparkmessenger.ChatController;
import com.mshvdvskgmail.technoparkmessenger.Controller;
import com.mshvdvskgmail.technoparkmessenger.R;
import com.mshvdvskgmail.technoparkmessenger.activities.MainActivity;
import com.mshvdvskgmail.technoparkmessenger.network.REST;
import com.mshvdvskgmail.technoparkmessenger.network.RMQChat;
import com.mshvdvskgmail.technoparkmessenger.network.RabbitMQ;
import com.mshvdvskgmail.technoparkmessenger.network.model.Result;
import com.mshvdvskgmail.technoparkmessenger.network.model.User;
//import com.mshvdvskgmail.technoparkmessenger.services.RabbitMQService;

import java.util.UUID;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;

/**
 * Created by mshvdvsk on 07/03/2017.
 */

public class FragmentAuthorization extends Fragment{
    private final static String TAG  = FragmentAuthorization.class.toString();

    private EditText emailField;
    private EditText passwordField;
    private TextView mAuthorizationButton;
    private ImageView cancelCross1;
    private ImageView cancelCross2;

    public FragmentAuthorization() {}

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mRootView = inflater.inflate(R.layout.fragment_athorization, container, false);

        cancelCross1 = (ImageView) mRootView.findViewById(R.id.fragment_authorization_image_cancel_cross_first);
        cancelCross2 = (ImageView) mRootView.findViewById(R.id.fragment_authorization_image_cancel_cross_second);
        mAuthorizationButton = (TextView) mRootView.findViewById(R.id.fragment_authorization_tv_enter);
        emailField = (EditText) mRootView.findViewById(R.id.fragment_authorization_et);
        passwordField = (EditText) mRootView.findViewById(R.id.fragment_authorization_et_password);

        mAuthorizationButton.setEnabled(false);
        if(BuildConfig.DEBUG){
            emailField.setText("testme1");
            passwordField.setText("Hello123test");
            mAuthorizationButton.setEnabled(true);
        }

        emailField.addTextChangedListener(textWatcher);
        passwordField.addTextChangedListener(textWatcher);

        setButtonListeners(mRootView);

        return mRootView;
    }

    private void setButtonListeners(View mRootView) {
        mAuthorizationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                REST.getInstance().login(emailField.getText().toString(), passwordField.getText().toString(), Controller.getInstance().deviceType(), Controller.getInstance().deviceId(), "")
                        .flatMap(new Func1<Result<User>, Observable<RabbitMQ>>() {
                            @Override
                            public Observable<RabbitMQ> call(Result<User> userResult) {
//                                user = userResult.data;
                                Controller.getInstance().getAuth().user = userResult.data;
                                    ChatController.getInstance().r = new RMQChat("amqp://tmes:tmespass@t-mes.xsrv.ru:5672/%2Ftmes%2Fchat", 20, userResult.data.queue);
                                    return ChatController.getInstance().r.connectAndSubscribe();

                                //{"type":"rabbit","version":1,"date":1490606701,"headers":[],
                            }

                        })
                        .subscribe(new Subscriber<RabbitMQ>() {
                            @Override
                            public void onCompleted() {
                                Log.d(TAG, "onCompleted");
                                //вся щепочка выполнена успешно
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.d(TAG, "onError " + e.getMessage());
                                e.printStackTrace();
                                //где то произошел косяк, либо сайт, илбо реббит, либо интернет
                                Toast.makeText(getContext(), "Ошибка авторизации", Toast.LENGTH_LONG);
                            }

                            @Override
                            public void onNext(RabbitMQ rabbitMQ) {
                                Log.d(TAG, "onNext");
                                //выполняется 1 раз.
//                                init();
                                Controller.getInstance().fillAll();
//                                Controller.getInstance().fillContacts();
//                                Controller.getInstance().fillChats();
//                                Controller.getInstance().fillGroupChats();
                                FragmentMainFourTabScreen mainScreen = new FragmentMainFourTabScreen();
                                getFragmentManager()
                                        .beginTransaction()
                                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                                        .replace(R.id.container, mainScreen)
//                                        .addToBackStack(null)
                                        .commit();

                            }
                        });
//
//                REST.getInstance().login(emailField.getText().toString(), passwordField.getText().toString(), Controller.getInstance().deviceType(), Controller.getInstance().deviceId(), "")
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .subscribe(new REST.DataSubscriber<User>(){
//
//                            @Override
//                            public void onData(User data) {
//                                Log.w("technopark", "data "+data.name);
////                                Controller.getInstance().putUser(data);
//                                Controller.getInstance().getAuth().user = data;
//                                    r = new RMQChat("amqp://tmes:tmespass@t-mes.xsrv.ru:5672/%2Ftmes%2Fchat", 20, data.queue);
//                                    r.connectAndSubscribe();
////                                Log.w("technopark", "service "+MainActivity.service);
////                                MainActivity.service.auth(data);
//
//                                Controller.getInstance().fillContacts();
//                                Controller.getInstance().fillChats();
//                                Controller.getInstance().fillGroupChats();
//                                FragmentMainFourTabScreen mainScreen = new FragmentMainFourTabScreen();
//                                getFragmentManager()
//                                        .beginTransaction()
//                                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
//                                        .replace(R.id.container, mainScreen)
////                                        .addToBackStack(null)
//                                        .commit();
//                            }
//
//                            @Override
//                            public void onError(Throwable e) {
//                                Log.e("technopart", "error "+e.getLocalizedMessage());
//                            }
//                        });
/*                REST.getInstance().bar(controller.getAuth().user, controller.getAuth().wifiToken, currentPlace.id)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new REST.DataSubscriber<Bar>() {
                            @Override
                            public void onData(Bar data) {
                                Log.d(TAG, "Bar ik");
//                        guestList = data.inside;
                                guestsAdapter.setGuestList(data.inside);
//                        chatList = data.dialogs;
                                chatsAdapter.setChatList(data.dialogs);
                                setContent();
                            }
                        });*/
            }
        });

        /* the "ВОССТАНОВЛЕНИЕ ПАРОЛЯ" button */

        TextView mResetPassword = (TextView) mRootView.findViewById(R.id.fragment_authorization_tv_reset);
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


        cancelCross1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emailField.setText("");
                cancelCross1.setVisibility(View.GONE);
            }
        });

        /* the username input delete button */


        cancelCross2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                passwordField.setText("");
                cancelCross2.setVisibility(View.GONE);
            }
        });

//        EditText etEmail = (EditText) mRootView.findViewById(R.id.fragment_authorization_et_email);
//        final EditText etPassword = (EditText) mRootView.findViewById(R.id.fragment_authorization_et_password);
//        etEmail.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                etPassword.requestFocus();
//            }
//        });
    }

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {}

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {}

        @Override
        public void afterTextChanged(Editable editable) {
            mAuthorizationButton.setEnabled(!emailField.getText().toString().isEmpty() && !passwordField.getText().toString().isEmpty());
            cancelCross1.setVisibility(emailField.getText().toString().isEmpty() ? View.GONE : View.VISIBLE);
            cancelCross1.setVisibility(passwordField.getText().toString().isEmpty() ? View.GONE : View.VISIBLE);
        }
    };
}
