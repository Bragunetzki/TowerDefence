package com.mygdx.towerdefence.events.eventClasses;

import com.mygdx.towerdefence.events.ViewEvent;
import com.mygdx.towerdefence.events.ViewHolder;

public class TileTouchedEvent implements ViewEvent {
    int x, y;

    public TileTouchedEvent(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public void execute(ViewHolder view) {
        view.showConstructionDialog(x, y);
    }
}
