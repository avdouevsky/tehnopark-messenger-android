package com.mshvdvskgmail.technoparkmessenger.models;

import com.mshvdvskgmail.technoparkmessenger.helpers.MediaListHelper;
import com.mshvdvskgmail.technoparkmessenger.network.model.Attachment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static android.os.Build.VERSION_CODES.M;

/**
 * Created by mshvdvsk on 21/03/2017.
 */

public class MediaList {
    private List <MediaListHelper> rowList;
    private List<Attachment> attachments;
    private MediaListHelper helperUnit;
    private int counter;

    public MediaList(List<Attachment> attachments){
        this.attachments = attachments;
        helperUnit = new MediaListHelper();
        rowList = new ArrayList<>();
        counter = 0;
        fillRowList();
        int a = 5;
    }

    private void fillRowList(){

        for (int i = 0; i < attachments.size(); i++){
            if (i==0) {
                helperUnit.putUuid(counter, attachments.get(i).uuid);
                counter++;
            } else {
                if (attachments.get(i).time==null||!convertIntoMonth(attachments.get(i).time).equals(convertIntoMonth(attachments.get(i-1).time))){
                    for (int p = counter; p < 4; p++){
                        helperUnit.putUuid(p, null);
                    }
                    rowList.add(helperUnit);
                    counter = 0;
                } else {
                    helperUnit.putUuid(counter, attachments.get(i).uuid);
                    if(counter==3){
                        counter=0;
                    } else counter++;
                }
            }
        }
    }

    private String convertIntoMonth(String date){
        long dateLong = Long.parseLong(date);
        Date dateDate = new Date(dateLong * 1000);
        Locale russianLocale = new Locale("ru","RU");
        SimpleDateFormat dateFormatRequired = new SimpleDateFormat("mm", russianLocale);
        String monthName = dateFormatRequired.format(dateDate);
        return monthName;
    }







//    private boolean isBottom;
//    private String date;
//
//    private boolean isPressedFirst;
//    private boolean isPressedSecond;
//    private boolean isPressedThird;
//    private boolean isPressedForth;
//
//    private boolean isEmptyFirst;
//    private boolean isEmptySecond;
//    private boolean isEmptyThird;
//    private boolean isEmptyForth;
//
//
//    public boolean isBottom() {
//        return isBottom;
//    }
//
//    public void setBottom(boolean bottom) {
//        isBottom = bottom;
//    }
//
//    public String getDate() {
//        return date;
//    }
//
//    public void setDate(String date) {
//        this.date = date;
//    }
//
//    public boolean isPressedFirst() {
//        return isPressedFirst;
//    }
//
//    public void setPressedFirst(boolean pressedFirst) {
//        isPressedFirst = pressedFirst;
//    }
//
//    public boolean isPressedSecond() {
//        return isPressedSecond;
//    }
//
//    public void setPressedSecond(boolean pressedSecond) {
//        isPressedSecond = pressedSecond;
//    }
//
//    public boolean isPressedThird() {
//        return isPressedThird;
//    }
//
//    public void setPressedThird(boolean pressedThird) {
//        isPressedThird = pressedThird;
//    }
//
//    public boolean isPressedForth() {
//        return isPressedForth;
//    }
//
//    public void setPressedForth(boolean pressedForth) {
//        isPressedForth = pressedForth;
//    }
//
//    public boolean isEmptyFirst() {
//        return isEmptyFirst;
//    }
//
//    public void setEmptyFirst(boolean emptyFirst) {
//        isEmptyFirst = emptyFirst;
//    }
//
//    public boolean isEmptySecond() {
//        return isEmptySecond;
//    }
//
//    public void setEmptySecond(boolean emptySecond) {
//        isEmptySecond = emptySecond;
//    }
//
//    public boolean isEmptyThird() {
//        return isEmptyThird;
//    }
//
//    public void setEmptyThird(boolean emptyThird) {
//        isEmptyThird = emptyThird;
//    }
//
//    public boolean isEmptyForth() {
//        return isEmptyForth;
//    }
//
//    public void setEmptyForth(boolean emptyForth) {
//        isEmptyForth = emptyForth;
//    }
//
//

}
