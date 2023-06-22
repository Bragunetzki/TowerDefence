package com.mygdx.towerdefence.events.eventClasses;

import com.mygdx.towerdefence.events.ViewEvent;
import com.mygdx.towerdefence.events.ViewHolder;

public class SetTimerEvent implements ViewEvent {
    private final float time;

    public SetTimerEvent(float time) {
        this.time = time;
    }

    @Override
    public void execute(ViewHolder view) {
        view.setTimerLabel(time);
    }
}
