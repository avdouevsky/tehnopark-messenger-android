package com.mshvdvskgmail.technoparkmessenger.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;

import com.mshvdvskgmail.technoparkmessenger.Fragments;
import com.mshvdvskgmail.technoparkmessenger.R;
import com.mshvdvskgmail.technoparkmessenger.adapters.MainScreenTabAdapter;
import com.mshvdvskgmail.technoparkmessenger.events.SwitchFragmentEvent;
import com.mshvdvskgmail.technoparkmessenger.helpers.ArgsBuilder;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by mshvdvsk on 16/03/2017.
 */

public class FragmentMainFourTabScreen extends BaseFragment{
    private final static int ICON_WRITE = R.drawable.icon_write;
    private final static int ICON_PLUS = R.drawable.icon_plus;
    private final static int ICON_SEARCH = R.drawable.icon_search;

    private View mRootView;
    private ImageView imageActionIcon;
    private AlertDialog alert;
    private ViewPager viewPager;

    public FragmentMainFourTabScreen() {}

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        setRetainInstance(true);
        final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_main_four_tab_screen, container, false);

        TabLayout tabLayout = (TabLayout) mRootView.findViewById(R.id.fragment_main_four_tab_tl_tabs);
//

        viewPager = (ViewPager) mRootView.findViewById(R.id.fragment_main_four_tab_vp_pager);
        final MainScreenTabAdapter adapter = new MainScreenTabAdapter
                (getChildFragmentManager(), tabLayout.getTabCount(), getContext());
        viewPager.setAdapter(adapter);
//
//        tabLayout.setupWithViewPager(viewPager);
//
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            tab.setCustomView(adapter.getTabView(i));
        }

        /* setting tab pager listener */

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                updateUI();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        imageActionIcon = (ImageView) mRootView.findViewById(R.id.fragment_main_four_tab_image_new_or_search);

        imageActionIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (viewPager.getCurrentItem()){
                    case 0:
                    case 1:
                        EventBus.getDefault().postSticky(new SwitchFragmentEvent(Fragments.ADD_MEMBER, ArgsBuilder.create().chat(null).bundle()));
                        break;
                }

            }
        });


        return mRootView;
    }

    @Override
    public void onResume() {
        super.onResume();

        updateUI();
    }

    private void updateUI(){
        switch (viewPager.getCurrentItem()){
            case 0:
                imageActionIcon.setImageResource(ICON_PLUS);
                break;
            case 1:
                imageActionIcon.setImageResource(ICON_WRITE);
                break;
            case 2:
                imageActionIcon.setImageResource(ICON_SEARCH);
                break;
            case 3:
                imageActionIcon.setImageResource(ICON_SEARCH);
                break;
        }
    }

}
