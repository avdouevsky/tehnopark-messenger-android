package com.mshvdvskgmail.technoparkmessenger.activities;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import com.mshvdvskgmail.technoparkmessenger.phone.SipService;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by andrey on 15.05.2017.
 */

public class BaseActivity extends AppCompatActivity {
    private final static String TAG = BaseActivity.class.toString();

    protected SipService service = null;
    private ServiceConnection mConnection = new BaseActivity.MyServiceConnection();

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);

        Intent intent = new Intent(this, SipService.class);
        if(!bindService(intent, mConnection, Context.BIND_AUTO_CREATE)){
            Log.w(TAG, "service bind error!");
            // todo restart service and rebind
//            alertDialog.setMessage("Service not binded2");
//            alertDialog.show();
//            progressDialog.dismiss();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);

        if (service != null) {
            unbindService(mConnection);
            service = null;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(service == null){
            Intent intent = new Intent(this, SipService.class);
            if(!bindService(intent, mConnection, Context.BIND_AUTO_CREATE)){
                Log.w(TAG, "service bind error!");
            }
        }
    }

    private class MyServiceConnection implements ServiceConnection{
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            SipService.ServiceBinder binder = (SipService.ServiceBinder) iBinder;
            service = binder.getService();
            Log.v(TAG, "onServiceConnected");
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            Log.w(TAG, "onServiceDisconnected");
            service =null;
        }
    }
}
