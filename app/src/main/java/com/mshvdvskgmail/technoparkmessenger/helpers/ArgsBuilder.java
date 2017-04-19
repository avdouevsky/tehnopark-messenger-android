package com.mshvdvskgmail.technoparkmessenger.helpers;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.mshvdvskgmail.technoparkmessenger.Controller;
//import su.bnet.hurma1.network.model.Bar;
import com.mshvdvskgmail.technoparkmessenger.network.model.User;

public class ArgsBuilder {
    public static final String USER_UNIQUE_ID = "USER_UNIQUE_ID";
    public static final String TOOLBAR_TITLE = "TOOLBAR_TITLE";
//    public static final String BAR_ID = "BAR_ID";

    private Bundle bundle;
    private Intent intent;

    public ArgsBuilder(@Nullable Bundle bundle) {
        if(bundle == null) bundle = new Bundle();
        this.bundle = bundle;
    }

    public static ArgsBuilder create(){
        return new ArgsBuilder(null);
    }

    public static ArgsBuilder create(Bundle bundle){
        return new ArgsBuilder(bundle);
    }

    public ArgsBuilder intent(Context packageContext, Class<?> cls){
        intent = new Intent(packageContext, cls);
        return this;
    }

    public ArgsBuilder put(String key, int val){
        bundle.putInt(key, val);
        return this;
    }

    public ArgsBuilder put(String key, String val){
        bundle.putString(key, val);
        return this;
    }

    public ArgsBuilder user(User user){
        Controller.getInstance().putUser(user);
        return put(USER_UNIQUE_ID, user.unique_id);
    }

    public ArgsBuilder title(String title){
        return put(TOOLBAR_TITLE, title);
    }


    public int get(String key, int def){
        return bundle.getInt(key, def);
    }

    @Nullable
    public String title(){
        return bundle.getString(TOOLBAR_TITLE, null);
    }

    /**
     * @return User or null
     */
    @Nullable
    public User user(){
        String uid = bundle.getString(USER_UNIQUE_ID, null);
        if (uid == null) return null;
        return Controller.getInstance().getUser(uid);
    }

//    public Bar bar(){
//        int id = bundle.getInt(BAR_ID, 0);
//        if(id == 0) return null;
//        return Controller.getInstance().getPlaceById(id);
//    }

    public Bundle bundle() {
        return bundle;
    }

    public Intent intent(){
        return intent.putExtras(bundle());
    }
}