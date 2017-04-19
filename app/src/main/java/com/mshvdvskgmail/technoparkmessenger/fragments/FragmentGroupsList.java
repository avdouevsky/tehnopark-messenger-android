package com.mshvdvskgmail.technoparkmessenger.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.mshvdvskgmail.technoparkmessenger.R;
import com.mshvdvskgmail.technoparkmessenger.adapters.GroupListAdapter;
import com.mshvdvskgmail.technoparkmessenger.models.GroupsListItem;
import android.support.annotation.MainThread;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mshvdvskgmail.technoparkmessenger.Controller;
import com.mshvdvskgmail.technoparkmessenger.R;
import com.mshvdvskgmail.technoparkmessenger.adapters.ChatsListAdapter;
import com.mshvdvskgmail.technoparkmessenger.adapters.GroupListAdapter;
import com.mshvdvskgmail.technoparkmessenger.events.DataLoadEvent;
import com.mshvdvskgmail.technoparkmessenger.models.ChatsListItem;
import com.mshvdvskgmail.technoparkmessenger.models.GroupsListItem;
import com.mshvdvskgmail.technoparkmessenger.network.model.Chat;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

/**
 * Created by mshvdvsk on 29/03/2017.
 */

public class FragmentGroupsList extends Fragment {
    private View mRootView;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private ArrayList<Chat> groups;
    private GroupListAdapter mAdapter;

    @Override
    public void onPause() {
        EventBus.getDefault().unregister(this);
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.pager_item_recycler_view_with_search, container, false);

        mRecyclerView = (RecyclerView) mRootView.findViewById(R.id.recycler_view_all);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);

//        llSearch = (LinearLayout) mRootView.findViewById(R.id.ll_all_ll_search_bar);
//        llSearch.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                FragmentSearch search = new FragmentSearch();
//                getActivity().getSupportFragmentManager().beginTransaction()
//                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
//                        .replace(R.id.container, search)
//                        .addToBackStack(null)
//                        .commit();
//            }
//        });

        groups = new ArrayList<>();
        groups.addAll(Controller.getInstance().getGroupChats());

        mAdapter = new GroupListAdapter(groups, getContext());
        mRecyclerView.setAdapter(mAdapter);

        return mRootView;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public final void onEvent(DataLoadEvent event) {
        eventDataLoad(event.dataSource);
    }

    protected void eventDataLoad(String dataSource){
        if(dataSource.equals("Groups")) {
            Log.w("GroupsList", "eventDataLoad " + dataSource);
            groups.clear();
            groups.addAll(Controller.getInstance().getGroupChats());
            mAdapter.notifyDataSetChanged();
        }
    }

}

