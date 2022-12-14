package com.mygdx.towerdefence.config;

import com.mygdx.towerdefence.action.ActionType;
import com.mygdx.towerdefence.gameactor.Priority;

import java.util.HashMap;
import java.util.List;

public class BuildingConfig {
    public int id;
    public int maxHealth;
    public Priority priority;
    public int cost;
    public String name;
    public String spriteName;
    public int demolitionCurrency;
    public List<Integer> upgradeIDs;
    public float actionRate;
    public ActionType actionType;
    public float actionRange;
    public HashMap<String, Float> actionParams;
}
