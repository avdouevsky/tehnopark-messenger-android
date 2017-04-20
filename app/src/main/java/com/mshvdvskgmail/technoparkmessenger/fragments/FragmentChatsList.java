package com.mshvdvskgmail.technoparkmessenger.fragments;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.mshvdvskgmail.technoparkmessenger.Controller;
import com.mshvdvskgmail.technoparkmessenger.Fragments;
import com.mshvdvskgmail.technoparkmessenger.R;
import com.mshvdvskgmail.technoparkmessenger.adapters.ChatsListAdapter;
import com.mshvdvskgmail.technoparkmessenger.events.SwitchFragmentEvent;
import com.mshvdvskgmail.technoparkmessenger.helpers.ArgsBuilder;
import com.mshvdvskgmail.technoparkmessenger.helpers.ICommand;
import com.mshvdvskgmail.technoparkmessenger.network.REST;
import com.mshvdvskgmail.technoparkmessenger.network.model.Chat;
import org.greenrobot.eventbus.EventBus;
import java.util.List;

/**
 * Created by mshvdvsk on 17/03/2017.
 */
public class FragmentChatsList extends BaseFragment {
    private final static String TAG = FragmentChatsList.class.toString();

    private View mRootView;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
//    private List<Chat> chats = new ArrayList<>();
    private ChatsListAdapter mAdapter;
    private LinearLayout llSearch;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.recycler_view_with_search, container, false);

        mRecyclerView = (RecyclerView) mRootView.findViewById(R.id.recycler_view_all);
        //mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new ChatsListAdapter(getContext());
        mRecyclerView.setAdapter(mAdapter);
        setListeners(mRootView);
        loadData();

        mAdapter.setClickListener(new ICommand<Chat>() {
            @Override
            public void exec(Chat chat) {
                EventBus.getDefault().postSticky(new SwitchFragmentEvent(Fragments.CHAT, ArgsBuilder.create().chat(chat).bundle()));
            }
        });

        //chats.addAll(Controller.getInstance().getChats());
/*
        llSearch = (LinearLayout) mRootView.findViewById(R.id.ll_all_ll_search_bar);
        llSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentSearch search = new FragmentSearch();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .replace(R.id.container, search)
                        .addToBackStack(null)
                        .commit();
            }
        });

        contacts = new ArrayList<>();

        ChatsListItem dummyObject1 = new ChatsListItem();
        dummyObject1.setName("Пушкин");
        dummyObject1.setLastLine("you, fat fuck");
        dummyObject1.setTime("вчера");
        dummyObject1.setOnline(true);
        dummyObject1.setHasNew(true);
        dummyObject1.setNewCount(5);

        ChatsListItem dummyObject2 = new ChatsListItem();
        dummyObject2.setName("Кукушкин");
        dummyObject2.setLastLine("I am a poet!");
        dummyObject2.setTime("17:00");
        dummyObject2.setOnline(false);
        dummyObject2.setHasNew(false);
        dummyObject2.setNewCount(0);

        ChatsListItem dummyObject3 = new ChatsListItem();
        dummyObject3.setName("Птушкин");
        dummyObject3.setLastLine("I am your friend friend friend friend friend friend friend friend !");
        dummyObject3.setTime("03.03.1836");
        dummyObject3.setOnline(true);
        dummyObject3.setHasNew(true);
        dummyObject3.setNewCount(2);

        contacts.add(dummyObject1);
        contacts.add(dummyObject2);
        contacts.add(dummyObject3);*/

//        for (int i = 0; i < 10; i++){
//            contacts.add(dummyObject);
//        }


        return mRootView;
    }

    private void loadData(){
        REST.getInstance().chats(Controller.getInstance().getAuth().getUser().token)
        .subscribe(new REST.DataSubscriber<List<Chat>>() {
            @Override
            public void onData(List<Chat> data) {
                mAdapter.setData(data);
                Log.d(TAG, "asd");
            }
        });
    }

//    protected void eventDataLoad(String dataSource){
//        if(dataSource.equals("Chats")) {
//            Log.w("ChatsList", "eventDataLoad " + dataSource);
//            chats.clear();
//            chats.addAll(Controller.getInstance().getChats());
//            Log.w("ChatsList", "size "+chats.size());
//            mAdapter.notifyDataSetChanged();
//        }
//    }

//    protected void eventMessage(Message message) {
//        if(message.getType() == Message.Type.DIALOG) {
//            Controller.getInstance().fillChats();
//        }else if(message.getType() == Message.Type.ERROR){
//
//        }
//    }

    private void setListeners(View view){
//        LinearLayout llSearch = (LinearLayout) view.findViewById(R.id.ll_all_ll_search_bar);
//        llSearch.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                EventBus.getDefault().postSticky(new SwitchFragmentEvent(Fragments.SEARCH, null));
                //TODO FragmentSearch search = new FragmentSearch();
//                getActivity().getSupportFragmentManager()
//                        .beginTransaction()
//                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
//                        .replace(R.id.container, search)
//                        .addToBackStack(null)
////                        .commit();
//            }
//        });
    }
}
