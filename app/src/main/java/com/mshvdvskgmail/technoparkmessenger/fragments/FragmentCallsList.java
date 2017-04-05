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

public class FragmentCallsList extends Fragment {
    private View mRootView;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private ArrayList<SipCall> calls;
    private CallsListAdapter mAdapter;

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.pager_item_recycler_view, container, false);

        mRecyclerView = (RecyclerView) mRootView.findViewById(R.id.pager_recycler);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);

        calls = new ArrayList<>();

        /*CallsList dummyObject1 = new CallsList();
        dummyObject1.setName("Пушкин");
        dummyObject1.setTime("10:20");
        dummyObject1.setOnline(true);
        dummyObject1.setMissed(false);
        dummyObject1.setIncoming(true);

        CallsList dummyObject2 = new CallsList();
        dummyObject2.setName("Пушкин");
        dummyObject2.setTime("10:20");
        dummyObject2.setOnline(false);
        dummyObject2.setMissed(true);
        dummyObject2.setIncoming(true);

        CallsList dummyObject3 = new CallsList();
        dummyObject3.setName("Авдуевский Михаил");
        dummyObject3.setTime("Вчера");
        dummyObject3.setOnline(false);
        dummyObject3.setMissed(false);
        dummyObject3.setIncoming(false);

        calls.add(dummyObject1);
        calls.add(dummyObject2);
        calls.add(dummyObject3);

//        calls.add(dummyObject2);
//        calls.add(dummyObject3);

//        for (int i = 0; i < 10; i++){
//            contacts.add(dummyObject);
//        }*/
REST.getInstance().user_ext(Controller.getInstance().getAuth().getUser().token.session_id, Controller.getInstance().getAuth().getUser().token.token, Controller.getInstance().getAuth().getUser().id)
//        .observeOn(AndroidSchedulers.mainThread())
        .enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                REST.getInstance().calls(Controller.getInstance().getAuth().getUser().token.session_id, Controller.getInstance().getAuth().getUser().token.token, response.body(), "0", "100")
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new REST.DataSubscriber<List<SipCall>>(){
                            @Override
                            public void onData(List<SipCall> data){
                                Log.w("Technopart", "recieve callers "+data);
                                calls.addAll(data);
                            }

                            @Override
                            public void onCompleted(){
                                mAdapter.notifyDataSetChanged();
                            }
                        });

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
//        .subscribe(new REST.DataSubscriber<Integer>(){
//           @Override
//            public void onData(Integer ext_num){
//               Log.w("Technopark", "Ext number: "+ext_num);
//
//           }
//        });


        mAdapter = new CallsListAdapter(calls, getContext());
        mRecyclerView.setAdapter(mAdapter);

        return mRootView;
    }
}