package com.mshvdvskgmail.technoparkmessenger.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;

import com.mshvdvskgmail.technoparkmessenger.R;
import com.mshvdvskgmail.technoparkmessenger.fragments.FragmentCallsList;
import com.mshvdvskgmail.technoparkmessenger.fragments.FragmentChatsList;
import com.mshvdvskgmail.technoparkmessenger.fragments.FragmentContactsList;
import com.mshvdvskgmail.technoparkmessenger.fragments.FragmentContactsTabFiller;
import com.mshvdvskgmail.technoparkmessenger.fragments.FragmentDocumentsList;
import com.mshvdvskgmail.technoparkmessenger.fragments.FragmentLinksList;
import com.mshvdvskgmail.technoparkmessenger.fragments.FragmentMediaList;

/**
 * Created by mshvdvsk on 21/03/2017.
 */

public class MediaTabAdapter extends FragmentPagerAdapter {
    private int mNumOfTabs;
    private Context context;

    public MediaTabAdapter(FragmentManager fm, int NumOfTabs, Context context) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                FragmentMediaList tab0 = new FragmentMediaList();
                return tab0;
            case 1:
                FragmentLinksList tab1 = new FragmentLinksList();
                return tab1;
            case 2:
                FragmentDocumentsList tab2 = new FragmentDocumentsList();
                return tab2;
            default: return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }

}
