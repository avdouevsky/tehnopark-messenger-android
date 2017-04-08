package com.mshvdvskgmail.technoparkmessenger.events;

/**
 * Created by vlad on 05.04.17.
 */

public class DataLoadEvent {
    public String dataSource;

    public DataLoadEvent(String event){
        this.dataSource = event;
    }
}
