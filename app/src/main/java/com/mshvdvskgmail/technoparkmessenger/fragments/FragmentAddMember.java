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
import com.mshvdvskgmail.technoparkmessenger.adapters.SelectedContactsItemAdapter;
import com.mshvdvskgmail.technoparkmessenger.models.ContactsListItem;
import com.mshvdvskgmail.technoparkmessenger.models.SearchItem;

import java.util.ArrayList;

/**
 * Created by mshvdvsk on 07/04/2017.
 */

public class FragmentAddMember extends Fragment {
    private View mRootView;
    private RecyclerView rvContacts;
    private RecyclerView rvSelectedContacts;
    private LinearLayoutManager mLayoutManager;
    private ArrayList<ContactsListItem> contacts;
    private AddMemberListAdapter adapter;
    private SelectedContactsItemAdapter adapterSelected;
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

        rvSelectedContacts = (RecyclerView) mRootView.findViewById(R.id.fragment_add_member_rv_selected_contacts);
        rvSelectedContacts.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        rvSelectedContacts.setLayoutManager(mLayoutManager);

        contacts = new ArrayList<>();

        ContactsListItem a = new ContactsListItem();
        a.setName("Константин Константинопольский");
        a.setOfficePosition("MOOO DUCK A");

        ContactsListItem b = new ContactsListItem();
        b.setName("Константин Константинопольский");
        b.setOfficePosition("MOOO DUCK B");

        ContactsListItem c = new ContactsListItem();
        c.setName("Константин Константинопольский");
        c.setOfficePosition("MOOO DUCK C");

        ContactsListItem d = new ContactsListItem();
        d.setName("Константин Константинопольский");
        d.setOfficePosition("MOOO DUCK D");

        contacts.add(a);
        contacts.add(b);
        contacts.add(c);
        contacts.add(d);

        adapter = new AddMemberListAdapter(contacts, getContext());
        adapterSelected = new SelectedContactsItemAdapter(contacts, getContext());
        rvContacts.setAdapter(adapter);
        rvSelectedContacts.setAdapter(adapterSelected);
    }
}