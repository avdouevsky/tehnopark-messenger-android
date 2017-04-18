package com.mshvdvskgmail.technoparkmessenger.activities;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.mshvdvskgmail.technoparkmessenger.Controller;
import com.mshvdvskgmail.technoparkmessenger.R;
import com.mshvdvskgmail.technoparkmessenger.events.DataLoadEvent;
import com.mshvdvskgmail.technoparkmessenger.fragments.FragmentAddMember;
import com.mshvdvskgmail.technoparkmessenger.fragments.FragmentAuthorization;
import com.mshvdvskgmail.technoparkmessenger.fragments.FragmentChat;
import com.mshvdvskgmail.technoparkmessenger.fragments.FragmentChatsList;
import com.mshvdvskgmail.technoparkmessenger.fragments.FragmentContactsList;
import com.mshvdvskgmail.technoparkmessenger.fragments.FragmentGroupsSettings;
import com.mshvdvskgmail.technoparkmessenger.fragments.FragmentMainFourTabScreen;
import com.mshvdvskgmail.technoparkmessenger.fragments.FragmentMedia;
import com.mshvdvskgmail.technoparkmessenger.fragments.FragmentProfile;
import com.mshvdvskgmail.technoparkmessenger.helpers.Permissions;
import com.mshvdvskgmail.technoparkmessenger.network.model.Chat;
import com.mshvdvskgmail.technoparkmessenger.network.model.Message;
import com.mshvdvskgmail.technoparkmessenger.network.model.User;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import static android.R.attr.value;

public class MainActivity extends AppCompatActivity {
    private final static String TAG = MainActivity.class.toString();
//    @Nullable
//    public static RabbitMQService service = null;
//    private ServiceConnection mConnection = new MyServiceConnection();
    protected Controller controller = Controller.getInstance();

    /* a thing that watches if input fields are empty */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentAuthorization authorization = new FragmentAuthorization();
        getSupportFragmentManager()
                .beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .replace(R.id.container, authorization)
                .addToBackStack(null)
                .commit();
    }

    @Override
    protected void onStart(){
        super.onStart();

/*        Intent intent = new Intent(this, RabbitMQService.class);
        Log.w(TAG, "intent "+ intent);
        Log.w(TAG, "mConnection "+ mConnection);
        if(!bindService(intent, mConnection, Context.BIND_AUTO_CREATE)){
            Log.w(TAG, "service bind error!");
        }*/

    }

    @Override
    public void onResume(){
        super.onResume();
        EventBus.getDefault().register(this);
/*        if(service == null){
            Intent intent = new Intent(this, RabbitMQService.class);
            if(!bindService(intent, mConnection, Context.BIND_AUTO_CREATE)){
                Log.w(TAG, "service bind error!");
            }
        }*/
    }

    @Override
    public void onPause() {
        super.onPause();
//        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
//        if (service != null) {
//            unbindService(mConnection);
//            service = null;
//        }
//        super.onPause();
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


    public void  executeAction(String action, Object value) {
        if(action.equals("showProfile")){
            FragmentProfile fragment = new FragmentProfile();
            Bundle mBundle = new Bundle();
            User user = (User)value;
            mBundle.putSerializable("user", user);
            fragment.setArguments(mBundle);
            this.setFragment(fragment);
        }else if(action.equals("showChat")){
            FragmentChat fragment = new FragmentChat();
            Bundle mBundle = new Bundle();
            Chat chat = (Chat)value;
            mBundle.putSerializable("chat", chat);
            fragment.setArguments(mBundle);
            this.setFragment(fragment);
        }else if(action.equals("showGroupSettings")) {
            FragmentGroupsSettings fragment = new FragmentGroupsSettings();
            Bundle mBundle = new Bundle();
            Chat chat = (Chat) value;
            mBundle.putSerializable("chat", chat);
            fragment.setArguments(mBundle);
            this.setFragment(fragment);
        }else if(action.equals("showGroupSettingsMembers")){
            FragmentAddMember fragment = new FragmentAddMember();
            Bundle mBundle = new Bundle();
            Chat chat = (Chat)value;
            mBundle.putSerializable("chat", chat);
            fragment.setArguments(mBundle);
            this.setFragment(fragment);
        }else if(action.equals("showCreateGroup")){
            FragmentAddMember fragment = new FragmentAddMember();
            Bundle mBundle = new Bundle();
//            Chat chat = (Chat)value;
//            mBundle.putSerializable("chat", chat);
//            fragment.setArguments(mBundle);
            this.setFragment(fragment);

        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public final void onEvent(DataLoadEvent event) {
        eventDataLoad(event.dataSource);
    }

    protected void eventDataLoad(String dataSource){
        Log.v("Technopark", "dataSource "+dataSource);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public final void onEvent(Message message) {
        eventMessage(message);
    }

    protected void eventMessage(Message message){
        Log.v("Technopark", "new message "+message);
    }

    @Override
    public void onBackPressed() {
//        AlertDialog alertDialog = new AlertDialog.Builder(this)
//                .setMessage("Закрыть приложение?")
//                .setPositiveButton("Да", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        dialogInterface.dismiss();
//                        MainActivity.super.onBackPressed();
//                    }
//                })
//                .setNegativeButton("Нет", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        dialogInterface.dismiss();
//                    }
//                }).show();
    }

}
