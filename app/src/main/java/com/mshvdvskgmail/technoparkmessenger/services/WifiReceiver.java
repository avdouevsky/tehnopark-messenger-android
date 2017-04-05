package com.mshvdvskgmail.technoparkmessenger.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import rx.android.schedulers.AndroidSchedulers;
import com.mshvdvskgmail.technoparkmessenger.Controller;
import com.mshvdvskgmail.technoparkmessenger.network.REST;
import com.mshvdvskgmail.technoparkmessenger.network.model.WifiToken;

/**
 * Created by andrey on 09.02.2017.
 */
public class WifiReceiver extends BroadcastReceiver {
    private final static String TAG = WifiReceiver.class.toString();

    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager conMan = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = conMan.getActiveNetworkInfo();
        if (netInfo != null && netInfo.getType() == ConnectivityManager.TYPE_WIFI){
            Log.d(TAG, "Have Wifi Connection");

            Intent isWifiTestIntent = new Intent(context, RabbitMQService.class);
            isWifiTestIntent.putExtra("isWifi", true);
            context.startService(isWifiTestIntent);
        }
        else{
            Log.d(TAG, "Don't have Wifi Connection");
        }
    }
}
