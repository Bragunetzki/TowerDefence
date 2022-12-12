package com.mygdx.towerdefence.level;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.towerdefence.config.Creator;
import com.mygdx.towerdefence.gameactor.ActorType;
import com.mygdx.towerdefence.gameactor.Enemy;
import com.mygdx.towerdefence.gameactor.GameActor;
import com.mygdx.towerdefence.gameactor.Priority;

import java.util.List;

public class LevelController {
    private static final int PATHFINDING_UPDATE_RATE = 0; //optional parameter for optimization.
    LevelState levelState;
    private List<GameActor> deadActors;
    private final Creator creator;
    private WaveGenerator waveGenerator;
    private float pathfindingTimer;

    public LevelController(Creator creator, int levelID) {
        this.creator = creator;
        levelState = new LevelState(creator.getLevelConfig(levelID));
    }

    public void update(float delta) {
        waveGenerator.update(delta);

        for (GameActor b : levelState.activeBuildings) {
            updateActor(b, delta);
        }
        for (GameActor e : levelState.activeEnemies) {
            updateActor(e, delta);
        }
        if (pathfindingTimer <= 0) pathfindingTimer = PATHFINDING_UPDATE_RATE;
        else pathfindingTimer -= delta;

        for (GameActor actor : deadActors) {
            if (actor.getType() == ActorType.Enemy)
                levelState.activeEnemies.remove(actor);
            else levelState.activeBuildings.remove(actor);
        }
    }

    private void updateActor(GameActor actor, float delta) {
        if (!actor.isActive()) return;

        //pathfinding for enemies
        if (pathfindingTimer <= 0) {
            actor.setTarget(chooseTarget(actor.getPosition(), actor.getPriority(), levelState.activeEnemies));
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

    private GameActor chooseTarget(Vector2 position, Priority priority, List<GameActor> actors) {
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
        levelState.activeEnemies.add(newEnemy);
    }
}
