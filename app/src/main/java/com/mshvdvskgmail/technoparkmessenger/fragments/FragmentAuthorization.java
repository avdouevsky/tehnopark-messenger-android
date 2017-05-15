package com.mshvdvskgmail.technoparkmessenger.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.iid.FirebaseInstanceId;
import com.mshvdvskgmail.technoparkmessenger.BuildConfig;
import com.mshvdvskgmail.technoparkmessenger.ChatController;
import com.mshvdvskgmail.technoparkmessenger.Consts;
import com.mshvdvskgmail.technoparkmessenger.Controller;
import com.mshvdvskgmail.technoparkmessenger.Fragments;
import com.mshvdvskgmail.technoparkmessenger.R;
import com.mshvdvskgmail.technoparkmessenger.TechnoparkApp;
import com.mshvdvskgmail.technoparkmessenger.events.SwitchFragmentEvent;
import com.mshvdvskgmail.technoparkmessenger.network.REST;
import com.mshvdvskgmail.technoparkmessenger.network.RMQChat;
import com.mshvdvskgmail.technoparkmessenger.network.RabbitMQ;
import com.mshvdvskgmail.technoparkmessenger.network.model.Result;
import com.mshvdvskgmail.technoparkmessenger.network.model.User;
import com.mshvdvskgmail.technoparkmessenger.phone.SipService;

import org.greenrobot.eventbus.EventBus;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;

/**
 * Created by mshvdvsk on 07/03/2017.
 */

public class FragmentAuthorization extends BaseFragment{
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
        FirebaseApp.initializeApp(getActivity());

        cancelCross1 = (ImageView) mRootView.findViewById(R.id.fragment_authorization_image_cancel_cross_first);
        cancelCross2 = (ImageView) mRootView.findViewById(R.id.fragment_authorization_image_cancel_cross_second);
        mAuthorizationButton = (TextView) mRootView.findViewById(R.id.fragment_authorization_tv_enter);
        emailField = (EditText) mRootView.findViewById(R.id.fragment_authorization_et_email);
        passwordField = (EditText) mRootView.findViewById(R.id.fragment_authorization_et_password);

//        mAuthorizationButton.setEnabled(false);
        if(BuildConfig.DEBUG){
            emailField.setText("phone2");
            passwordField.setText("Hello123test");
//            mAuthorizationButton.setEnabled(true);
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
                String stringTrimmer = emailField.getText().toString().trim();
                REST.getInstance().login(stringTrimmer, passwordField.getText().toString(), Controller.getInstance().deviceType(), Controller.getInstance().deviceId(), FirebaseInstanceId.getInstance().getToken())
                        .flatMap(new Func1<Result<User>, Observable<RabbitMQ>>() {
                            @Override
                            public Observable<RabbitMQ> call(Result<User> userResult) {
                                Controller.getInstance().getAuth().user = userResult.data;
                                ChatController.getInstance().r = new RMQChat(Consts.Network.AMQP_API, 20, userResult.data.queue);

                                return ChatController.getInstance().r.connectAndSubscribe();
                            }
                        })
                        .subscribe(new Subscriber<RabbitMQ>() {
                            @Override
                            public void onCompleted() {
                                Log.d(TAG, "onCompleted");
                                //вся цепочка выполнена успешно
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.d(TAG, "onError " + e.getMessage());
                                Log.w(TAG, e);
                                e.printStackTrace();
                                //где то произошел косяк, либо сайт, илбо реббит, либо интернет
                                Toast.makeText(getActivity(), "Ошибка авторизации", Toast.LENGTH_LONG);
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
                                REST.getInstance().createSecurePicasso(getActivity(), Controller.getInstance().getAuth().getUser().token);
                                EventBus.getDefault().postSticky(new SwitchFragmentEvent(Fragments.MAIN_FOUR_TAB_SCREEN, null, SwitchFragmentEvent.Direction.REPLACE));
//                                FragmentMainFourTabScreen mainScreen = new FragmentMainFourTabScreen();
//                                getFragmentManager()
//                                        .beginTransaction()
////                                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
//                                        .replace(R.id.container, mainScreen)
////                                        .addToBackStack(null)
//                                        .commit();
//                                Intent intent = new Intent(TechnoparkApp.getContext(), SipService.class);
//                                intent.putExtra(SipService.COMMAND, SipService.Commands.CONNECT);
//                                intent.putExtra(SipService.USER, Controller.getInstance().getAuth().getUser());
//                                TechnoparkApp.getContext().startService(intent);
                                SipService.command(SipService.Commands.CONNECT, Controller.getInstance().getAuth().getUser());
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
                EventBus.getDefault().postSticky(new SwitchFragmentEvent(Fragments.RESET_PASSWORD, null));
//                FragmentResetPassword authorization = new FragmentResetPassword();
//                getFragmentManager()
//                        .beginTransaction()
//                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
//                        .replace(R.id.container, authorization)
//                        .addToBackStack(null)
//                        .commit();
            }
        });

        cancelCross1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emailField.setText("");
                cancelCross1.setVisibility(View.GONE);
            }
        });

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
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {}

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {}

        @Override
        public void afterTextChanged(Editable editable) {
//            mAuthorizationButton.setEnabled(!emailField.getText().toString().isEmpty() && !passwordField.getText().toString().isEmpty());
            cancelCross1.setVisibility(emailField.getText().toString().isEmpty() ? View.GONE : View.VISIBLE);
            cancelCross2.setVisibility(passwordField.getText().toString().isEmpty() ? View.GONE : View.VISIBLE);
        }
    };
}
