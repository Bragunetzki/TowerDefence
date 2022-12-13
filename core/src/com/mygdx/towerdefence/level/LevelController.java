package com.mygdx.towerdefence.level;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.towerdefence.config.Creator;
import com.mygdx.towerdefence.config.LevelConfig;
import com.mygdx.towerdefence.gameactor.*;

import java.util.Collection;
import java.util.List;

public class LevelController {
    private static final int PATHFINDING_UPDATE_RATE = 0; //optional parameter for optimization.
    private final LevelState levelState;
    private List<GameActor> deadActors;
    private final Creator creator;
    private WaveGenerator waveGenerator;
    private float pathfindingTimer;

    public LevelController(Creator creator, int levelID) {
        this.creator = creator;
        LevelConfig levelConfig = creator.getLevelConfig(levelID);
        levelState = new LevelState(levelConfig);
        addBuilding(0, levelState.nodeGraph.get(levelConfig.baseTileIndex));
        pathfindingTimer = 0;
    }

    public void update(float delta) {
        waveGenerator.update(delta);

        for (GameActor b : levelState.activeBuildings.values()) {
            updateActor(b, delta);
        }
        for (GameActor e : levelState.activeEnemies.values()) {
            updateActor(e, delta);
        }
        if (pathfindingTimer <= 0 && PATHFINDING_UPDATE_RATE != 0) pathfindingTimer = PATHFINDING_UPDATE_RATE;
        else pathfindingTimer -= delta;

        for (GameActor actor : deadActors) {
            if (actor.getType() == ActorType.Enemy)
                levelState.activeEnemies.values().remove(actor);
            else levelState.activeBuildings.values().remove(actor);
        }
    }

    private void updateActor(GameActor actor, float delta) {
        if (!actor.isActive()) return;

        //pathfinding for enemies
        if (pathfindingTimer <= 0) {
            actor.setTarget(chooseTarget(actor.getPosition(), actor.getPriority(), levelState.activeEnemies.values()));
            if (actor instanceof Enemy) {
                if (actor.getTarget() != null) {
                    PathNode[] path = levelState.getPath(actor.getCurrentNode(), actor.getTarget().getCurrentNode());
                    Enemy e = (Enemy) actor;
                    e.setTargetNode(path[0]);
                }
            }
        }

        //actions
        actor.act(delta);
        if (actor.getHealth() <= 0) {
            actor.kill();
            deadActors.add(actor);
        }
    }

    private GameActor chooseTarget(Vector2 position, Priority priority, Collection<GameActor> actors) {
        GameActor target = null;

        switch (priority) {
            case Default:
                //Simply selects closest available target
                float minRange = Float.MAX_VALUE;
                for (GameActor actor : actors) {
                    if (position.cpy().sub(actor.getPosition()).len2() <= minRange) {
                        minRange = position.cpy().sub(actor.getPosition()).len2();
                        target = actor;
                    }
                }
                break;
            default:
                break;
        }

        return target;
    }

    public void addEnemy(int enemyID, PathNode spawnNode) {
        Enemy newEnemy = creator.getNewEnemy(enemyID);
        newEnemy.setPosition(spawnNode.position);
        newEnemy.setCurrentNode(spawnNode);
        newEnemy.setTargetNode(spawnNode);
        int refID = MathUtils.random(10000);
        while (levelState.activeEnemies.containsKey(refID)) {
            refID = MathUtils.random(10000);
        }
        levelState.activeEnemies.put(refID, newEnemy);
    }

    public void addBuilding(int buildingID, PathNode buildNode) {
        Building newBuilding = creator.getNewBuilding(buildingID);
        newBuilding.setPosition(buildNode.position);
        newBuilding.setCurrentNode(buildNode);
        int refID = MathUtils.random(10000);
        while (levelState.activeEnemies.containsKey(refID)) {
            refID = MathUtils.random(10000);
        }
        levelState.activeBuildings.put(refID, newBuilding);
    }

    public LevelState getLevelState() {
        return levelState;
    }
}
