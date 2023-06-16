package com.mygdx.towerdefence.effect;

public interface Effect {
    void apply();
    float getRate();
    float getDuration();
    void update();
    void expire();
}
