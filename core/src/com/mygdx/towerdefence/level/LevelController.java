package com.mygdx.towerdefence.level;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.towerdefence.config.Creator;
import com.mygdx.towerdefence.config.config_classes.LevelConfig;
import com.mygdx.towerdefence.events.eventClasses.ActorDeathEvent;
import com.mygdx.towerdefence.events.eventClasses.ConstructBuildingEvent;
import com.mygdx.towerdefence.events.eventClasses.LevelEndEvent;
import com.mygdx.towerdefence.events.eventClasses.StateHolder;
import com.mygdx.towerdefence.gameactor.Enemy;
import com.mygdx.towerdefence.gameactor.GameActor;
import com.mygdx.towerdefence.framework.screens.LevelScreen;

public class LevelController {
    private static final int PATHFINDING_UPDATE_RATE = 0; //optional parameter for optimization.
    private final StateHolder levelState;
    private final WaveGenerator waveGenerator;
    private float pathfindingTimer;
    private boolean isActive;
    private final boolean isOnline;
    private final int reward;

    public LevelController(Creator creator, int levelID, boolean isOnline) {
        LevelConfig levelConfig = creator.getLevelConfig(levelID);
        reward = levelConfig.reward;
        Vector2 basePosition = levelConfig.baseTileCoords;

        if (!isOnline)
            LevelScreen.eventQueue.addStateEvent(new ConstructBuildingEvent(0, (int) basePosition.x, (int) basePosition.y, 0));

        pathfindingTimer = 0;

        waveGenerator = new WaveGenerator(levelConfig);
        if (!isOnline) {
            waveGenerator.start();
        }
        levelState = new LevelState(creator, levelConfig, waveGenerator);
        isActive = true;
        this.isOnline = isOnline;
    }

    public void update(float delta) {
        if (!isOnline) {
            if (!isActive) return;
            if (levelState.isLastEnemySpawned() && levelState.getEnemies().size() == 0) {
                LevelScreen.eventQueue.addStateEvent(new LevelEndEvent(true, reward));
                isActive = false;
            }
            waveGenerator.update(delta);

            for (int key : levelState.getBuildings().keySet()) {
                updateActor(levelState.getBuildings().get(key), key, delta, false);
            }
            for (int key : levelState.getEnemies().keySet()) {
                updateActor(levelState.getEnemies().get(key), key, delta, true);
            }
            if (pathfindingTimer <= 0 && PATHFINDING_UPDATE_RATE != 0) pathfindingTimer = PATHFINDING_UPDATE_RATE;
            else pathfindingTimer -= delta;
        } else {
            for (int key : levelState.getEnemies().keySet()) {
                predictEnemy((Enemy) levelState.getEnemies().get(key), delta);
            }
        }
    }

    private void predictEnemy(Enemy enemy, float delta) {
        enemy.setTarget(enemy.getPriority().chooseTarget(enemy.getPosition(), levelState.getBuildings().values()));
        if (enemy.getTarget() != null) {
            Vector2 nextNode = levelState.getMap().getPath(enemy.getPosition(), enemy.getTarget().getPosition());
            enemy.setMoveTarget(nextNode);
        }
        enemy.move(delta);
    }

    private void updateActor(GameActor actor, int refID, float delta, boolean isEnemy) {
        if (!actor.isActive()) return;

        if (pathfindingTimer <= 0) {
            if (isEnemy) {
                actor.setTarget(actor.getPriority().chooseTarget(actor.getPosition(), levelState.getBuildings().values()));
                if (actor.getTarget() != null) {
                    Vector2 nextNode = levelState.getMap().getPath(actor.getPosition(), actor.getTarget().getPosition());
                    Enemy e = (Enemy) actor;
                    e.setMoveTarget(nextNode);
                }
            } else {
                actor.setTarget(actor.getPriority().chooseTarget(actor.getPosition(), levelState.getEnemies().values()));
            }
        }
        //actions
        actor.act(delta);
        if (actor.getHealth() <= 0) {
            actor.kill();
            LevelScreen.eventQueue.addStateEvent(new ActorDeathEvent(refID, isEnemy));
        }
    }

    public StateHolder getLevelState() {
        return levelState;
    }
}
