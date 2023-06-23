package com.mygdx.towerdefence.events.eventClasses;

import com.mygdx.towerdefence.gameactor.GameActor;

public class FreezeActorEvent implements StateEvent{
    private final float duration;
    private final int refID;
    private final boolean targetsEnemy;

    public FreezeActorEvent(float duration, int refID, boolean targetsEnemy) {
        this.duration = duration;
        this.refID = refID;
        this.targetsEnemy = targetsEnemy;
    }

    @Override
    public void execute(StateHolder state) {
        GameActor target;
        if (targetsEnemy) target = state.getEnemies().get(refID);
        else target = state.getBuildings().get(refID);

        if (target != null)
            target.becomeFrozen(duration);
    }
}
