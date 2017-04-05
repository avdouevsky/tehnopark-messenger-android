package com.mshvdvskgmail.technoparkmessenger.network.model;

import android.util.Log;

import org.joda.time.DateTime;

import java.util.Date;

/**
 * Created by andrey on 23.01.2017.
 */

public class Settings {
    private final static String TAG = Settings.class.toString();

    private String messaging_server;
    private String upload_dir;
    private int time;
    public int work_time_since;
    public int work_time_till;

    /**
     * заполняется клиентом
     * разницм между внешним и локальным временем в секундах
     */
    private int delta = 0;
    /**
     * заполняестя клиентом
     * текущее время в секундах
     * unix time
     */
    private int local_time = 0;

    public String getMessagingServerUri() {
        return messaging_server;
    }

    public void time(){
        Date d = new Date();
        local_time = Math.round(d.getTime()/1000f);
        delta = local_time - time;
        Log.d(TAG, "time delta: " + delta);
    }

    public DateTime getTimeOfBegin(){
        Date juDate = new Date();
        DateTime now = new DateTime(juDate/*, DateTimeZone.forID("Europe/Moscow")*/);
        DateTime start = new DateTime(now.year().get(), now.monthOfYear().get(), now.getDayOfMonth(), work_time_since, 0, 0, 0/*,  DateTimeZone.forID("Europe/Moscow")*/);

        return start;

//        Calendar c = Calendar.getInstance();
//        c.setTimeZone(TimeZone.getTimeZone("GMT"));
//        c.set(Calendar.HOUR, work_time_since);
//        c.set(Calendar.MINUTE, 0);
//        c.set(Calendar.SECOND, 0);
//        int seconds = Math.round(c.getTimeInMillis() / 1000f);
//        Log.v(TAG, "getTimeOfBegin " + seconds);
//        return seconds;
    }
}
