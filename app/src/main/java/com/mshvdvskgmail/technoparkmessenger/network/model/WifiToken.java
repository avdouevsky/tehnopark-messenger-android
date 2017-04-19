package com.mshvdvskgmail.technoparkmessenger.network.model;

/**
 * Created by andrey on 23.01.2017.
 */

public class WifiToken {
    public int ttl = 0;
    public String wifi = "";
    public int date = 0;
    public String token = "";

    public boolean expired(){
        return (date + ttl) < System.currentTimeMillis() / 1000;
    }
}
