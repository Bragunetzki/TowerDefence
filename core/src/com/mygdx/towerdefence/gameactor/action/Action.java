package com.mygdx.towerdefence.gameactor.action;

import com.mygdx.towerdefence.gameactor.GameActor;

public interface Action {
    boolean call(GameActor caller, float delta, GameActor target);
    float getRate();
    float getRange();

    void setRate(float rate);
}
