package com.mshvdvskgmail.technoparkmessenger.fragments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mshvdvskgmail.technoparkmessenger.R;
import com.mshvdvskgmail.technoparkmessenger.adapters.MainScreenTabAdapter;
import com.mshvdvskgmail.technoparkmessenger.adapters.MediaTabAdapter;
import com.mshvdvskgmail.technoparkmessenger.models.MessageEvent;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by mshvdvsk on 21/03/2017.
 */

public class FragmentMedia extends Fragment {
    private View mRootView;
    private ImageView trashIcon;
    private ImageView shareIcon;
    private LinearLayout bottomBar;
    private TabLayout tabLayout;
    private TextView btSelect;
    private AlertDialog alert;
    private boolean isSelected;

    public FragmentMedia() {}

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_media, container, false);

        tabLayout = (TabLayout) mRootView.findViewById(R.id.tab_layout);
        bottomBar = (LinearLayout) mRootView.findViewById(R.id.bottom_bar);



//
        final ViewPager viewPager = (ViewPager) mRootView.findViewById(R.id.pager);
        final MediaTabAdapter adapter = new MediaTabAdapter
                (getChildFragmentManager(), tabLayout.getTabCount(), getContext());
        viewPager.setAdapter(adapter);
//
//        tabLayout.setupWithViewPager(viewPager);
//
//        for (int i = 0; i < tabLayout.getTabCount(); i++) {
//            TabLayout.Tab tab = tabLayout.getTabAt(i);
//            tab.setCustomView(adapter.getTabView(i));
//        }

        /* setting tab pager listener */

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        addListeners();

        return mRootView;
    }

    private void addListeners() {
        btSelect = (TextView) mRootView.findViewById(R.id.button_select);
        btSelect.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (!isSelected){
                    EventBus.getDefault().post(new MessageEvent("Hello everyone!"));
                    bottomBar.setVisibility(View.VISIBLE);
                    isSelected = true;
                } else {
                    EventBus.getDefault().post(new MessageEvent("Hello everyone!"));
                    bottomBar.setVisibility(View.GONE);
                    isSelected = false;
                }
            }
        });

        trashIcon = (ImageView) mRootView.findViewById(R.id.ic_trash);
        trashIcon.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
                alertDialog.setTitle("ОК, СПАСИБО");
                alertDialog.setMessage("Все работает ок, не так ли?");
                alertDialog.setPositiveButton("Да", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alertDialog.setNegativeButton("Не знаю", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alert = alertDialog.create();
                alert.show();
            }
        });

        shareIcon = (ImageView) mRootView.findViewById(R.id.ic_forward);
        shareIcon.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
                alertDialog.setTitle("ОК, СПАСИБО");
                alertDialog.setMessage("Все работает ок, не так ли?");
                alertDialog.setPositiveButton("Да", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alertDialog.setNegativeButton("Не знаю", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alert = alertDialog.create();
                alert.show();
            }
        });
    }

}
