package com.mygdx.towerdefence.config;

import com.mygdx.towerdefence.action.Action;
import com.mygdx.towerdefence.gameactor.Building;
import com.mygdx.towerdefence.gameactor.Enemy;

import java.util.Map;

public class Creator {
    public Building getNewBuilding(int ID) {
        return null;
    }

    public Enemy getNewEnemy(int ID) {
        return null;
    }

    public EnemyConfig getEnemyConfig(int ID) {
        return null;
    }

    public BuildingConfig getBuildingConfig(int ID) {
        return null;
    }

    public Action getAction(int ID) {
        return null;
    }

    public LevelConfig getLevelConfig(int ID) {
        return null;
    }

    public Map<Integer, BuildingConfig> getBuildingMap() {
        return null;
    }
}
