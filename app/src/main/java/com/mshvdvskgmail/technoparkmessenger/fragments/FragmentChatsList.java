package com.mshvdvskgmail.technoparkmessenger.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mshvdvskgmail.technoparkmessenger.ChatController;
import com.mshvdvskgmail.technoparkmessenger.Controller;
import com.mshvdvskgmail.technoparkmessenger.R;
import com.mshvdvskgmail.technoparkmessenger.adapters.ChatsListAdapter;
import com.mshvdvskgmail.technoparkmessenger.events.DataLoadEvent;
import com.mshvdvskgmail.technoparkmessenger.models.ChatsListItem;
import com.mshvdvskgmail.technoparkmessenger.network.model.Chat;
import com.mshvdvskgmail.technoparkmessenger.network.model.Message;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mshvdvsk on 17/03/2017.
 */

public class FragmentChatsList extends BaseFragment {
    private View mRootView;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private ArrayList<Chat> chats;
    private ChatsListAdapter mAdapter;

    @Override
    public void onPause() {
//        EventBus.getDefault().unregister(this);
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
//        EventBus.getDefault().register(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.pager_item_recycler_view, container, false);

        mRecyclerView = (RecyclerView) mRootView.findViewById(R.id.pager_recycler);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);

        chats = new ArrayList<Chat>();
        chats.addAll(Controller.getInstance().getChats());
/*
        ChatsListItem dummyObject1 = new ChatsListItem();
        dummyObject1.setName("Пушкин");
        dummyObject1.setLastLine("you, fat fuck");
        dummyObject1.setTime("вчера");
        dummyObject1.setOnline(true);
        dummyObject1.setHasNew(true);
        dummyObject1.setNewCount(5);

        ChatsListItem dummyObject2 = new ChatsListItem();
        dummyObject2.setName("Кукушкин");
        dummyObject2.setLastLine("I am a poet!");
        dummyObject2.setTime("17:00");
        dummyObject2.setOnline(false);
        dummyObject2.setHasNew(false);
        dummyObject2.setNewCount(0);

        ChatsListItem dummyObject3 = new ChatsListItem();
        dummyObject3.setName("Птушкин");
        dummyObject3.setLastLine("I am your friend friend friend friend friend friend friend friend !");
        dummyObject3.setTime("03.03.1836");
        dummyObject3.setOnline(true);
        dummyObject3.setHasNew(true);
        dummyObject3.setNewCount(2);

        contacts.add(dummyObject1);
        contacts.add(dummyObject2);
        contacts.add(dummyObject3);*/

//        for (int i = 0; i < 10; i++){
//            contacts.add(dummyObject);
//        }
        mAdapter = new ChatsListAdapter(chats, getContext());
        mRecyclerView.setAdapter(mAdapter);

        return mRootView;
    }

//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public final void onEvent(DataLoadEvent event) {
//        eventDataLoad(event.dataSource);
//    }

    protected void eventDataLoad(String dataSource){
        if(dataSource.equals("Chats")) {
            Log.w("ChatsList", "eventDataLoad " + dataSource);
            chats.clear();
            chats.addAll(Controller.getInstance().getChats());
            Log.w("ChatsList", "size "+chats.size());
            mAdapter.notifyDataSetChanged();
        }
    }

    protected void eventMessage(Message message) {
        if(message.getType() == Message.Type.DIALOG) {
            Controller.getInstance().fillChats();
        }else if(message.getType() == Message.Type.ERROR){

        }
    }
}
