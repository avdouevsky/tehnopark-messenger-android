package com.mshvdvskgmail.technoparkmessenger.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

    private View rootView;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private ArrayList<DocumentsListItem> documents;
    private DocumentsListAdapter adapter;
    private StickyHeaderDecoration decor;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.recycler_view_basic, container, false);

        try{
            EventBus.getDefault().register(this);
        } catch (Exception e){}

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view_all);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
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

        adapter = new DocumentsListAdapter(documents, getContext());
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