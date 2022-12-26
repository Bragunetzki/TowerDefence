package com.mygdx.towerdefence.events;

import com.mygdx.towerdefence.gameactor.GameActor;

public class DamageActorEvent implements StateEvent {
    private final int damage, refID;
    private final boolean targetsEnemy;

    public DamageActorEvent(int damage, int refID, boolean targetsEnemy) {
        this.damage = damage;
        this.refID = refID;
        this.targetsEnemy = targetsEnemy;
    }

    @Override
    public void execute(StateHolder state) {
        GameActor target;
        if (targetsEnemy) target = state.getEnemies().get(refID);
        else target = state.getBuildings().get(refID);

        target.applyDamage(damage);
    }
}
