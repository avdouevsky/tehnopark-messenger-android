package com.mshvdvskgmail.technoparkmessenger.network.model;

import android.util.Log;

import com.mshvdvskgmail.technoparkmessenger.Controller;
import com.mshvdvskgmail.technoparkmessenger.helpers.Helpers;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by andrey on 23.01.2017.
 */

public class Message {
    private final static String TAG = Message.class.toString();

    //public static String DIALOG = "dialog";

    protected String type;
    public String room;
    public int date;
    public int version = 1;
    public Map<String, String> headers;
    public List<Attachment> attachments;
    public String message = "";
    public Token user_token;
    public WifiToken wifi_token;
    public User sender = new User();
    public String uuid;
    public String local_id;

    protected String status = "sent";


    public String getTimeAsString() {
        return new SimpleDateFormat("HH:mm").format(new Date(date * 1000L));
    }

    public Status getStatus() {
        if(status == null) return Status.READ;
        try {
            return Status.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            Log.w(TAG, e.getMessage());
            return Status.READ;
        }
    }

    public void setStatus(Status status) {
        this.status = status.toString();
    }

    public Type getType() {
        if(type == null) return Type.UNKNOWN;
        try {
            return Type.valueOf(type.toUpperCase().replace('-', '_'));
        } catch (IllegalArgumentException e) {
            Log.w(TAG, e.getMessage());
            return Type.UNKNOWN;
        }
    }

    public String getTime() {
        return Helpers.timeToString(date);
    }

    public boolean isIncoming() {
        if(sender.equals(Controller.getInstance().getAuth().getUser())){
            return false;
        }
        return true;
    }

    public enum Status{
        SENT, DELIVERED, READ;

        @Override
        public String toString() {
            return super.toString().toLowerCase();
        }
    }

    public enum Type{
        ERROR, DIALOG, CHIN_CHIN, CHIN_FRIEND, CHIN_ACCEPT, MESSAGE_STATUS, UNKNOWN, USER_STATUS;

        @Override
        public String toString() {
            return super.toString().toLowerCase().replace('_', '-');
        }

    }
}
