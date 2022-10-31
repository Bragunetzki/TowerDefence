package com.mygdx.towerdefence;

import com.badlogic.gdx.math.Vector2;

public class Building {
    private int health;
    private boolean active;
    private final Vector2 position;
    private Enemy target;
    private ActionConfig action;
    private float actionTimer;
    private Priority priority;
    private final int demolitionCurrency;

    public Building(BuildingConfig config, Vector2 position) {
        active = true;
        this.position = position;
        this.action = config.action;
        this.priority = config.priority;
        this.demolitionCurrency = config.demolitionCurrency;
    }

    public int getHealth() {
        return health;
    }

    public void inflictDamage(int damage) {
        health -= damage;
    }

    public boolean isActive() {
        return active;
    }

    public Vector2 getPosition() {
        return position.cpy();
    }

    public void setTarget(Enemy target) {
        this.target = target;
    }

    public Enemy getTarget() {
        return target;
    }

    public ActionConfig getAction() {
        return action;
    }

    public void setAction(ActionConfig action) {
        this.action = action;
    }

    public void setActive(boolean value) {
        active = value;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public Priority getPriority() {
        return priority;
    }

    public void resetTimer() {
        actionTimer = action.rate;
    }

    public boolean isReady() {
        return (actionTimer <= 0);
    }

    public void decTimer(float time) {
        actionTimer -= time;
    }

    public int getDemolitionCurrency() {
        return demolitionCurrency;
    }
}
