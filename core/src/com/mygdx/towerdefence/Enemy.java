package com.mygdx.towerdefence;

import com.badlogic.gdx.math.Vector2;

public class Enemy {
    private int health;
    private boolean active;
    private final Vector2 position;
    private Building target;
    private ActionConfig action;
    private float actionTimer;
    private Priority priority;
    private float speed;

    public Enemy(EnemyConfig config, Vector2 position) {
        active = true;
        this.position = position;
        this.action = config.action;
        this.priority = config.priority;
        this.speed = config.speed;
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

    public void setTarget(Building target) {
        this.target = target;
    }

    public Building getTarget() {
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

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
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

    public void move(Vector2 node, float delta) {
        Vector2 direction = node.sub(position);
        position.add(direction.nor().scl(speed).scl(delta));
    }
}
