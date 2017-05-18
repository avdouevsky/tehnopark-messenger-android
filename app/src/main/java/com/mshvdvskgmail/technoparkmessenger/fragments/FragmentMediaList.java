package com.mshvdvskgmail.technoparkmessenger.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mshvdvskgmail.technoparkmessenger.Controller;
import com.mshvdvskgmail.technoparkmessenger.R;
import com.mshvdvskgmail.technoparkmessenger.adapters.MediaListAdapter;
import com.mshvdvskgmail.technoparkmessenger.events.MessageEvent;
import com.mshvdvskgmail.technoparkmessenger.helpers.ArgsBuilder;
import com.mshvdvskgmail.technoparkmessenger.models.MediaList;
import com.mshvdvskgmail.technoparkmessenger.network.REST;
import com.mshvdvskgmail.technoparkmessenger.network.model.Attachment;
import com.mshvdvskgmail.technoparkmessenger.network.model.User;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import ca.barrenechea.widget.recyclerview.decoration.StickyHeaderDecoration;

/**
 * Created by mshvdvsk on 21/03/2017.
 */

public class FragmentMediaList extends BaseFragment {

    private final static String TAG = FragmentLinksList.class.toString();

    private View mRootView;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private ArrayList<MediaList> media;
    private MediaListAdapter adapter;
    private StickyHeaderDecoration decor;
    private User user;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.recycler_view_basic, container, false);

//        try{
//            EventBus.getDefault().register(this);
//        } catch (Exception e){}

        user = ArgsBuilder.create(getArguments()).user();
        REST.getInstance().get_user_pictures(Controller.getInstance().getAuth().getUser().token, user)
                .subscribe(new REST.DataSubscriber<List<Attachment>>() {
                    @Override
                    public void onData(List<Attachment> data) {
                        if (data!=null){
                            adapter = new MediaListAdapter(data, getContext());
                            decor = new StickyHeaderDecoration(adapter);
                            mRecyclerView.addItemDecoration(decor, 0);
                            mRecyclerView.setAdapter(adapter);
                        }
                    }
                });

        mRecyclerView = (RecyclerView) mRootView.findViewById(R.id.recycler_view_all);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new GridLayoutManager(getActivity(), 1);
        mRecyclerView.setLayoutManager(mLayoutManager);




//        adapter = new MediaListAdapter(media, getContext());
//        decor = new StickyHeaderDecoration(adapter);
//        mRecyclerView.addItemDecoration(decor, 0);
//        mRecyclerView.setAdapter(adapter);

        return mRootView;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        Log.d(TAG, "onMessageEvent");
        if (event.getState()){
            adapter.isSelecting = true;
            adapter.notifyDataSetChanged();
        } else {
            adapter.isSelecting = false;
            adapter.clearSelected();
            adapter.notifyDataSetChanged();
        }
    }

}
