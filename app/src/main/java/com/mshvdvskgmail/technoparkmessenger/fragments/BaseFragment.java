package com.mshvdvskgmail.technoparkmessenger.fragments;

import android.support.v4.app.Fragment;

import android.util.Log;

import com.mshvdvskgmail.technoparkmessenger.events.DataLoadEvent;
import com.mshvdvskgmail.technoparkmessenger.events.MessageEvent;
import com.mshvdvskgmail.technoparkmessenger.network.model.Message;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by vlad on 07.04.17.
 */

public class BaseFragment extends Fragment{
    private final static String TAG = BaseFragment.class.toString();

    @Override
    public void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
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

}
