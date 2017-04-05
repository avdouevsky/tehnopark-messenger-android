package com.mshvdvskgmail.technoparkmessenger.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mshvdvskgmail.technoparkmessenger.R;
import com.mshvdvskgmail.technoparkmessenger.adapters.CallsListAdapter;
import com.mshvdvskgmail.technoparkmessenger.models.CallsList;

import java.util.ArrayList;

/**
 * Created by mshvdvsk on 20/03/2017.
 */

public class FragmentCallsList extends Fragment {
    private View mRootView;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private ArrayList<CallsList> calls;
    private CallsListAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.recycler_view_basic, container, false);

        mRecyclerView = (RecyclerView) mRootView.findViewById(R.id.recycler_view_all);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);

        calls = new ArrayList<>();

        CallsList dummyObject1 = new CallsList();
        dummyObject1.setName("Пушкин");
        dummyObject1.setTime("10:20");
        dummyObject1.setOnline(true);
        dummyObject1.setMissed(false);
        dummyObject1.setIncoming(true);

        CallsList dummyObject2 = new CallsList();
        dummyObject2.setName("Пушкин");
        dummyObject2.setTime("10:20");
        dummyObject2.setOnline(false);
        dummyObject2.setMissed(true);
        dummyObject2.setIncoming(true);

        CallsList dummyObject3 = new CallsList();
        dummyObject3.setName("Авдуевский Михаил");
        dummyObject3.setTime("Вчера");
        dummyObject3.setOnline(false);
        dummyObject3.setMissed(false);
        dummyObject3.setIncoming(false);

        calls.add(dummyObject1);
        calls.add(dummyObject2);
        calls.add(dummyObject3);

//        calls.add(dummyObject2);
//        calls.add(dummyObject3);

//        for (int i = 0; i < 10; i++){
//            contacts.add(dummyObject);
//        }
        mAdapter = new CallsListAdapter(calls, getContext());
        mRecyclerView.setAdapter(mAdapter);

        return mRootView;
    }
}