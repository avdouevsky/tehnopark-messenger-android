package com.mshvdvskgmail.technoparkmessenger.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.mshvdvskgmail.technoparkmessenger.R;
import com.mshvdvskgmail.technoparkmessenger.fragments.FragmentCallsList;
import com.mshvdvskgmail.technoparkmessenger.fragments.FragmentChatsList;
import com.mshvdvskgmail.technoparkmessenger.fragments.FragmentContactsList;
import com.mshvdvskgmail.technoparkmessenger.fragments.FragmentContactsTabFiller;
import com.mshvdvskgmail.technoparkmessenger.fragments.FragmentGroupsList;

/**
 * Created by mshvdvsk on 13/03/2017.
 */

public class MainScreenTabAdapter extends FragmentPagerAdapter {
    private int mNumOfTabs;
    private Context context;

    public MainScreenTabAdapter(FragmentManager fm, int NumOfTabs, Context context) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                FragmentChatsList tab0 = new FragmentChatsList();
                return tab0;
            case 1:
                FragmentGroupsList tab1 = new FragmentGroupsList();
                return tab1;
            case 2:
                FragmentContactsList tab2 = new FragmentContactsList();
                return tab2;
            case 3:
                FragmentCallsList tab3 = new FragmentCallsList();
                return tab3;

            default: return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }

    public View getTabView(int position) {
        View v;
        /*switch (position) {
            case 0:
                v = LayoutInflater.from(context).inflate(R.layout.contacts_custom_tab_1, null);
                return v;
            case 1:
                v = LayoutInflater.from(context).inflate(R.layout.contacts_custom_tab_2, null);
                return v;
            case 2:
                v = LayoutInflater.from(context).inflate(R.layout.contacts_custom_tab_3, null);
                return v;
            case 3:
                v = LayoutInflater.from(context).inflate(R.layout.contacts_custom_tab_4, null);
                return v;
        }*/
        v = LayoutInflater.from(context).inflate(R.layout.contacts_custom_tab_1, null);
        TextView title = (TextView)v.findViewById(R.id.custom_tab_view);
        switch (position) {
            case 0:
                title.setText("ЧАТЫ");
                break;
            case 1:
                title.setText("ГРУППЫ");
                break;
            case 2:
                title.setText("КОНТАКТЫ");
                break;
            case 3:
                title.setText("ВЫЗОВЫ");
                break;
        }

        FrameLayout badge = (FrameLayout)v.findViewById(R.id.fl_badge);
        badge.setVisibility(View.GONE);

        return v;
//        return null;
    }
}
