package com.mshvdvskgmail.technoparkmessenger.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mshvdvskgmail.technoparkmessenger.R;
import com.mshvdvskgmail.technoparkmessenger.adapters.DocumentsListAdapter;
import com.mshvdvskgmail.technoparkmessenger.adapters.MediaListAdapter;
import com.mshvdvskgmail.technoparkmessenger.models.MessageEvent;
import com.mshvdvskgmail.technoparkmessenger.models.ModelDocumentsItem;
import com.mshvdvskgmail.technoparkmessenger.models.ModelMediaList;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import ca.barrenechea.widget.recyclerview.decoration.StickyHeaderDecoration;

/**
 * Created by mshvdvsk on 22/03/2017.
 */

public class FragmentDocumentsList extends Fragment {
    private View mRootView;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private ArrayList<ModelDocumentsItem> documents;
    private DocumentsListAdapter mAdapter;
    private StickyHeaderDecoration decor;
    private boolean isPressed;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.pager_item_recycler_view, container, false);
        EventBus.getDefault().register(this);

        mRecyclerView = (RecyclerView) mRootView.findViewById(R.id.pager_recycler);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);

        documents = new ArrayList<>();

        ModelDocumentsItem a = new ModelDocumentsItem();
        a.setDataSent("ЯНВАРЬ");
        documents.add(a);
        documents.add(a);
        documents.add(a);

        ModelDocumentsItem b = new ModelDocumentsItem();
        b.setDataSent("Февраль");
        documents.add(b);

        ModelDocumentsItem c = new ModelDocumentsItem();
        c.setDataSent("Август");
        documents.add(c);

        ModelDocumentsItem d = new ModelDocumentsItem();
        d.setDataSent("Жопка");
        documents.add(d);
        documents.add(d);
        documents.add(d);




        mAdapter = new DocumentsListAdapter(documents, getContext());
        decor = new StickyHeaderDecoration(mAdapter);
        mRecyclerView.addItemDecoration(decor, 0);
        mRecyclerView.setAdapter(mAdapter);

        return mRootView;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
//        Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show();
        if (!isPressed) {
//            mAdapter.isAnimated = true;
            mAdapter.isPressed = true;
            mAdapter.notifyDataSetChanged();
            isPressed = true;
        } else {
//            mAdapter.isAnimated = true;
            mAdapter.isPressed = false;
            mAdapter.notifyDataSetChanged();
            isPressed = false;
        }

    }

}