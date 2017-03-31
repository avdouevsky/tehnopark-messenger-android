package com.mshvdvskgmail.technoparkmessenger.models;

/**
 * Created by mshvdvsk on 21/03/2017.
 */

public class MediaList {
    private boolean isBottom;
    private String date;

    private boolean isPressedFirst;
    private boolean isPressedSecond;
    private boolean isPressedThird;
    private boolean isPressedForth;

    private boolean isEmptyFirst;
    private boolean isEmptySecond;
    private boolean isEmptyThird;
    private boolean isEmptyForth;


    public boolean isBottom() {
        return isBottom;
    }

    public void setBottom(boolean bottom) {
        isBottom = bottom;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isPressedFirst() {
        return isPressedFirst;
    }

    public void setPressedFirst(boolean pressedFirst) {
        isPressedFirst = pressedFirst;
    }

    public boolean isPressedSecond() {
        return isPressedSecond;
    }

    public void setPressedSecond(boolean pressedSecond) {
        isPressedSecond = pressedSecond;
    }

    public boolean isPressedThird() {
        return isPressedThird;
    }

    public void setPressedThird(boolean pressedThird) {
        isPressedThird = pressedThird;
    }

    public boolean isPressedForth() {
        return isPressedForth;
    }

    public void setPressedForth(boolean pressedForth) {
        isPressedForth = pressedForth;
    }

    public boolean isEmptyFirst() {
        return isEmptyFirst;
    }

    public void setEmptyFirst(boolean emptyFirst) {
        isEmptyFirst = emptyFirst;
    }

    public boolean isEmptySecond() {
        return isEmptySecond;
    }

    public void setEmptySecond(boolean emptySecond) {
        isEmptySecond = emptySecond;
    }

    public boolean isEmptyThird() {
        return isEmptyThird;
    }

    public void setEmptyThird(boolean emptyThird) {
        isEmptyThird = emptyThird;
    }

    public boolean isEmptyForth() {
        return isEmptyForth;
    }

    public void setEmptyForth(boolean emptyForth) {
        isEmptyForth = emptyForth;
    }
}
