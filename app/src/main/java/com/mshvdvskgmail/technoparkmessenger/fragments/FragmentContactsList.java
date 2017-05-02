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

public class FragmentContactsList  extends BaseFragment {
    private ContactsListAdapter mAdapter;
    private StickyHeaderDecoration decor;
    private RecyclerView mRecyclerView;
    private SideSelector sideSelector;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.recycler_view_contacts, container, false);


        mRecyclerView = (RecyclerView) root.findViewById(R.id.recycler_view_contacts_rv_contacts);

        sideSelector = (SideSelector) root.findViewById(R.id.recycler_view_contacts_ss_side_selector);

        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new ContactsListAdapter(getContext());
        mRecyclerView.setAdapter(mAdapter);
        //decor = new StickyHeaderDecoration(mAdapter);
        //mRecyclerView.addItemDecoration(decor, 0);



        loadData();

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void loadData(){
        REST.getInstance().contacts(Controller.getInstance().getAuth().getUser().token, null)
                .subscribe(new REST.DataSubscriber<List<User>>() {
                    @Override
                    public void onData(List<User> data) {
                        mAdapter.setData(data);
                        sideSelector.setRecyclerView(mRecyclerView);
                        decor = new StickyHeaderDecoration(mAdapter);
                        mRecyclerView.addItemDecoration(decor, 0);
                        sideSelector.invalidate();
                    }
                });
    }
}
