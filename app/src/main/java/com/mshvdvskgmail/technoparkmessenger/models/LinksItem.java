package com.mshvdvskgmail.technoparkmessenger.models;

/**
 * Created by mshvdvsk on 23/03/2017.
 */

public class LinksItem {
    private String linkSent;
    private String linkTitle;
    private String linkAdress;
    private boolean isPressed;


    public String getLinkSent() {
        return linkSent;
    }

    public void setLinkSent(String linkSent) {
        this.linkSent = linkSent;
    }

    public String getLinkTitle() {
        return linkTitle;
    }

    public void setLinkTitle(String linkTitle) {
        this.linkTitle = linkTitle;
    }

    public String getLinkAdress() {
        return linkAdress;
    }

    public void setLinkAdress(String linkAdress) {
        this.linkAdress = linkAdress;
    }

    public boolean isPressed() {
        return isPressed;
    }

    public void setPressed(boolean pressed) {
        isPressed = pressed;
    }
}
