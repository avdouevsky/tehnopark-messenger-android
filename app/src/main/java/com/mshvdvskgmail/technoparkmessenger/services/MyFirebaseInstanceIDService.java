package com.mshvdvskgmail.technoparkmessenger.services;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by andrey on 03.02.2017.
 */

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {
    private final static String TAG = MyFirebaseInstanceIDService.class.toString();

    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);

        // TODO: Implement this method to send any registration to your app's servers.
//        sendRegistrationToServer(refreshedToken);
    }
}
