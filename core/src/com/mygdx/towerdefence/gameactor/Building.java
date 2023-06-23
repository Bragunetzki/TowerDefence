package com.mygdx.towerdefence.gameactor;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;
import com.mygdx.towerdefence.config.config_classes.BuildingConfig;
import com.mygdx.towerdefence.config.config_classes.BuildingUpgradeConfig;
import com.mygdx.towerdefence.config.config_classes.UpgradeConfig;
import com.mygdx.towerdefence.gameactor.action.Action;
import com.mygdx.towerdefence.gameactor.action.FreezeAction;
import com.mygdx.towerdefence.gameactor.action.GenerateCurrencyAction;
import com.mygdx.towerdefence.gameactor.priority.Priority;

import java.util.HashSet;
import java.util.Set;

public class Building implements GameActor, Pool.Poolable {
    private final int id;
    private int health;
    private final int maxHealth;
    private final Priority priority;
    private final Vector2 position;
    private final String name;
    private final Action action;
    private boolean isActive;
    private float actionTimer;
    private GameActor target;
    private final ActorType actorType;
    private int refID;
    private int buildHealthRate;
    private float buildTimer;
    private float buildTimerStepCurrent;
    private static final float buildTimerStep = 0.5f;
    private final Set<Integer> boughtUpgrades = new HashSet<>();

    public Building(BuildingConfig config, Action action, Vector2 position) {
        this.id = config.id;
        this.maxHealth = config.maxHealth;
        this.health = this.maxHealth;
        this.priority = config.priority;
        this.name = config.name;
        this.action = action;
        this.position = new Vector2(position);
        isActive = true;
        actionTimer = action.getRate();
        target = null;
        actorType = ActorType.Building;

        if (id != 0)
            startConstruction();
    }

    public Building(BuildingConfig config, Action action) {
        this(config, action, Vector2.Zero);
    }

    private void startConstruction() {
        buildTimer = 5;
        buildTimerStepCurrent = buildTimerStep;
        health = 1;
        buildHealthRate = maxHealth / Math.round(buildTimer / buildTimerStep);
    }

    public int applyDamage(int damage) {
        health -= damage;
        if (health < 0) health = 0;
        return health;
    }

    @Override
    public void becomeFrozen(float duration) {
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
    public int getHealth() {
        return health;
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
    public void setPosition(float x, float y) {
        this.position.x = x;
        this.position.y = y;
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
    public void reset() {
        this.health = maxHealth;
    }

    @Override
    public void act(float delta) {
        if (buildTimer > 0) {
            buildTimer -= delta;
            buildTimerStepCurrent -= delta;
            if (buildTimerStepCurrent <= 0) {
                health += buildHealthRate;
                buildTimerStepCurrent = buildTimerStep;
            }
            if (buildTimer <= 0) {
                health += buildHealthRate;
                if (health > maxHealth) health = maxHealth;
            }
            return;
        }

        if (action.getRate() < 0) return;
        actionTimer -= delta;
        if (actionTimer <= 0) {
            if (action.call(this, delta, target))
                actionTimer = action.getRate();
        }
    }

    public boolean isConstructed() {
        return (buildTimer <= 0);
    }

    @Override
    public void kill() {
        isActive = false;
    }

    @Override
    public boolean isActive() {
        return isActive;
    }

    @Override
    public GameActor getTarget() {
        return target;
    }

    @Override
    public void setTarget(GameActor target) {
        this.target = target;
    }

    public void setBuildTime(float time) {
        buildTimer = time;
    }

    @Override
    public void setRefID(int refID) {
        this.refID = refID;
    }

    @Override
    public int getRefID() {
        return refID;
    }

    @Override
    public void setHealth(int health) {
        this.health = health;
    }

    @Override
    public ActorType getType() {
        return actorType;
    }

    public void applyUpgrade(BuildingUpgradeConfig upgrade, int index) {
        if (boughtUpgrades.contains(index))
            return;
        for (UpgradeConfig u : upgrade.upgrades) {
            switch (u.upgradedParameter) {
                case "actionRate":
                    action.setRate(action.getRate() * u.modifier);
                    break;
                case "value":
                    GenerateCurrencyAction gAction = (GenerateCurrencyAction) action;
                    (gAction).setValue((int) (gAction.getValue() * u.modifier));
                    break;
                case "duration":
                    FreezeAction fAction = (FreezeAction) action;
                    (fAction).setDuration((int) (fAction.getDuration() * u.modifier));
                    break;
            }
        }
        boughtUpgrades.add(index);
    }
}
