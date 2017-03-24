package com.mshvdvskgmail.technoparkmessenger.models;

/**
 * Created by mshvdvsk on 20/03/2017.
 */

public class CallsList {
    private String name;
    private String time;
    private boolean isMissed;
    private boolean isIncoming;
    private boolean isOnline;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public boolean isMissed() {
        return isMissed;
    }

    public void setMissed(boolean missed) {
        isMissed = missed;
    }

    public boolean isIncoming() {
        return isIncoming;
    }

    public void setIncoming(boolean incoming) {
        isIncoming = incoming;
    }

    public boolean isOnline() {
        return isOnline;
    }

    public void setOnline(boolean online) {
        isOnline = online;
    }
}
