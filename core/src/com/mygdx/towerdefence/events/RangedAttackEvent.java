package com.mygdx.towerdefence.events;

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
        view.addProjectile(0, x, y, targetRefID, targetsEnemy);
    }
}
