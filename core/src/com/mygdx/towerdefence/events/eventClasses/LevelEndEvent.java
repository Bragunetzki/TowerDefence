package com.mygdx.towerdefence.events.eventClasses;

import com.mygdx.towerdefence.framework.screens.LevelScreen;

public class LevelEndEvent implements StateEvent {
    private final boolean victory;
    private final int reward;
    public LevelEndEvent(boolean victory, int reward) {
        this.victory = victory;
        this.reward = reward;
    }
    @Override
    public void execute(StateHolder state) {
        LevelScreen.eventQueue.addViewEvent(new LevelEndViewEvent(victory, reward));
    }
}
