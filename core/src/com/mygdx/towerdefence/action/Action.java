package com.mygdx.towerdefence.action;

import com.mygdx.towerdefence.GameActor;

public interface Action {
    void call(GameActor caller, float delta, GameActor target);
    float getRate();
    float getRange();
}
