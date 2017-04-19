package com.mshvdvskgmail.technoparkmessenger.activities;


import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.mshvdvskgmail.technoparkmessenger.Controller;
import com.mshvdvskgmail.technoparkmessenger.Fragments;
import com.mshvdvskgmail.technoparkmessenger.R;
import com.mshvdvskgmail.technoparkmessenger.events.ErrorEvent;
import com.mshvdvskgmail.technoparkmessenger.events.SwitchFragmentEvent;
import com.mshvdvskgmail.technoparkmessenger.fragments.BaseFragment;
import com.mshvdvskgmail.technoparkmessenger.events.DataLoadEvent;
import com.mshvdvskgmail.technoparkmessenger.fragments.FragmentMainFourTabScreen;
import com.mshvdvskgmail.technoparkmessenger.network.model.Message;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import su.bnet.flowcontrol.BundleCommand;
import su.bnet.flowcontrol.FragmentNavigator;
import su.bnet.flowcontrol.Router;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends AppCompatActivity {
    private final static String TAG = MainActivity.class.toString();

    private Router<Fragments, BundleCommand> router;
    private FragmentNavigator<Fragments, BundleCommand> navigator;

    protected Controller controller = Controller.getInstance();

    private void initRoute(){
        Log.v(TAG, "initRoute");
        router = new Router<>();
        //clear stack
        if(getFragmentManager().getBackStackEntryCount() != 0){
            Log.w(TAG, "we have a stack!");
            for (int i = 0; i < getFragmentManager().getBackStackEntryCount(); i++) {
                getFragmentManager().popBackStack();
            }
        }
        navigator = new FragmentNavigator<Fragments, BundleCommand>(router, getSupportFragmentManager(), R.id.container) {
            @Override
            protected Fragment createFragment(Fragments screenKey, Bundle data) {
                return BaseFragment.newInstance(screenKey, data);
            }
        };

        for(Fragments s : Fragments.values()){
            router.add(s, new MainCommand(s));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initRoute();

        navigator.forwardTo(Fragments.AUTHORIZATION);
    }

    @Override
    protected void onStart(){
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onResume(){
        super.onResume();
/*        if(service == null){
            Intent intent = new Intent(this, RabbitMQService.class);
            if(!bindService(intent, mConnection, Context.BIND_AUTO_CREATE)){
                Log.w(TAG, "service bind error!");
            }
        }*/
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
//        if (service != null) {
//            unbindService(mConnection);
//            service = null;
//        }
//        super.onPause();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    public void setFragment(Fragment frag){
// todo
//        getSupportFragmentManager()
//                .beginTransaction()
//                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
//                .replace(R.id.container, frag)
//                .addToBackStack(null)
//                .commit();
    }


    public void  executeAction(String action, Object value) {
//todo
//        if(action.equals("showProfile")){
//            FragmentProfile fragment = new FragmentProfile();
//            Bundle mBundle = new Bundle();
//            User user = (User)value;
//            mBundle.putSerializable("user", user);
//            fragment.setArguments(mBundle);
//            this.setFragment(fragment);
//        }else if(action.equals("showChat")){
//            FragmentChat fragment = new FragmentChat();
//            Bundle mBundle = new Bundle();
//            Chat chat = (Chat)value;
//            mBundle.putSerializable("chat", chat);
//            fragment.setArguments(mBundle);
//            this.setFragment(fragment);
//        }else if(action.equals("showGroupSettings")) {
//            FragmentGroupsSettings fragment = new FragmentGroupsSettings();
//            Bundle mBundle = new Bundle();
//            Chat chat = (Chat) value;
//            mBundle.putSerializable("chat", chat);
//            fragment.setArguments(mBundle);
//            this.setFragment(fragment);
//        }else if(action.equals("showGroupSettingsMembers")){
//            FragmentAddMember fragment = new FragmentAddMember();
//            Bundle mBundle = new Bundle();
//            Chat chat = (Chat)value;
//            mBundle.putSerializable("chat", chat);
//            fragment.setArguments(mBundle);
//            this.setFragment(fragment);
//        }else if(action.equals("showCreateGroup")){
//            FragmentAddMember fragment = new FragmentAddMember();
//            Bundle mBundle = new Bundle();
////            Chat chat = (Chat)value;
////            mBundle.putSerializable("chat", chat);
////            fragment.setArguments(mBundle);
//            this.setFragment(fragment);
//        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public final void onEvent(DataLoadEvent event) {
        eventDataLoad(event.dataSource);
    }

    protected void eventDataLoad(String dataSource){
        Log.v("Technopark", "dataSource "+dataSource);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public final void onEvent(Message message) {
        eventMessage(message);
    }

    protected void eventMessage(Message message){
        Log.v("Technopark", "new message "+message);
    }

    @Override
    public void onBackPressed() {
        if(!navigator.back()){
//            Intent intent = new Intent(this, LoginActivity.class);
//                startActivity(intent);
            AlertDialog alert = new AlertDialog.Builder(this)
                    .setMessage("Закрытие программы! Вы действительно хотите выйти?")
                    .setNegativeButton("Нет", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            finish();
                        }
                    })
                    .show();
//                finish();
        }
//        AlertDialog alertDialog = new AlertDialog.Builder(this)
//                .setMessage("Закрыть приложение?")
//                .setPositiveButton("Да", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        dialogInterface.dismiss();
//                        MainActivity.super.onBackPressed();
//                    }
//                })
//                .setNegativeButton("Нет", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        dialogInterface.dismiss();
//                    }
//                }).show();
    }

    public void take_me_back (View v) {
//TODO
//        FragmentMainFourTabScreen main = new FragmentMainFourTabScreen();
//        getSupportFragmentManager()
//                .beginTransaction()
//                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
//                .replace(R.id.container, main)
//                .addToBackStack(null)
//                .commit();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(SwitchFragmentEvent event) {
        Log.v(TAG, "onMessageEvent SwitchFragmentEvent");
        switch (event.getDirection()){
            case REPLACE:
                navigator.replaceTo(event.getStates(), event.getBundle());
                break;
            case FROWARD:
            default:
                navigator.forwardTo(event.getStates(), event.getBundle());
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(ErrorEvent event) {
        new AlertDialog.Builder(this).setMessage(event.getThrowable().getMessage()).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
    }

    private class MainCommand extends BundleCommand{
        private Fragments state;

        public MainCommand(Fragments state) {
            this.state = state;
        }

        @Override
        public void forward(Bundle data) {
//            if(toolbar != null){
//                //set title
//                String title = ArgsBuilder.create(data).title();
//                if(title != null) toolbar.setTitle(title);
//                else toolbar.setTitle(state.getTitle());
//            }
//            if(navigationView != null){
//                frameNavigationBar.setVisibility(state.isNavigationBar() ? View.VISIBLE : View.GONE);
//                toggle.setDrawerIndicatorEnabled(state.isNavigationBar());
//            }

            super.forward(data);
        }

        @Override
        public void rollback() {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//            toolbar.getTitleView().setState(ActiveTextView.State.PASSIVE);
            invalidateOptionsMenu();
//            toggle.setDrawerIndicatorEnabled(false);
        }
    }
}
