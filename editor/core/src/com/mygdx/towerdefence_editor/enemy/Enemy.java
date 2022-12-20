package com.mygdx.towerdefence_editor.enemy;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class Enemy {

    public enum ActionType {
        BASE_ATTACK,
        TOWER_ATTACK,
        SUPPORT
        // ???
    }

    public enum ActionParameter {

    }

    private String name;
    private int maxHealth;
    private int reward;
    private String spriteFileName;
    private int speed;
    private ActionType actionType;
    private int actionRate;
    private int actionRange;
    private Map<ActionParameter, Float> actionParameters;
}
