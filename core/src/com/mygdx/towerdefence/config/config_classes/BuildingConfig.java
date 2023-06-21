package com.mygdx.towerdefence.config.config_classes;

import com.mygdx.towerdefence.gameactor.action.ActionType;
import com.mygdx.towerdefence.gameactor.priority.Priority;

import java.util.List;
import java.util.Map;

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
    public Map<String, Float> actionParams;
}
