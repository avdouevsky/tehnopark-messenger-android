package com.mshvdvskgmail.technoparkmessenger.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mshvdvskgmail.technoparkmessenger.Controller;
import com.mshvdvskgmail.technoparkmessenger.R;
import com.mshvdvskgmail.technoparkmessenger.adapters.ContactsListAdapter;
import com.mshvdvskgmail.technoparkmessenger.adapters.SideSelector;
import com.mshvdvskgmail.technoparkmessenger.models.ContactsListItem;
import com.mshvdvskgmail.technoparkmessenger.network.REST;
import com.mshvdvskgmail.technoparkmessenger.network.model.User;

import java.util.ArrayList;
import java.util.List;

import ca.barrenechea.widget.recyclerview.decoration.StickyHeaderDecoration;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by mshvdvsk on 19/03/2017.
 */

public class FragmentContactsList  extends Fragment {

    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private ArrayList<User> contacts = new ArrayList<>();
    private ContactsListAdapter mAdapter;
    private StickyHeaderDecoration decor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recycler_view_contacts, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_contacts_rv_contacts);
        SideSelector sideSelector = (SideSelector) view.findViewById(R.id.recycler_view_contacts_ss_side_selector);

        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);

        decor = new StickyHeaderDecoration(mAdapter);
        mRecyclerView.addItemDecoration(decor, 0);

        mAdapter = new ContactsListAdapter(contacts, getContext());
        mRecyclerView.setAdapter(mAdapter);

        sideSelector.setRecyclerView(mRecyclerView);

        loadData();

        return view;
    }

    private void loadData(){
        contacts.addAll(Controller.getInstance().getContacts());
//        ContactsListItem dummyObject1 = new ContactsListItem();
//        dummyObject1.setName("Пушкин1");
//        dummyObject1.setOfficePosition("fucker1");
//        dummyObject1.setOnline(true);
//
//        ContactsListItem dummyObject2 = new ContactsListItem();
//        dummyObject2.setName("Пушкин2");
//        dummyObject2.setOfficePosition("fucker2");
//        dummyObject2.setOnline(true);
//
//        ContactsListItem dummyObject3 = new ContactsListItem();
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
/*
        int counter = 0;

        for (char ch : SideSelector.ALPHABET) {
            counter++;
            if ((counter % 2)==0){
                for (int i = 1; i <= 2; i++) {
                    ContactsListItem dummyObject = new ContactsListItem();
                    dummyObject.setName(String.valueOf(ch) + "-" + i);
                    dummyObject.setOfficePosition("CEO");
                    dummyObject.setOnline(true);
                    contacts.add(dummyObject);
                }
            }

        }*/


//        REST.getInstance().contacts(Controller.getInstance().getAuth().getUser().token.session_id, Controller.getInstance().getAuth().getUser().token.token, "")
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new REST.DataSubscriber<List<User>>(){
//                    @Override
//                    public void onData(List<User> data){
//                        contacts.addAll(data);
//                    }
//
//                    @Override
//                    public void onCompleted(){
//                        mAdapter.notifyDataSetChanged();
//                    }
//                });

/*        REST.getInstance().bar(controller.getAuth().user, controller.getAuth().wifiToken, currentPlace.id)
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

//        mAdapter = new ContactsListAdapter(contacts, getContext(), getActivity().getSupportFragmentManager());
    }
}
