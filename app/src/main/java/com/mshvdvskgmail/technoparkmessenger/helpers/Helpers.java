package com.mshvdvskgmail.technoparkmessenger.helpers;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by andrey on 20.04.2017.
 */

public class Helpers {
    public static String timeToString(int time){
        SimpleDateFormat out_format = new SimpleDateFormat("HH:mm");
        Date date = new Date(time * 1000);
        return out_format.format(date);
    }
}
