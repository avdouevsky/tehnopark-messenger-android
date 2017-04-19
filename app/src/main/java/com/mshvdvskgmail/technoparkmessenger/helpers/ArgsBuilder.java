package com.mshvdvskgmail.technoparkmessenger.helpers;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.mshvdvskgmail.technoparkmessenger.Controller;
//import su.bnet.hurma1.network.model.Bar;
import com.mshvdvskgmail.technoparkmessenger.network.model.Chat;
import com.mshvdvskgmail.technoparkmessenger.network.model.User;

public class ArgsBuilder {
    public static final String USER_UNIQUE_ID = "USER_UNIQUE_ID";
    public static final String TOOLBAR_TITLE = "TOOLBAR_TITLE";
//    public static final String BAR_ID = "BAR_ID";

    private static Temp temp = new Temp();

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
        temp.user = user;
        return this;
    }

    public ArgsBuilder title(String title){
        return put(TOOLBAR_TITLE, title);
    }

    public ArgsBuilder chat(Chat chat){
        temp.chat = chat;
        return this;
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
        return temp.user;
    }

    @Nullable
    public Chat chat(){
        return temp.chat;
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

    private static class Temp{
        private User user;
        private Chat chat;
    }
}