package com.mygdx.towerdefence;

import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.List;

public class LevelState {
    private final List<Enemy> enemies;
    private final List<Building> buildings;
    private int currency;

    public LevelState() {
        currency = 0;
        enemies = new ArrayList<>();
        buildings = new ArrayList<>();
    }

    public List<Enemy> getEnemies() {
        return enemies;
    }

    public List<Building> getBuildings() {
        return buildings;
    }

    public int getCurrency() {
        return currency;
    }

    public void addCurrency(int val) {
        currency += val;
    }

    //TODO pathfinding algorithm
    public Vector2 getClosestNodeInPath(Vector2 position, Building target) {
        return new Vector2(1, 1);
    }

    public void addEnemy(EnemyConfig config, Vector2 position) {
        enemies.add(new Enemy(config, position));
    }

    public void addBuilding(BuildingConfig config, Vector2 position) {
        buildings.add(new Building(config, position));
    }
}
