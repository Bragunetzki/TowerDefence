package com.mygdx.towerdefence.action;

import com.mygdx.towerdefence.gameactor.GameActor;

import java.util.HashMap;

public class BasicAttackAction extends DoNothingAction {
    private final float range;
    private final int damage;

    public BasicAttackAction(float rate, float range, HashMap<String, Float> params) {
        super(rate, range, params);
        this.range = range;
        damage = Math.round(params.get("damage"));
    }

    @Override
    public void call(GameActor caller, float delta, GameActor target) {
        if (target.getPosition().dst(caller.getPosition()) <= range) {
            target.applyDamage(damage);
        }
    }
}
