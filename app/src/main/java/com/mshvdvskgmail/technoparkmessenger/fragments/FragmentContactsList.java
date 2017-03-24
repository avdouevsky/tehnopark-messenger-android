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
import com.mshvdvskgmail.technoparkmessenger.adapters.ContactsListAdapter;
import com.mshvdvskgmail.technoparkmessenger.adapters.SideSelector;
import com.mshvdvskgmail.technoparkmessenger.models.ModelChatsListItem;
import com.mshvdvskgmail.technoparkmessenger.models.ModelContactsListItem;

import java.util.ArrayList;

import ca.barrenechea.widget.recyclerview.decoration.StickyHeaderDecoration;

/**
 * Created by mshvdvsk on 19/03/2017.
 */

public class FragmentContactsList  extends Fragment {

    private View mRootView;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private ArrayList<ModelContactsListItem> contacts;
    private ContactsListAdapter mAdapter;
    private StickyHeaderDecoration decor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.pager_item_recycler_view_contacts, container, false);

        mRecyclerView = (RecyclerView) mRootView.findViewById(R.id.pager_recycler);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);

        contacts = new ArrayList<>();

//        ModelContactsListItem dummyObject1 = new ModelContactsListItem();
//        dummyObject1.setName("Пушкин1");
//        dummyObject1.setOfficePosition("fucker1");
//        dummyObject1.setOnline(true);
//
//        ModelContactsListItem dummyObject2 = new ModelContactsListItem();
//        dummyObject2.setName("Пушкин2");
//        dummyObject2.setOfficePosition("fucker2");
//        dummyObject2.setOnline(true);
//
//        ModelContactsListItem dummyObject3 = new ModelContactsListItem();
//        dummyObject3.setName("Пушкин3");
//        dummyObject3.setOfficePosition("fucker3");
//        dummyObject3.setOnline(true);
//
//        for (int i = 0; i < 20; i++){
//            contacts.add(dummyObject1);
//            contacts.add(dummyObject2);
//            contacts.add(dummyObject3);
//        }

//        contacts.add(dummyObject1);
//        contacts.add(dummyObject2);
//        contacts.add(dummyObject3);

        int counter = 0;

        for (char ch : SideSelector.ALPHABET) {
            counter++;
            if ((counter % 2)==0){
                for (int i = 1; i <= 2; i++) {
                    ModelContactsListItem dummyObject = new ModelContactsListItem();
                    dummyObject.setName(String.valueOf(ch) + "-" + i);
                    dummyObject.setOfficePosition("CEO");
                    dummyObject.setOnline(true);
                    contacts.add(dummyObject);
                }
            }

        }

        mAdapter = new ContactsListAdapter(contacts, getContext());
        decor = new StickyHeaderDecoration(mAdapter);
        mRecyclerView.addItemDecoration(decor, 0);
        mRecyclerView.setAdapter(mAdapter);

        SideSelector sideSelector = (SideSelector) mRootView.findViewById(R.id.side_selector);
        sideSelector.setRecyclerView(mRecyclerView);

        return mRootView;
    }
}
