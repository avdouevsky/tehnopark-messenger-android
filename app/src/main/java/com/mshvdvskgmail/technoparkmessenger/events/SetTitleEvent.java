package com.mshvdvskgmail.technoparkmessenger.events;

/**
 * Created by andrey on 23.12.2016.
 */

public class SetTitleEvent {
    private String title;

    public SetTitleEvent(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
