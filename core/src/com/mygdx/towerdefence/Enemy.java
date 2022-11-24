package com.mygdx.towerdefence;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.towerdefence.action.Action;

public class Enemy implements GameActor {
    public final static int NODE_SNAP_DISTANCE = 5;

    private final int id;
    private int health;
    private final int maxHealth;
    private final Priority priority;
    private final Vector2 position;
    private final String name;
    private final Action action;
    private final float speed;
    private boolean isActive;
    private float actionTimer;
    private GameActor target;
    private Vector2 targetLocation;
    private final ActorType actorType;

    public Enemy(EnemyConfig config, Action action, Vector2 position) {
        this.id = config.id;
        this.maxHealth = config.maxHealth;
        this.health = this.maxHealth;
        this.priority = config.priority;
        this.name = config.name;
        this.speed = config.speed;
        this.position = new Vector2(position);
        this.action = action;
        isActive = true;
        actionTimer = action.getRate();
        target = null;
        actorType = ActorType.Enemy;
    }

    public Enemy(EnemyConfig config, Action action) {
        this(config, action, Vector2.Zero);
    }

    public float getSpeed() {
        return speed;
    }

    @Override
    public int applyDamage(int damage) {
        health -= damage;
        if (health < 0) health = 0;
        return  health;
    }

    @Override
    public int getHealth() {
        return health;
    }

    @Override
    public int getHealthMax() {
        return maxHealth;
    }

    @Override
    public int getID() {
        return id;
    }

    @Override
    public Priority getPriority() {
        return priority;
    }

    @Override
    public Vector2 getPosition() {
        return position;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Action getAction() {
        return action;
    }

    @Override
    public boolean isActive() {
        return isActive;
    }

    @Override
    public void act(float delta) {
        move(targetLocation, delta);
        actionTimer -= delta;
        if (actionTimer <= 0) {
            action.call(this, delta, target);
            actionTimer = action.getRate();
        }
    }

    @Override
    public void kill() {
        isActive = false;
    }

    @Override
    public GameActor getTarget() {
        return target;
    }

    @Override
    public void setTarget(GameActor target) {
        this.target = target;
    }

    private void move(Vector2 node, float delta) {
        if (node.dst(position) > NODE_SNAP_DISTANCE) {
            Vector2 direction = node.sub(position);
            position.add(direction.nor().scl(speed).scl(delta));
        }
        else {
            position.set(node);
        }
    }

    public void setTargetLocation(Vector2 location) {
        this.targetLocation = location;
    }

    @Override
    public ActorType getType() {
        return actorType;
    }

    public void setPosition(Vector2 position) {
        this.position.set(position);
    }
}
