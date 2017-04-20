package com.mshvdvskgmail.technoparkmessenger.fragments;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mshvdvskgmail.technoparkmessenger.Fragments;
import com.mshvdvskgmail.technoparkmessenger.R;
import com.mshvdvskgmail.technoparkmessenger.events.SwitchFragmentEvent;
import com.mshvdvskgmail.technoparkmessenger.helpers.ArgsBuilder;
import com.mshvdvskgmail.technoparkmessenger.helpers.ICommand;


import com.mshvdvskgmail.technoparkmessenger.Controller;
import com.mshvdvskgmail.technoparkmessenger.adapters.ChatsListAdapter;
import com.mshvdvskgmail.technoparkmessenger.network.REST;
import com.mshvdvskgmail.technoparkmessenger.network.model.Chat;

import org.greenrobot.eventbus.EventBus;
import java.util.List;

/**
 * Created by mshvdvsk on 29/03/2017.
 */

public class FragmentGroupsList extends BaseFragment {
    private RecyclerView mRecyclerView;
//    private ArrayList<Chat> groups;
    private ChatsListAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.recycler_view_with_search, container, false);

        mRecyclerView = (RecyclerView) root.findViewById(R.id.recycler_view_all);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new ChatsListAdapter(getContext());
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setClickListener(new ICommand<Chat>() {
            @Override
            public void exec(Chat chat) {
                EventBus.getDefault().postSticky(new SwitchFragmentEvent(Fragments.CHAT, ArgsBuilder.create().chat(chat).bundle()));
            }
        });

//        llSearch = (LinearLayout) root.findViewById(R.id.ll_all_ll_search_bar);
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

        //groups = new ArrayList<>();
        //groups.addAll(Controller.getInstance().getGroupChats());

        loadData();

        return root;
    }

    private void loadData(){
        REST.getInstance().groups(Controller.getInstance().getAuth().getUser().token)
                .subscribe(new REST.DataSubscriber<List<Chat>>() {
                    @Override
                    public void onData(List<Chat> data) {
                        mAdapter.setData(data);
                    }
                });
    }

//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public final void onEvent(DataLoadEvent event) {
//        eventDataLoad(event.dataSource);
//    }

//    protected void eventDataLoad(String dataSource){
//        if(dataSource.equals("Groups")) {
//            Log.w("GroupsList", "eventDataLoad " + dataSource);
//            groups.clear();
//            groups.addAll(Controller.getInstance().getGroupChats());
//            mAdapter.notifyDataSetChanged();
//        }
//    }

}

