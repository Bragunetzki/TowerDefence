package com.mygdx.towerdefence.action;

import com.mygdx.towerdefence.GameActor;

import java.util.HashMap;

public class BasicAttackAction extends DoNothingAction {
    private final float range;
    private final int damage;

    public BasicAttackAction(float rate, HashMap<String, Float> params) {
        super(rate, params);
        range = params.get("range");
        damage = Math.round(params.get("damage"));
    }

    @Override
    public void call(GameActor caller, float delta, GameActor target) {
        if (target.getPosition().dst(caller.getPosition()) <= range) {
            target.applyDamage(damage);
        }
    }
}
