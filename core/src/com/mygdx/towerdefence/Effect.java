package com.mygdx.towerdefence;

public interface Effect {
    void apply();
    float getRate();
    float getDuration();
    void update();
    void expire();
}
