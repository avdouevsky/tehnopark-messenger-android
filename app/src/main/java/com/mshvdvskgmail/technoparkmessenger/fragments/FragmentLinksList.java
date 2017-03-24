package com.mshvdvskgmail.technoparkmessenger.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mshvdvskgmail.technoparkmessenger.R;
import com.mshvdvskgmail.technoparkmessenger.adapters.LinksListAdapter;
import com.mshvdvskgmail.technoparkmessenger.models.LinksItem;

import java.util.ArrayList;

import ca.barrenechea.widget.recyclerview.decoration.StickyHeaderDecoration;

/**
 * Created by mshvdvsk on 23/03/2017.
 */

public class FragmentLinksList extends Fragment {
    private View mRootView;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private ArrayList<LinksItem> links;
    private LinksListAdapter mAdapter;
    private StickyHeaderDecoration decor;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.pager_item_recycler_view, container, false);
//        EventBus.getDefault().register(this);

        mRecyclerView = (RecyclerView) mRootView.findViewById(R.id.pager_recycler);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);

        links = new ArrayList<>();

        LinksItem a = new LinksItem();
        a.setLinkSent("ЯНВАРЬ");
        links.add(a);
        links.add(a);
        links.add(a);

        LinksItem b = new LinksItem();
        b.setLinkSent("Февраль");
        links.add(b);

        LinksItem c = new LinksItem();
        c.setLinkSent("Август");
        links.add(c);

        LinksItem d = new LinksItem();
        d.setLinkSent("Жопка");
        links.add(d);
        links.add(d);
        links.add(d);

        mAdapter = new LinksListAdapter(links, getContext());
        decor = new StickyHeaderDecoration(mAdapter);
        mRecyclerView.addItemDecoration(decor, 0);
        mRecyclerView.setAdapter(mAdapter);

        return mRootView;
    }

//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void onMessageEvent(MessageEvent event) {
////        Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show();
//
//        mAdapter.
//
//    }

}
