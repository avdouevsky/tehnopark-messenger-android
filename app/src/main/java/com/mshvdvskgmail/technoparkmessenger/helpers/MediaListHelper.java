package com.mshvdvskgmail.technoparkmessenger.helpers;

/**
 * Created by mshvd_000 on 18.05.2017.
 */

public class MediaListHelper {
    public String uuid1;
    public String uuid2;
    public String uuid3;
    public String uuid4;

    public void putUuid(int number, String uuid){
        switch (number) {
            case 0:
                uuid1 = uuid;
                break;
            case 1:
                uuid2 = uuid;
                break;
            case 2:
                uuid3 = uuid;
                break;
            case 3:
                uuid4 = uuid;
                break;
        }
    }
}
