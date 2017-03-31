package com.mshvdvskgmail.technoparkmessenger.models;

/**
 * Created by mshvdvsk on 29/03/2017.
 */

public class GroupsListItem {
    private String name;
    private String lastMessage;
    private String time;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
