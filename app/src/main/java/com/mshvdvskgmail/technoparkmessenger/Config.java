package com.mshvdvskgmail.technoparkmessenger;

import android.content.Context;

/**
 * Created by vlad on 17.04.17.
 */

public class Config {
    private static Context mAppContext;
//    private static ISip mSipService;

    public static void init(Context context){
        Config.mAppContext=context;
    }

    public static Context getAppContext(){
        return Config.mAppContext;
    }

//    public static void setSipService(ISip sipService){
//        Config.mSipService=sipService;
//    }

//    public static ISip getSipService(){
//        return Config.mSipService;
//    }
}

