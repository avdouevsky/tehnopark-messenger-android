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
import com.mshvdvskgmail.technoparkmessenger.adapters.MediaListAdapter;
import com.mshvdvskgmail.technoparkmessenger.models.MediaList;

import java.util.ArrayList;

import ca.barrenechea.widget.recyclerview.decoration.StickyHeaderDecoration;

/**
 * Created by mshvdvsk on 21/03/2017.
 */

public class FragmentMediaList extends Fragment {
    private View mRootView;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private ArrayList<MediaList> media;
    private MediaListAdapter mAdapter;
    private StickyHeaderDecoration decor;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.pager_item_recycler_view, container, false);

        mRecyclerView = (RecyclerView) mRootView.findViewById(R.id.pager_recycler);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new GridLayoutManager(getActivity(), 1);
        mRecyclerView.setLayoutManager(mLayoutManager);

        media = new ArrayList<>();

        MediaList a = new MediaList();
        a.setDate("ЯНВАРЬ");
        media.add(a);
        media.add(a);
        media.add(a);

        MediaList b = new MediaList();
        b.setDate("ФЕВРАЛЬ");
        media.add(b);

        MediaList c = new MediaList();
        c.setDate("МАРТ");
        media.add(c);

        MediaList d = new MediaList();
        d.setDate("АПРЕЛЬ");
        media.add(d);

        MediaList e = new MediaList();
        e.setDate("ЯНВАРЬ");
        media.add(e);

        MediaList f = new MediaList();
        f.setDate("ИЮНЬ");
        media.add(f);

        MediaList g = new MediaList();
        g.setDate("АВГУСТ");
        media.add(g);

        MediaList h = new MediaList();
        h.setDate("СЕНТЯБРЬ");
        media.add(h);


        mAdapter = new MediaListAdapter(media, getContext());
        decor = new StickyHeaderDecoration(mAdapter);
        mRecyclerView.addItemDecoration(decor, 0);
        mRecyclerView.setAdapter(mAdapter);

        return mRootView;
    }
}
