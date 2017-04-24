package com.mshvdvskgmail.technoparkmessenger.fragments;

import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.util.Log;

import com.mshvdvskgmail.technoparkmessenger.Fragments;
import com.mshvdvskgmail.technoparkmessenger.events.DataLoadEvent;
import com.mshvdvskgmail.technoparkmessenger.events.MessageEvent;
import com.mshvdvskgmail.technoparkmessenger.events.SetTitleEvent;
import com.mshvdvskgmail.technoparkmessenger.helpers.ArgsBuilder;
import com.mshvdvskgmail.technoparkmessenger.network.model.Message;
import com.mshvdvskgmail.technoparkmessenger.network.model.User;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

/**
 * Created by vlad on 07.04.17.
 */

public class BaseFragment extends Fragment {
    private final static String TAG = BaseFragment.class.toString();

    public static Fragment newInstance(Fragments screenKey, Bundle data) {
        Fragment fragment;

        switch (screenKey){
            case AUTHORIZATION:
                fragment = new FragmentAuthorization();
                fragment.setArguments(data);
                return fragment;
            case MAIN_FOUR_TAB_SCREEN:
                fragment = new FragmentMainFourTabScreen();
                fragment.setArguments(data);
                return fragment;
            case CHAT:
                fragment = new FragmentChat();
                fragment.setArguments(data);
                return fragment;
            case PROFILE:
                fragment = new FragmentProfile();
                fragment.setArguments(data);
                return fragment;
//            case INCOMING_CALL:
//                fragment = new FragmentIncomingCall();
//                fragment.setArguments(data);
//                return fragment;
            case GROUPS_SETTINGS:
                fragment = new FragmentGroupsSettings();
                fragment.setArguments(data);
                return fragment;
            default:
                fragment = new SimpleFragment();
                if(data == null) data = new Bundle();
                data.putString(SimpleFragment.TEXT, screenKey.toString());
                fragment.setArguments(data);
                return fragment;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Override
    public void onResume() {
        super.onResume();


        if(ArgsBuilder.create(getArguments()).title() != null){
            EventBus.getDefault().post(new SetTitleEvent(ArgsBuilder.create(getArguments()).title()));
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public final void onEvent(DataLoadEvent event) {
        eventDataLoad(event.dataSource);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public final void onEvent(MessageEvent event) {
        eventMessage(event.getMessage());
    }


    protected void eventDataLoad(String dataSource){
        Log.d(TAG, "event dataSource");
    }

    protected void eventMessage(Message message) {
        Log.d(TAG, "event message");
    }


    public void setSelected_contacts(ArrayList<User> selected_contacts){

    }
}
