package com.mygdx.towerdefence.action;

import com.mygdx.towerdefence.gameactor.GameActor;

import java.util.HashMap;

public class DoNothingAction implements Action {
    private final float rate;
    private final float range;

    public DoNothingAction(float rate, float range, HashMap<String, Float> params) {
        this.rate = rate;
        this.range = range;
    }

    @Override
    public void call(GameActor caller, float delta, GameActor target) {
    }

    @Override
    public float getRate() {
        return rate;
    }

    @Override
    public float getRange() {
        return range;
    }
}
