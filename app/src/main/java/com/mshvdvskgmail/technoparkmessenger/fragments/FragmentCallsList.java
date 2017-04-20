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
import com.mshvdvskgmail.technoparkmessenger.adapters.CallsListAdapter;
import com.mshvdvskgmail.technoparkmessenger.network.REST;
import com.mshvdvskgmail.technoparkmessenger.network.model.SipCall;
import com.mshvdvskgmail.technoparkmessenger.network.model.Result;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by mshvdvsk on 20/03/2017.
 */
public class FragmentCallsList extends BaseFragment {
    private CallsListAdapter mAdapter;

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.recycler_view_with_search, container, false);

        RecyclerView mRecyclerView = (RecyclerView) root.findViewById(R.id.recycler_view_all);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new CallsListAdapter(getContext());
        mRecyclerView.setAdapter(mAdapter);

        loadData();

        return root;
    }

    private void loadData(){
        REST.getInstance().calls(Controller.getInstance().getAuth().getUser().token, 0, 20)
        .subscribe(new REST.DataSubscriber<List<SipCall>>() {
            @Override
            public void onData(List<SipCall> data) {
                mAdapter.setData(data);
            }
        });
    }
}