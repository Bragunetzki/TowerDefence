package com.mygdx.towerdefence.events;

public class ActorDeathEvent implements StateEvent {
    private final int refID;
    private final boolean isEnemy;

    public ActorDeathEvent(int refID, boolean isEnemy) {
        this.refID = refID;
        this.isEnemy = isEnemy;
    }

    @Override
    public void execute(StateHolder state) {
        if (isEnemy)
            state.getEnemies().remove(refID);
        else state.getBuildings().remove(refID);
    }
}
