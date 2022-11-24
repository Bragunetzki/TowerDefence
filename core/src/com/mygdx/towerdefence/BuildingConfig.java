package com.mygdx.towerdefence;

import java.util.HashMap;
import java.util.List;

public class BuildingConfig {
    public int id;
    public int maxHealth;
    public Priority priority;
    public int cost;
    public String name;
    public String SpriteName;
    public int demolitionCurrency;
    public List<Integer> upgradeIDs;
    public float actionRate;
    public ActionType actionType;
    public HashMap<String, Float> actionParams;
}
