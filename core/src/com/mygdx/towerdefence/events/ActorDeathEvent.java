package com.mygdx.towerdefence.events;

import com.mygdx.towerdefence.config.EnemyConfig;
import com.mygdx.towerdefence.screens.LevelScreen;

public class ActorDeathEvent implements StateEvent {
    private final int refID;
    private final boolean isEnemy;

    public ActorDeathEvent(int refID, boolean isEnemy) {
        this.refID = refID;
        this.isEnemy = isEnemy;
    }

    @Override
    public void execute(StateHolder state) {
        if (isEnemy) {
            EnemyConfig config = state.getCreator().getEnemyConfig(state.getEnemies().get(refID).getID());
            LevelScreen.eventQueue.addStateEvent(new AlterCurrencyEvent(config.reward));
            state.getEnemies().remove(refID);
        } else {
            state.getBuildings().remove(refID);
        }
    }
}
