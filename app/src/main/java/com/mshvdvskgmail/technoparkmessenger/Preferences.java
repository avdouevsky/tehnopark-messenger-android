package com.mshvdvskgmail.technoparkmessenger;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import com.mshvdvskgmail.technoparkmessenger.network.model.Settings;

/**
 * Created by andrey on 17.02.2017.
 */

public class Preferences {
    private final static String SETTINGS_FILE = "settings_builder_file";
    private final static String SETTINGS_KEY = "settings_builder";

    transient private Gson gson;
    transient private SharedPreferences pref;

    private String social;
    private String social_id;
    private String social_token;

    private Settings settings;

    public Preferences() {
        gson = new Gson();
    }

    public String getSocial() {
        return social;
    }

    public String getSocialId() {
        return social_id;
    }

    public String getSocialToken() {
        return social_token;
    }

    public Settings getSettings() {
        return settings;
    }

    public Preferences setLogin(String social, String social_id, String social_token){
        this.social = social;
        this.social_id = social_id;
        this.social_token = social_token;
        return this;
    }

    public Preferences setSettings(Settings settings) {
        this.settings = settings;
        return this;
    }

    public void save(){
        String json = gson.toJson(this);
        pref.edit().putString(SETTINGS_KEY, json).commit();
    }

    public static class Builder{
        private Preferences preferences = new Preferences();


        public Builder(Context context) {
            preferences.pref = context.getSharedPreferences(SETTINGS_FILE, Context.MODE_PRIVATE);
        }

        public Preferences load(){
            String json = preferences.pref.getString(SETTINGS_KEY, null);
            if(json != null){
                Preferences p = preferences.gson.fromJson(json, Preferences.class);
                p.gson = preferences.gson;
                p.pref = preferences.pref;
                preferences = p;
            }
            return preferences;
        }

        public Preferences clear(){
            preferences.pref.edit().clear().commit();
            return preferences;
        }

        public Preferences getPreferences() {
            return preferences;
        }
    }
}
