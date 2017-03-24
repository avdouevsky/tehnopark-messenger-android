package com.mshvdvskgmail.technoparkmessenger.models;

/**
 * Created by mshvdvsk on 19/03/2017.
 */

public class ContactsListItem {

    private String name;
    private String officePosition;
    private boolean isOnline;

    public String getOfficePosition() {
        return officePosition;
    }

    public void setOfficePosition(String officePosition) {
        this.officePosition = officePosition;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isOnline() {
        return isOnline;
    }

    public void setOnline(boolean online) {
        isOnline = online;
    }

}
