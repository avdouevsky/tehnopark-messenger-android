package com.mshvdvskgmail.technoparkmessenger.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mshvdvskgmail.technoparkmessenger.R;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.mshvdvskgmail.technoparkmessenger.Controller;
import com.mshvdvskgmail.technoparkmessenger.R;
import com.mshvdvskgmail.technoparkmessenger.activities.MainActivity;
import com.mshvdvskgmail.technoparkmessenger.adapters.AddMemberListAdapter;
import com.mshvdvskgmail.technoparkmessenger.adapters.SearchItemsAdapter;
import com.mshvdvskgmail.technoparkmessenger.adapters.SelectedContactsItemAdapter;
import com.mshvdvskgmail.technoparkmessenger.models.ContactsListItem;
import com.mshvdvskgmail.technoparkmessenger.models.SearchItem;

import java.util.ArrayList;

import com.mshvdvskgmail.technoparkmessenger.network.REST;
import com.mshvdvskgmail.technoparkmessenger.network.model.Chat;
import com.mshvdvskgmail.technoparkmessenger.network.model.ChatUser;
import com.mshvdvskgmail.technoparkmessenger.network.model.User;

import java.util.ArrayList;

import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by mshvdvsk on 07/04/2017.
 */

public class FragmentAddMember extends BaseFragment {
    private View mRootView;
    private RecyclerView rvContacts;
    private RecyclerView rvSelectedContacts;
    private LinearLayoutManager mLayoutManager;
    private ArrayList<User> contacts;
    private ArrayList<User> selected_contacts;
    private AddMemberListAdapter adapter;
    private SelectedContactsItemAdapter adapterSelected;
    private AlertDialog alert;
    private Chat activeChat;

    public FragmentAddMember() {}

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        activeChat = (Chat) getArguments().getSerializable("chat");

        mRootView = inflater.inflate(R.layout.fragment_add_member, container, false);
        setAdapterContent(mRootView);

//        llSearch = (LinearLayout) mRootView.findViewById(R.id.fragment_add_member_ll_search_bar);
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
        selected_contacts = new ArrayList<>();
        if(activeChat != null){
            for (ChatUser tmp_user:activeChat.users) {
                selected_contacts.add(tmp_user.user);
            }
        }

        contacts.addAll(Controller.getInstance().getContacts());

//        ContactsListItem a = new ContactsListItem();
//        a.setName("Константин Константинопольский");
//        a.setOfficePosition("MOOO DUCK A");
//
//        ContactsListItem b = new ContactsListItem();
//        b.setName("Константин Константинопольский");
//        b.setOfficePosition("MOOO DUCK B");
//
//        ContactsListItem c = new ContactsListItem();
//        c.setName("Константин Константинопольский");
//        c.setOfficePosition("MOOO DUCK C");
//
//        ContactsListItem d = new ContactsListItem();
//        d.setName("Константин Константинопольский");
//        d.setOfficePosition("MOOO DUCK D");
//
//        contacts.add(a);
//        contacts.add(b);
//        contacts.add(c);
//        contacts.add(d);

        adapter = new AddMemberListAdapter(contacts, selected_contacts, getContext(), this);
        adapterSelected = new SelectedContactsItemAdapter(selected_contacts, getContext(), this);
        rvContacts.setAdapter(adapter);
        rvSelectedContacts.setAdapter(adapterSelected);

        FrameLayout close = (FrameLayout)mRootView.findViewById(R.id.fragment_add_member_fl_cancel);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();
                fm.popBackStack();
            }
        });
        FrameLayout apply = (FrameLayout)mRootView.findViewById(R.id.fragment_add_member_fl_accept);
        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList <String> tmp_users = new ArrayList<String>();
                ArrayList <String> chat_name = new ArrayList<String>();
                for (User tmp_user: selected_contacts) {
                    tmp_users.add(tmp_user.unique_id);
                    chat_name.add(tmp_user.name);
                }
                REST.getInstance().chat(Controller.getInstance().getAuth().getUser().token.session_id, Controller.getInstance().getAuth().getUser().token.token, TextUtils.join(",", tmp_users), TextUtils.join(", ", chat_name))
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new REST.DataSubscriber<Chat>(){
                            @Override
                            public void onCompleted(){
                                Controller.getInstance().fillChats();
                                Controller.getInstance().fillGroupChats();

//                                EventBus.getDefault().postSticky(new DataLoadEvent("Chats"));
                            }

                            @Override
                            public void onData(Chat data) {
                                FragmentManager fm = getFragmentManager();
                                fm.popBackStack();
                            }
                        });
            }
        });
    }

    @Override
    public void setSelected_contacts(ArrayList<User> selected_contacts){
        Log.w("addMember", "set selected called "+selected_contacts);
        this.selected_contacts = selected_contacts;
        adapter.notifyDataSetChanged();
        adapterSelected.notifyDataSetChanged();
    }

}