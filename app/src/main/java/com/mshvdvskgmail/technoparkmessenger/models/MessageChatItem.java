package com.mshvdvskgmail.technoparkmessenger.models;

/**
 * Created by mshvdvsk on 30/03/2017.
 */

public class MessageChatItem {
    private String text;
    private int image;
    private String time;
    private int status;
    private int type;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
