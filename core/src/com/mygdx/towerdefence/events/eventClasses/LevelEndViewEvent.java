package com.mygdx.towerdefence.events.eventClasses;

import com.mygdx.towerdefence.events.ViewEvent;
import com.mygdx.towerdefence.events.ViewHolder;

public class LevelEndViewEvent implements ViewEvent {
    private final boolean victory;
    private final int reward;
    public LevelEndViewEvent(boolean victory, int reward) {
        this.victory = victory;
        this.reward = reward;
    }
    @Override
    public void execute(ViewHolder view) {
        view.showEndDialog(victory, reward);
    }
}
