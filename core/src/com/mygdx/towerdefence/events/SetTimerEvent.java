package com.mygdx.towerdefence.events;

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
