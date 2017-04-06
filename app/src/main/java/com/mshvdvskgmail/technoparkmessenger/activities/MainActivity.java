package com.mshvdvskgmail.technoparkmessenger.activities;

import android.content.Context;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.mshvdvskgmail.technoparkmessenger.R;
import com.mshvdvskgmail.technoparkmessenger.fragments.FragmentAuthorization;
import com.mshvdvskgmail.technoparkmessenger.fragments.FragmentChat;
import com.mshvdvskgmail.technoparkmessenger.fragments.FragmentContactsList;
import com.mshvdvskgmail.technoparkmessenger.fragments.FragmentDeniedCall;
import com.mshvdvskgmail.technoparkmessenger.fragments.FragmentGroupsSettings;
import com.mshvdvskgmail.technoparkmessenger.fragments.FragmentIncomingCall;
import com.mshvdvskgmail.technoparkmessenger.fragments.FragmentMainFourTabScreen;
import com.mshvdvskgmail.technoparkmessenger.fragments.FragmentMedia;
import com.mshvdvskgmail.technoparkmessenger.fragments.FragmentOutgoingCall;
import com.mshvdvskgmail.technoparkmessenger.fragments.FragmentProfile;
import com.mshvdvskgmail.technoparkmessenger.fragments.FragmentSearch;
import com.squareup.picasso.Picasso;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends AppCompatActivity {

    /* a thing that watches if input fields are empty */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        ImageView profileIcon = (ImageView) findViewById(R.id.profile_icon);
//        Picasso.with(this).load(R.drawable.pushkin).transform(new RoundedCornersTransformation(360,0)).into(profileIcon);

        FragmentChat authorization = new FragmentChat();
        getSupportFragmentManager()
                .beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .replace(R.id.container, authorization)
                .addToBackStack(null)
                .commit();
    }

    /* checking if both fields are not empty */
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

//    @Override
//    public void onBackPressed() {
//
//        int count = getFragmentManager().getBackStackEntryCount();
//
//        if (count == 0) {
//            super.onBackPressed();
//            //additional code
//        } else {
//            getFragmentManager().popBackStack();
//        }
//
//    }

    public void take_me_back (View v) {
        FragmentMainFourTabScreen main = new FragmentMainFourTabScreen();
        getSupportFragmentManager()
                .beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .replace(R.id.container, main)
                .addToBackStack(null)
                .commit();
    }

}
