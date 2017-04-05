package com.mshvdvskgmail.technoparkmessenger.activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.mshvdvskgmail.technoparkmessenger.R;
import com.mshvdvskgmail.technoparkmessenger.fragments.FragmentAuthorization;
import com.mshvdvskgmail.technoparkmessenger.fragments.FragmentChatsList;
import com.mshvdvskgmail.technoparkmessenger.fragments.FragmentContactsList;
import com.mshvdvskgmail.technoparkmessenger.fragments.FragmentMainFourTabScreen;
import com.mshvdvskgmail.technoparkmessenger.fragments.FragmentMedia;
import com.mshvdvskgmail.technoparkmessenger.fragments.FragmentProfile;
import com.mshvdvskgmail.technoparkmessenger.helpers.Permissions;
import com.mshvdvskgmail.technoparkmessenger.network.model.Chat;
import com.mshvdvskgmail.technoparkmessenger.network.model.User;
import com.squareup.picasso.Picasso;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import static android.R.attr.value;

public class MainActivity extends AppCompatActivity {

    /* a thing that watches if input fields are empty */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        Permissions.request(this, Manifest.permission.USE_SIP, Permissions.USE_SIP, new GrantedSIP(MainActivity.this));


        FragmentAuthorization authorization = new FragmentAuthorization();
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

    public void setFragment(Fragment frag){
        getSupportFragmentManager()
                .beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .replace(R.id.container, frag)
                .addToBackStack(null)
                .commit();
    }


    public void executeAction(String action, Object value) {
        if(action.equals("showProfile")){
            FragmentProfile fragmentProfile = new FragmentProfile();
            Bundle mBundle = new Bundle();
            User user = (User)value;
            mBundle.putSerializable("user", user);
            fragmentProfile.setArguments(mBundle);
            this.setFragment(fragmentProfile);
        }else if(action.equals("showChat")){
            Chat chat = (Chat)value;
            Log.w("Technopark", "showChat: "+chat);
/*            FragmentChatsList fragmentProfile = new FragmentProfile();
            Bundle mBundle = new Bundle();
            User user = (User)value;
            mBundle.putSerializable("user", user);
            fragmentProfile.setArguments(mBundle);
            this.setFragment(fragmentProfile);*/
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        Permissions.handleResponse(requestCode, Permissions.USE_SIP, grantResults, new GrantedSIP(MainActivity.this), null);
    }


    private class GrantedSIP implements Permissions.Callback{
        private MainActivity activity;

        public GrantedSIP(MainActivity activity) {
            this.activity = activity;
            Log.w("SIP", "Start activity");
            Intent intent = new Intent(this.activity, CallActivity.class);
            startActivity(intent);

        }

        @Override
        public void execute() {
            Log.w("SIP", "Perms ok");
        }
    }


}
