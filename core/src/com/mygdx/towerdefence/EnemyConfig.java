package com.mygdx.towerdefence;

import java.util.HashMap;

public class EnemyConfig {
    public int id;
    public int maxHealth;
    public Priority priority;
    public float speed;
    public String name;
    public String SpriteName;
    public float actionRate;
    public ActionType actionType;
    public HashMap<String, Float> actionParams;
}
