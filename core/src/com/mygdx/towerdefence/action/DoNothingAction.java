package com.mygdx.towerdefence.action;

import com.mygdx.towerdefence.GameActor;

import java.util.HashMap;

public class DoNothingAction implements Action {
    private final float rate;

    public DoNothingAction(float rate, HashMap<String, Float> params) {
        this.rate = rate;
    }

    @Override
    public void call(GameActor caller, float delta, GameActor target) {
    }

    @Override
    public float getRate() {
        return rate;
    }
}
