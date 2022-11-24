package com.mygdx.towerdefence;

import com.badlogic.gdx.math.Vector2;

import java.util.List;

public class LevelController {
    LevelState levelState;
    private List<GameActor> deadActors;
    private Creator creator;
    private WaveGenerator waveGenerator;

    public LevelController() {
        levelState = new LevelState();
    }

    public void update(float delta) {
        waveGenerator.update(delta);

        for (GameActor b : levelState.activeBuildings) {
            updateActor(b, delta);
        }
        for (GameActor e : levelState.activeEnemies) {
            updateActor(e, delta);
        }

        for (GameActor actor : deadActors) {
            if (actor.getType() == ActorType.Enemy)
                levelState.activeEnemies.remove(actor);
            else levelState.activeBuildings.remove(actor);
        }
    }

    private void updateActor(GameActor actor, float delta) {
        if (!actor.isActive()) return;

        //pathfinding for enemies
        actor.setTarget(chooseTarget(actor.getPosition(), actor.getPriority(), levelState.activeEnemies));
        if (actor instanceof Enemy) {
            if (actor.getTarget() != null) {
                Vector2 node = levelState.getClosestNode(actor.getPosition(), actor.getTarget().getPosition());
                Enemy e = (Enemy) actor;
                e.setTargetLocation(node);
            }
        }

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

    public void addEnemy(int enemyID, Vector2 spawnPosition) {
        Enemy newEnemy = creator.getNewEnemy(enemyID);
        newEnemy.setPosition(spawnPosition);
        levelState.activeEnemies.add(newEnemy);
    }
}
