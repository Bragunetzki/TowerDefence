package com.mygdx.towerdefence;

import java.util.HashMap;

public class Database {
    private final HashMap<Integer, BuildingConfig> buildingMap;
    private final HashMap<Integer, EnemyConfig> enemyMap;

    public Database() {
        buildingMap = new HashMap<>();
        enemyMap = new HashMap<>();
    }

    public BuildingConfig getBuilding(int ID) {
        return buildingMap.get(ID);
    }

    public EnemyConfig getEnemy(int ID) {
        return enemyMap.get(ID);
    }
}
