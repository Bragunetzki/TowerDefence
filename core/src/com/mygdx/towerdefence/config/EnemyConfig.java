package com.mygdx.towerdefence.config;

import com.mygdx.towerdefence.action.ActionType;
import com.mygdx.towerdefence.gameactor.Priority;

import java.util.HashMap;

public class EnemyConfig {
    public int id;
    public int maxHealth;
    public int reward;
    public Priority priority;
    public float speed;
    public String name;
    public String SpriteName;
    public float actionRate;
    public float actionRange;
    public ActionType actionType;
    public HashMap<String, Float> actionParams;
}
