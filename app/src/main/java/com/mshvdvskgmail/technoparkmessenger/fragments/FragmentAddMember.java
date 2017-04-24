package com.mshvdvskgmail.technoparkmessenger.fragments;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mshvdvskgmail.technoparkmessenger.R;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.mshvdvskgmail.technoparkmessenger.Controller;
import com.mshvdvskgmail.technoparkmessenger.adapters.AddMemberListAdapter;
import com.mshvdvskgmail.technoparkmessenger.adapters.SelectedContactsItemAdapter;
import com.mshvdvskgmail.technoparkmessenger.helpers.ArgsBuilder;

import java.util.ArrayList;
import java.util.List;

import com.mshvdvskgmail.technoparkmessenger.helpers.ICommand;
import com.mshvdvskgmail.technoparkmessenger.network.REST;
import com.mshvdvskgmail.technoparkmessenger.network.model.Chat;
import com.mshvdvskgmail.technoparkmessenger.network.model.ChatUser;
import com.mshvdvskgmail.technoparkmessenger.network.model.Result;
import com.mshvdvskgmail.technoparkmessenger.network.model.User;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;
import rx.functions.Func2;

/**
 * Created by mshvdvsk on 07/04/2017.
 */
public class FragmentAddMember extends BaseFragment {
    private RecyclerView rvContacts;
    private RecyclerView rvSelectedContacts;
    private LinearLayoutManager mLayoutManager;
    private TextView fragment_add_member_tv_selected_counter;

    private AddMemberListAdapter adapterUsers;
    private SelectedContactsItemAdapter adapterSelected;

    private Chat chat;

    public FragmentAddMember() {}

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_add_member, container, false);

        fragment_add_member_tv_selected_counter = (TextView) root.findViewById(R.id.fragment_add_member_tv_selected_counter);
        rvContacts = (RecyclerView) root.findViewById(R.id.fragment_add_member_rv_contacts);
        FrameLayout frameClose = (FrameLayout)root.findViewById(R.id.fragment_add_member_fl_cancel);
        FrameLayout apply = (FrameLayout)root.findViewById(R.id.fragment_add_member_fl_accept);
        rvSelectedContacts = (RecyclerView) root.findViewById(R.id.fragment_add_member_rv_selected_contacts);

        rvContacts.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rvContacts.setLayoutManager(mLayoutManager);

        rvSelectedContacts.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        rvSelectedContacts.setLayoutManager(mLayoutManager);

        chat = ArgsBuilder.create().chat();

        adapterUsers = new AddMemberListAdapter(getContext());
        adapterSelected = new SelectedContactsItemAdapter(getContext());
        rvContacts.setAdapter(adapterUsers);
        rvSelectedContacts.setAdapter(adapterSelected);

        //setAdapterContent();

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

        frameClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isAdded()) getActivity().onBackPressed();
            }
        });

        adapterSelected.setClickListener(new ICommand<User>() {
            @Override
            public void exec(User data) {
                adapterSelected.removeData(data);
                data.uiSelected = false;
                adapterUsers.notifyDataSetChanged();
                reCalcCounters();
            }
        });

        adapterUsers.setClickListener(new ICommand<User>() {
            @Override
            public void exec(User data) {
                data.uiSelected = !data.uiSelected;
                if(data.uiSelected)
                    adapterSelected.addData(data);
                else
                    adapterSelected.removeData(data);
                adapterUsers.notifyDataSetChanged();
                reCalcCounters();
            }
        });

        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(chat == null){
                    //create new
                    REST.getInstance().chat(Controller.getInstance().getAuth().getUser().token, adapterSelected.getData(), "Новый чат")
                        .subscribe(new REST.DataSubscriber<Chat>(){
                            @Override
                            public void onData(Chat data) {
                                if(isAdded()) getActivity().onBackPressed();
                            }
                        });
                }else{
                            REST.getInstance().group_add(Controller.getInstance().getAuth().getUser().token, chat, adapterSelected.getData())
                                .flatMap(new Func1<Result<Chat>, Observable<Result<Chat>>>() {
                                    @Override
                                    public Observable<Result<Chat>> call(Result<Chat> chatResult) {
                                        return REST.getInstance().group_remove(Controller.getInstance().getAuth().getUser().token, chat, adapterSelected.getData());
                                    }
                                }).subscribe(new REST.DataSubscriber<Chat>() {
                                @Override
                                public void onData(Chat data) {
                                    if(isAdded()) getActivity().onBackPressed();
                                }
                            });
                }
            }
        });

        loadData();

        return root;
    }

    private void reCalcCounters(){
        fragment_add_member_tv_selected_counter.setText(String.format("%d / %d", adapterSelected.getItemCount(), adapterUsers.getItemCount()));
    }

    private void loadData(){
        REST.getInstance().contacts(Controller.getInstance().getAuth().getUser().token, null)
                .subscribe(new REST.DataSubscriber<List<User>>() {
                    @Override
                    public void onData(List<User> data) {
                        adapterUsers.setData(data);
                        setAdapterContent(data);
                        reCalcCounters();
                    }
                });
    }

    private void setAdapterContent(List<User> contacts) {
        if(chat != null){
            List<User> users = new ArrayList<>();
            for (ChatUser cu: chat.users) if(cu.user != null) users.add(cu.user);
            for(User u : contacts) if(users.contains(u)) u.uiSelected = true;
            users.clear();
            for(User u : contacts) if(u.uiSelected) users.add(u);
            adapterSelected.setData(users);
        }
    }
}