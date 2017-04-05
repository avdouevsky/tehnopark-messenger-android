package com.mshvdvskgmail.technoparkmessenger.helpers;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

/**
 * http://lets.i-partner.ru/projects/prj0069/wiki/Android_60_permissions
 *
 * Created by andrey on 29.06.2016.
 */
public class Permissions {
    private final static String TAG = Permissions.class.toString();

    public static int USE_SIP = 10047;

    public static void request(Activity activity, String permissions, int code, Callback function){
        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        if (currentapiVersion >= android.os.Build.VERSION_CODES.M){
            if (ContextCompat.checkSelfPermission(activity,
                    permissions)
                    != PackageManager.PERMISSION_GRANTED) {

                // Should we show an explanation?
                if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
                        permissions)) {
                    Log.d(TAG, "shouldShowRequestPermissionRationale");
                    // Show an expanation to the user *asynchronously* -- don't block
                    // this thread waiting for the user's response! After the user
                    // sees the explanation, try again to request the permission.

                } /*else*/ {
                    Log.d(TAG, "not shouldShowRequestPermissionRationale");
                    // No explanation needed, we can request the permission.

                    ActivityCompat.requestPermissions(activity,
                            new String[]{permissions},
                            code);

                    // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                    // app-defined int constant. The callback method gets the
                    // result of the request.
                }
            } else{
                function.execute();
            }
        } else {
            // do something for phones running an SDK before lollipop
            function.execute();
        }
    }

    public static void handleResponse(int requestCode, int code, int[] grantResults, Callback granted, Callback notGranted){
        if (requestCode == code) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                // permission was granted, yay! Do the
                // contacts-related task you need to do.
                Log.d(TAG, "granted");
                granted.execute();
            } else {

                // permission denied, boo! Disable the
                // functionality that depends on this permission.
                Log.d(TAG, "not granted");
                notGranted.execute();
            }
        }
    }

    public interface Callback{
        public void execute();
    }
}
