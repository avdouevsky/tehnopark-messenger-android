package com.mshvdvskgmail.technoparkmessenger.models;

/**
 * Created by mshvdvsk on 29/03/2017.
 */

public class MemberListItem {
    private String name;
    private String officePosition;
    private boolean isOnline;
    private boolean isAdmin;

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

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }
}
