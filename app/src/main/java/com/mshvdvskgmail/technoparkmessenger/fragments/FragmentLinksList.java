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
import com.mshvdvskgmail.technoparkmessenger.events.MessageEvent;
import com.mshvdvskgmail.technoparkmessenger.models.LinksItem;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import ca.barrenechea.widget.recyclerview.decoration.StickyHeaderDecoration;

/**
 * Created by mshvdvsk on 23/03/2017.
 */

public class FragmentLinksList extends BaseFragment {

    private final static String TAG = FragmentLinksList.class.toString();


    private View rootView;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private ArrayList<LinksItem> links;
    private LinksListAdapter adapter;
    private StickyHeaderDecoration decor;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.recycler_view_basic, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view_all);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        links = new ArrayList<>();

        LinksItem a = new LinksItem();
        a.setLinkSent("ЯНВАРЬ");
        links.add(a);

        LinksItem b = new LinksItem();
        b.setLinkSent("ЯНВАРЬ");
        links.add(b);

        LinksItem c = new LinksItem();
        c.setLinkSent("ЯНВАРЬ");
        links.add(c);

        LinksItem d = new LinksItem();
        d.setLinkSent("Февраль");
        links.add(d);

        LinksItem e = new LinksItem();
        e.setLinkSent("Август");
        links.add(e);

        LinksItem f = new LinksItem();
        f.setLinkSent("Жопка");
        links.add(f);

        LinksItem g = new LinksItem();
        g.setLinkSent("Жопка");
        links.add(g);

        LinksItem h = new LinksItem();
        h.setLinkSent("Жопка");
        links.add(h);


        adapter = new LinksListAdapter(links, getContext());
        decor = new StickyHeaderDecoration(adapter);
        recyclerView.addItemDecoration(decor, 0);
        recyclerView.setAdapter(adapter);

        return rootView;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
//        Log.d(TAG, "onMessageEvent");
//        if (event.state){
//            adapter.isAnimated = true;
//            adapter.notifyDataSetChanged();
//        } else {
//            adapter.isAnimated = true;
//            adapter.notifyDataSetChanged();
//        }
    }

}
