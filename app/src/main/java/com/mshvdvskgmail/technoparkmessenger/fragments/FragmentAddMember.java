package com.mshvdvskgmail.technoparkmessenger.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.mshvdvskgmail.technoparkmessenger.R;
import com.mshvdvskgmail.technoparkmessenger.adapters.AddMemberListAdapter;
import com.mshvdvskgmail.technoparkmessenger.adapters.SearchItemsAdapter;
import com.mshvdvskgmail.technoparkmessenger.models.ContactsListItem;
import com.mshvdvskgmail.technoparkmessenger.models.SearchItem;

import java.util.ArrayList;

/**
 * Created by mshvdvsk on 07/04/2017.
 */

public class FragmentAddMember extends Fragment {
    private View mRootView;
    private RecyclerView rvContacts;
    private LinearLayoutManager mLayoutManager;
    private ArrayList<ContactsListItem> contacts;
    private AddMemberListAdapter adapter;
    private AlertDialog alert;

    public FragmentAddMember() {}

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mRootView = inflater.inflate(R.layout.fragment_add_member, container, false);
        setAdapterContent(mRootView);
        return mRootView;
    }

    private void setAdapterContent(View mRootView) {
        rvContacts = (RecyclerView) mRootView.findViewById(R.id.fragment_add_member_rv_contacts);
        rvContacts.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rvContacts.setLayoutManager(mLayoutManager);

        contacts = new ArrayList<>();
        ContactsListItem dummyObject = new ContactsListItem();
        for (int i = 0; i < 10; i++){
            dummyObject.setName("Константин Константинопольский");
            dummyObject.setOfficePosition("MOOO DUCK");
            contacts.add(dummyObject);
        }
        adapter = new AddMemberListAdapter(contacts, getContext());
        rvContacts.setAdapter(adapter);
    }

}