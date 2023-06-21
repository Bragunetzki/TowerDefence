package com.mygdx.towerdefence.events;

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
