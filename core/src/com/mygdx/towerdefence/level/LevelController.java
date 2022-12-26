package com.mygdx.towerdefence.level;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.towerdefence.config.Creator;
import com.mygdx.towerdefence.config.LevelConfig;
import com.mygdx.towerdefence.events.ActorDeathEvent;
import com.mygdx.towerdefence.events.ConstructBuildingEvent;
import com.mygdx.towerdefence.events.StateHolder;
import com.mygdx.towerdefence.gameactor.Enemy;
import com.mygdx.towerdefence.gameactor.GameActor;
import com.mygdx.towerdefence.screens.LevelScreen;

public class LevelController {
    private static final int PATHFINDING_UPDATE_RATE = 0; //optional parameter for optimization.
    private final StateHolder levelState;
    private WaveGenerator waveGenerator;
    private float pathfindingTimer;

    public LevelController(Creator creator, int levelID) {
        LevelConfig levelConfig = creator.getLevelConfig(levelID);
        levelState = new LevelState(creator, levelConfig);
        Vector2 basePosition = levelConfig.baseTileCoords;
        LevelScreen.eventQueue.addStateEvent(new ConstructBuildingEvent(0, (int) basePosition.x, (int) basePosition.y));
        pathfindingTimer = 0;
    }

    public void update(float delta) {
        waveGenerator.update(delta);

        for (int key : levelState.getBuildings().keySet()) {
            updateActor(levelState.getBuildings().get(key), key, delta, false);
        }
        for (int key : levelState.getEnemies().keySet()) {
            updateActor(levelState.getEnemies().get(key), key, delta, true);
        }
        if (pathfindingTimer <= 0 && PATHFINDING_UPDATE_RATE != 0) pathfindingTimer = PATHFINDING_UPDATE_RATE;
        else pathfindingTimer -= delta;
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
