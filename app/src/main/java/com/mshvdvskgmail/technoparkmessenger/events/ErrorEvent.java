package com.mshvdvskgmail.technoparkmessenger.events;

/**
 * Created by andrey on 19.04.2017.
 */

public class ErrorEvent {
    private Throwable throwable;

    public ErrorEvent(Throwable throwable) {
        this.throwable = throwable;
    }

    public Throwable getThrowable() {
        return throwable;
    }
}
