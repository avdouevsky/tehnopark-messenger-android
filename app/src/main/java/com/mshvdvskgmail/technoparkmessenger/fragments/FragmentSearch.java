package com.mshvdvskgmail.technoparkmessenger.fragments;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.mshvdvskgmail.technoparkmessenger.R;
import com.mshvdvskgmail.technoparkmessenger.adapters.ProfileFilesAdapter;
import com.mshvdvskgmail.technoparkmessenger.adapters.SearchItemsAdapter;
import com.mshvdvskgmail.technoparkmessenger.models.ProfileAttachment;
import com.mshvdvskgmail.technoparkmessenger.models.SearchItem;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

/**
 * Created by mshvdvsk on 04/04/2017.
 */

public class FragmentSearch extends BaseFragment {
    private View mRootView;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private ArrayList<SearchItem> searchResult;
    private SearchItemsAdapter adapter;
    private AlertDialog alert;

    public FragmentSearch() {}

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mRootView = inflater.inflate(R.layout.fragment_search, container, false);
        setAdapterContent(mRootView);
        return mRootView;
    }

    private void setAdapterContent(View mRootView) {
        mRecyclerView = (RecyclerView) mRootView.findViewById(R.id.fragment_search_rv_result);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        final EditText etSearch = (EditText) mRootView.findViewById(R.id.fragment_search_et_search_field);

        searchResult = new ArrayList<>();
        SearchItem dummyObject = new SearchItem();
        for (int i = 0; i < 10; i++){
            dummyObject.setMessage("Привет, спасибо за информацию!");
            dummyObject.setName("Константин Константинопольский");
            dummyObject.setTime("11:20");
            searchResult.add(dummyObject);
        }
        adapter = new SearchItemsAdapter(searchResult, getContext());
        mRecyclerView.setAdapter(adapter);

        TextView txCancel = (TextView)mRootView.findViewById(R.id.fragment_search_tv_cancel);
        txCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    etSearch.setText("");
                    hideSoftKeyboard(getActivity());
                } catch (Exception e){ }
            }
        });
    }

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(
                activity.getCurrentFocus().getWindowToken(), 0);
    }
}
