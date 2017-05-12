package com.mshvdvskgmail.technoparkmessenger.events;

import android.os.Bundle;

import com.mshvdvskgmail.technoparkmessenger.Fragments;

/**
 * Created by andrey on 16.12.2016.
 */

public class SwitchFragmentEvent {
    private Fragments states;
    private Bundle bundle;
    private Direction direction;

    public SwitchFragmentEvent(Fragments states, Bundle bundle, Direction direction) {
        this.states = states;
        this.bundle = bundle;
        this.direction = direction;
    }

    public SwitchFragmentEvent(Fragments states, Bundle bundle) {
        this(states, bundle, Direction.FORWARD);
    }

    public Fragments getStates() {
        return states;
    }

    public Bundle getBundle() {
        return bundle;
    }

    public Direction getDirection() {
        return direction;
    }

    public enum Direction{
        FORWARD,
        BACK,
        REPLACE,
        BACKTO
    }
}
