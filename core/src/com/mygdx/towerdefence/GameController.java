package com.mygdx.towerdefence;

import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class GameController {
    Database database;
    WaveGenerator waveGenerator;
    LevelState levelState;
    List<Building> deadBuildings;
    List<Enemy> deadEnemies;

    public void initLevel(Queue<Wave> waves) {
        levelState = new LevelState();
        waveGenerator = new WaveGenerator(this, waves);
        deadBuildings = new ArrayList<>();
        deadEnemies = new ArrayList<>();
    }

    public void controllerTick(float delta) {
        waveGenerator.update(delta);

        for (Building building : levelState.getBuildings()) {
            updateBuilding(building, delta);
        }
        for (Enemy enemy : levelState.getEnemies()) {
            updateEnemy(enemy, delta);
        }

        for (Building dead : deadBuildings) {
            levelState.getBuildings().remove(dead);
        }
        for (Enemy dead : deadEnemies) {
            levelState.getEnemies().remove(dead);
        }
    }

    public void addEnemy(int enemyID, Vector2 position) {
        levelState.addEnemy(database.getEnemy(enemyID), position);
    }

    public void addBuilding(int buildingID, Vector2 position) {
        levelState.addBuilding(database.getBuilding(buildingID), position);
    }

    public void demolishBuilding(int index) {
        Building building = levelState.getBuildings().get(index);
        int curr = building.getDemolitionCurrency();
        levelState.addCurrency(curr);
        deadBuildings.add(building);
    }

    private void updateBuilding(Building building, float delta) {
        if (!building.isActive()) return;

        ActionConfig action = building.getAction();
        Enemy target = chooseTargetEnemy(building.getPosition(), action.params.get("range"), building.getPriority());
        building.setTarget(target);

        if (building.isReady()) {
            switch (action.actionType) {
                case SingleTargetAttack:
                    if (target == null) break;
                    if (inRange(building.getPosition(), target.getPosition(), action.params.get("range")))
                        attackEnemy(target, action.params.get("damage"));
                    break;
                case Default:
                    break;
            }
            building.resetTimer();
        }

        if (building.getHealth() <= 0) deadBuildings.add(building);
        building.decTimer(delta);
    }

    private void updateEnemy(Enemy enemy, float delta) {
        if (!enemy.isActive()) return;

        ActionConfig action = enemy.getAction();
        float range = action.params.get("range");
        Building target = chooseTargetBuilding(enemy.getPosition(), range, enemy.getPriority());
        enemy.setTarget(target);

        if (target != null) {
            Vector2 node = levelState.getClosestNodeInPath(enemy.getPosition(), target);
            if (!inRange(enemy.getPosition(), target.getPosition(), range))
                enemy.move(node, delta);
        }

        if (enemy.isReady()) {
            switch (action.actionType) {
                case SingleTargetAttack:
                    if (target == null) break;
                    if (inRange(enemy.getPosition(), target.getPosition(), range))
                        attackBuilding(target, action.params.get("damage"));
                case Default:
                    break;
            }
        }

        if (enemy.getHealth() <= 0) deadEnemies.add(enemy);
        enemy.decTimer(delta);
    }

    private boolean inRange(Vector2 position1, Vector2 position2, Float range) {
        Vector2 pos1 = new Vector2(position1);
        Vector2 pos2 = new Vector2(position2);
        return pos1.sub(pos2).len() <= range;
    }

    //TODO target choosing algorithm
    private Enemy chooseTargetEnemy(Vector2 position, float range, Priority priority) {
        return null;
    }

    private Building chooseTargetBuilding(Vector2 position, float range, Priority priority) {
        return null;
    }

    public void attackEnemy(Enemy enemy, float damage) {
        if (enemy == null) return;
        enemy.inflictDamage((int) damage);
    }

    public void attackBuilding(Building building, float damage) {
        if (building == null) return;
        building.inflictDamage((int) damage);
    }
}
