package com.example.valentin.capteurmajeure;

/**
 * Created by Valentin on 07/01/2018.
 */

public abstract class RoomContextRule {

    public void apply(RoomContextState context) {
        if (condition(context))
            action();
    }

    protected abstract boolean condition(RoomContextState context);

    protected abstract void action();

}