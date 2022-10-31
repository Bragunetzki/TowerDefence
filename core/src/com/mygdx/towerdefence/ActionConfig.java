package com.mygdx.towerdefence;

import java.util.HashMap;

public class ActionConfig {
    public final int ID;
    public final float rate;
    public final ActionType actionType;
    public final HashMap<String, Float> params;

    public ActionConfig(int id, float rate, ActionType actionType) {
        ID = id;
        this.rate = rate;
        this.actionType = actionType;
        params = new HashMap<>();
    }
}
