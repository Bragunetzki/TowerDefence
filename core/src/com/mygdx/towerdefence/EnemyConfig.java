package com.mygdx.towerdefence;

import com.badlogic.gdx.graphics.g2d.Sprite;

import java.util.List;

public class EnemyConfig {
    public final int ID;
    public final int maxHealth;
    public final Priority priority;
    public final float speed;
    public final String name;
    public final Sprite sprite;
    public final ActionConfig action;
    public final EnemyType enemyType;

    public EnemyConfig(int id, int maxHealth, Priority priority, float speed, String name, Sprite sprite,
                          List<Upgrade> upgrades, ActionConfig action, EnemyType enemyType) {
        ID = id;
        this.maxHealth = maxHealth;
        this.priority = priority;
        this.speed = speed;
        this.name = name;
        this.sprite = sprite;
        this.action = action;
        this.enemyType = enemyType;
    }
}
