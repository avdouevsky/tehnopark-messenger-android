package com.mshvdvskgmail.technoparkmessenger.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mshvdvskgmail.technoparkmessenger.R;
import com.mshvdvskgmail.technoparkmessenger.adapters.GroupListAdapter;
import com.mshvdvskgmail.technoparkmessenger.models.GroupsListItem;

import java.util.ArrayList;

/**
 * Created by mshvdvsk on 29/03/2017.
 */

public class FragmentGroupsList extends Fragment {
    private View mRootView;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private ArrayList<GroupsListItem> groups;
    private GroupListAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.recycler_view_with_search, container, false);

        mRecyclerView = (RecyclerView) mRootView.findViewById(R.id.recycler_view_all);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);

        groups = new ArrayList<>();

        GroupsListItem dummyObject1 = new GroupsListItem();
        dummyObject1.setName("Отдел продаж");
        dummyObject1.setLastMessage("Документы подписали");
        dummyObject1.setTime("11:20");

        GroupsListItem dummyObject2 = new GroupsListItem();
        dummyObject2.setName("Отпуск");
        dummyObject2.setLastMessage("Вернусь в понедельник");
        dummyObject2.setTime("вчера");

        GroupsListItem dummyObject3 = new GroupsListItem();
        dummyObject3.setName("Корпоратив");
        dummyObject3.setLastMessage("Место отличное");
        dummyObject3.setTime("15.01.2017");

        groups.add(dummyObject1);
        groups.add(dummyObject2);
        groups.add(dummyObject3);

//        for (int i = 0; i < 10; i++){
//            contacts.add(dummyObject);
//        }
        adapter = new GroupListAdapter(groups, getContext());
        mRecyclerView.setAdapter(adapter);

        return mRootView;
    }
}

