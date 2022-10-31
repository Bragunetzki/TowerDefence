package com.mygdx.towerdefence;

import com.badlogic.gdx.graphics.g2d.Sprite;

import java.util.List;

public class BuildingConfig {
    public final int ID;
    public final int maxHealth;
    public final Priority priority;
    public final int cost;
    public final String name;
    public final Sprite sprite;
    public final int demolitionCurrency;
    public final List<Upgrade> upgrades;
    public final ActionConfig action;
    public final BuildingType buildingType;

    public BuildingConfig(int id, int maxHealth, Priority priority, int cost,
                          String name, Sprite sprite, int demolitionCurrency,
                          List<Upgrade> upgrades, ActionConfig action, BuildingType buildingType) {
        ID = id;
        this.maxHealth = maxHealth;
        this.priority = priority;
        this.cost = cost;
        this.name = name;
        this.sprite = sprite;
        this.demolitionCurrency = demolitionCurrency;
        this.upgrades = upgrades;
        this.action = action;
        this.buildingType = buildingType;
    }
}
