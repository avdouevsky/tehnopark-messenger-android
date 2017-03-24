package com.mshvdvskgmail.technoparkmessenger.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mshvdvskgmail.technoparkmessenger.R;
import com.mshvdvskgmail.technoparkmessenger.adapters.ChatsListAdapter;
import com.mshvdvskgmail.technoparkmessenger.models.ModelChatsListItem;

import java.util.ArrayList;

/**
 * Created by mshvdvsk on 17/03/2017.
 */

public class FragmentChatsList extends Fragment {
    private View mRootView;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private ArrayList<ModelChatsListItem> contacts;
    private ChatsListAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.pager_item_recycler_view, container, false);

        mRecyclerView = (RecyclerView) mRootView.findViewById(R.id.pager_recycler);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);

        contacts = new ArrayList<>();

        ModelChatsListItem dummyObject1 = new ModelChatsListItem();
        dummyObject1.setName("Пушкин");
        dummyObject1.setLastLine("you, fat fuck");
        dummyObject1.setTime("вчера");
        dummyObject1.setOnline(true);
        dummyObject1.setHasNew(true);
        dummyObject1.setNewCount(5);

        ModelChatsListItem dummyObject2 = new ModelChatsListItem();
        dummyObject2.setName("Кукушкин");
        dummyObject2.setLastLine("I am a poet!");
        dummyObject2.setTime("17:00");
        dummyObject2.setOnline(false);
        dummyObject2.setHasNew(false);
        dummyObject2.setNewCount(0);

        ModelChatsListItem dummyObject3 = new ModelChatsListItem();
        dummyObject3.setName("Птушкин");
        dummyObject3.setLastLine("I am your friend friend friend friend friend friend friend friend !");
        dummyObject3.setTime("03.03.1836");
        dummyObject3.setOnline(true);
        dummyObject3.setHasNew(true);
        dummyObject3.setNewCount(2);

        contacts.add(dummyObject1);
        contacts.add(dummyObject2);
        contacts.add(dummyObject3);

//        for (int i = 0; i < 10; i++){
//            contacts.add(dummyObject);
//        }
        mAdapter = new ChatsListAdapter(contacts, getContext());
        mRecyclerView.setAdapter(mAdapter);

        return mRootView;
    }
}
