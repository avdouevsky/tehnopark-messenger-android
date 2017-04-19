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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mshvdvskgmail.technoparkmessenger.R;
import com.mshvdvskgmail.technoparkmessenger.adapters.MediaTabAdapter;
import com.mshvdvskgmail.technoparkmessenger.events.MessageEvent;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by mshvdvsk on 21/03/2017.
 */

public class FragmentMedia extends Fragment {
    private View mRootView;
    private ImageView imageTrashIcon;
    private ImageView imageShareIcon;
    private LinearLayout linearBottomBar;
    private TabLayout tabLayout;
    private TextView tvSelectButton;
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

        tabLayout = (TabLayout) mRootView.findViewById(R.id.fragment_media_tl_tabs);
        linearBottomBar = (LinearLayout) mRootView.findViewById(R.id.fragment_media_ll_bottom_bar);



//
        final ViewPager viewPager = (ViewPager) mRootView.findViewById(R.id.fragment_media_vp_pager);
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

        tvSelectButton = (TextView) mRootView.findViewById(R.id.fragment_media_tv_select);
        tvSelectButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (!isSelected){
//                    EventBus.getDefault().post(new MessageEvent(true));
                    linearBottomBar.setVisibility(View.VISIBLE);
                    tvSelectButton.setText("Отменить");
                    isSelected = true;
                } else {
//                    EventBus.getDefault().post(new MessageEvent(false));
                    linearBottomBar.setVisibility(View.GONE);
                    tvSelectButton.setText("Выбрать");
                    isSelected = false;
                }
            }
        });

        imageTrashIcon = (ImageView) mRootView.findViewById(R.id.fragment_media_image_trash);
        imageTrashIcon.setOnClickListener(new View.OnClickListener(){
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

        imageShareIcon = (ImageView) mRootView.findViewById(R.id.fragment_media_image_forward);
        imageShareIcon.setOnClickListener(new View.OnClickListener(){
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
