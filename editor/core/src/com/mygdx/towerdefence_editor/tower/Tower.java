package com.mygdx.towerdefence_editor.tower;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class Tower {

    public enum ActionType {
        NONE,
        ECONOMIC,
        ATTACK
        // что-то еще
    }

    public enum ActionParameter {

    }

    private String name;
    private int maxHealth;
    private int cost;
    private String spriteFileName;
    private List<TowerUpgrade> upgrades;
    private int demolitionCurrency;
    private ActionType actionType;
    private int actionRate;
    private int actionRange;
    private Map<ActionParameter, Float> actionParameters;
    // добавить эффекты

    public Tower() {
    }

    public Tower(String name, int maxHealth, int cost, String spriteFileName, List<TowerUpgrade> upgrades,
                 int demolitionCurrency, ActionType actionType, int range, int rate, Map<ActionParameter, Float> actionParameters) {
        this.name = name;
        this.maxHealth = maxHealth;
        this.cost = cost;
        this.spriteFileName = spriteFileName;
        this.upgrades = upgrades;
        this.demolitionCurrency = demolitionCurrency;
        this.actionType = actionType;
        this.actionRange = range;
        this.actionRate = rate;
        this.actionParameters = actionParameters;
    }

}
