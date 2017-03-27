package com.mshvdvskgmail.technoparkmessenger.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mshvdvskgmail.technoparkmessenger.R;
import com.mshvdvskgmail.technoparkmessenger.adapters.DocumentsListAdapter;
import com.mshvdvskgmail.technoparkmessenger.events.MessageEvent;
import com.mshvdvskgmail.technoparkmessenger.models.DocumentsListItem;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import ca.barrenechea.widget.recyclerview.decoration.StickyHeaderDecoration;

/**
 * Created by mshvdvsk on 22/03/2017.
 */

public class FragmentDocumentsList extends Fragment {
    private final static String TAG = FragmentDocumentsList.class.toString();

    private View mRootView;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private ArrayList<DocumentsListItem> documents;
    private DocumentsListAdapter mAdapter;
    private StickyHeaderDecoration decor;
//    private boolean isPressed;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.pager_item_recycler_view, container, false);

        try{
            EventBus.getDefault().register(this);
        } catch (Exception e){}

        mRecyclerView = (RecyclerView) mRootView.findViewById(R.id.pager_recycler);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        documents = new ArrayList<>();

        DocumentsListItem a = new DocumentsListItem();
        a.setDataSent("ЯНВАРЬ");
        documents.add(a);

        DocumentsListItem z = new DocumentsListItem();
        z.setDataSent("ЯНВАРЬ");
        documents.add(z);

        DocumentsListItem x = new DocumentsListItem();
        x.setDataSent("ЯНВАРЬ");
        documents.add(x);

        DocumentsListItem b = new DocumentsListItem();
        b.setDataSent("Февраль");
        documents.add(b);

        DocumentsListItem c = new DocumentsListItem();
        c.setDataSent("Август");
        documents.add(c);

        DocumentsListItem d = new DocumentsListItem();
        d.setDataSent("Жопка");
        documents.add(d);

        DocumentsListItem f = new DocumentsListItem();
        f.setDataSent("Жопка");
        documents.add(f);

        DocumentsListItem g = new DocumentsListItem();
        g.setDataSent("Жопка");
        documents.add(g);

        mAdapter = new DocumentsListAdapter(documents, getContext());
        decor = new StickyHeaderDecoration(mAdapter);
        mRecyclerView.addItemDecoration(decor, 0);
        mRecyclerView.setAdapter(mAdapter);
        return mRootView;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        if (event.state){
            mAdapter.isAnimated = true;
            mAdapter.notifyDataSetChanged();
        } else {
            mAdapter.isAnimated = true;
            mAdapter.notifyDataSetChanged();
        }
    }

}