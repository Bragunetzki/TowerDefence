package com.mygdx.towerdefence.events.eventClasses;

import com.mygdx.towerdefence.events.ViewEvent;
import com.mygdx.towerdefence.events.ViewHolder;

public class FreezingEffectEvent implements ViewEvent {
    private final float duration;
    private final float x, y;

    public FreezingEffectEvent(float duration, float x, float y) {
        this.duration = duration;
        this.x = x;
        this.y = y;
    }

    @Override
    public void execute(ViewHolder view) {
        view.addIce(duration, x, y);
    }
}
