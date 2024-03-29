package com.mygdx.towerdefence.events.eventClasses;

import com.mygdx.towerdefence.events.ViewEvent;
import com.mygdx.towerdefence.events.ViewHolder;

public class RangedAttackEvent implements ViewEvent {
    private final int damage;
    private final int targetRefID;
    private final boolean targetsEnemy;
    private final float x, y;

    public RangedAttackEvent(int damage, float x, float y, int targetRefID, boolean targetsEnemy) {
        this.damage = damage;
        this.targetRefID = targetRefID;
        this.targetsEnemy = targetsEnemy;
        this.x = x;
        this.y = y;
    }

    @Override
    public void execute(ViewHolder view) {
        view.addProjectile(damage, x, y, targetRefID, targetsEnemy);
    }
}
