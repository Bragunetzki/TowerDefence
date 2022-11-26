package com.mygdx.towerdefence_editor;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Tower {

    enum ActionType {
        ECONOMIC,
        ATTACK
        // что-то еще
    }

    enum ActionParameter {
        RATE,
        RANGE
        // MAX_HEALTH -- улучшается на уровне или в дереве технологий?
        // some EFFECT_PARAMETERS -- зависит от реализации эффектов
    }

    private String name;
    private int maxHealth;
    private int cost;
    private String spriteFileName;
    private List<TowerUpgrade> upgrades;
    private int demolitionCurrency;
    private ActionType actionType;
    private int rate;
    private int range;

    public Tower() {
    }

    public Tower(String name, int maxHealth, int cost, String spriteFileName, List<TowerUpgrade> upgrades,
                 int demolitionCurrency, ActionType actionType, int range, int rate) {
        this.name = name;
        this.maxHealth = maxHealth;
        this.cost = cost;
        this.spriteFileName = spriteFileName;
        this.upgrades = upgrades;
        this.demolitionCurrency = demolitionCurrency;
        this.actionType = actionType;
        this.range = range;
        this.rate = rate;
    }

    /*
    private final int id; // башне наверное не нужен, нужен только в списке всех башен
    public Tower(int id) {
        this.id = id;
    }
     */
}
