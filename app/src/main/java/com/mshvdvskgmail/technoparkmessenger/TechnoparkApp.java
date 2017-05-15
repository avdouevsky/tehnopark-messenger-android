package com.mshvdvskgmail.technoparkmessenger;

import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.squareup.picasso.Picasso;

import org.acra.ACRA;
import org.acra.ReportingInteractionMode;
import org.acra.annotation.ReportsCrashes;

import com.mshvdvskgmail.technoparkmessenger.network.RabbitMQ;
import com.mshvdvskgmail.technoparkmessenger.services.MyFirebaseInstanceIDService;
import com.mshvdvskgmail.technoparkmessenger.services.MyFirebaseMessagingService;
//import com.mshvdvskgmail.technoparkmessenger.services.RabbitMQService;

/**
 * Created by Admin on 23.01.2017.
 */
@ReportsCrashes(
        formKey = "", // This is required for backward compatibility but not used
        formUri = "http://bnet.i-partner.ru/projects/ACRA/",
        mode = ReportingInteractionMode.TOAST,
        forceCloseDialogAfterToast = true,
        resToastText = 1//R.string.crash_toast_text
)
public class TechnoparkApp extends Application {
    private final static String TAG = TechnoparkApp.class.toString();

    private static TechnoparkApp instance;
    private static Context context;

    public float rand = 0f;

    @Override
    public void onCreate() {
        super.onCreate();

        if(!BuildConfig.DEBUG) ACRA.init(this);


        instance = this;
        context = getApplicationContext();
        Log.w("Technopark", "app instance "+instance);
        Log.w("Technopark", "app context "+context);

        Picasso.with(context).setIndicatorsEnabled(BuildConfig.DEBUG);
//        Picasso.with(context).setLoggingEnabled(BuildConfig.DEBUG);
        Config.init(this);
        // это нужно для шрифтов...
        AssetManager assetManager = getAssets();

        Controller.getInstance();   //создаем память для наших манипуляций

//        startService(new Intent(this, RabbitMQService.class));

        startService(new Intent(this, MyFirebaseInstanceIDService.class));
        startService(new Intent(this, MyFirebaseMessagingService.class));
    }

    public static TechnoparkApp getInstance() {
        return instance;
    }

    public static Context getContext() {
        return context;
    }


}
