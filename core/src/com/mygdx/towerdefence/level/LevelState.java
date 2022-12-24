package com.mygdx.towerdefence.level;

import com.mygdx.towerdefence.config.Creator;
import com.mygdx.towerdefence.config.LevelConfig;
import com.mygdx.towerdefence.events.StateHolder;
import com.mygdx.towerdefence.gameactor.GameActor;

import java.util.HashMap;
import java.util.Map;

public class LevelState implements StateHolder {
    private final Map<Integer, GameActor> activeBuildings;
    private final Map<Integer, GameActor> activeEnemies;
    private final LevelMap tileMap;
    private int inLevelCurrency;
    private final Creator creator;

    public LevelState(Creator creator, LevelConfig config) {
        inLevelCurrency = config.startingCurrency;
        activeBuildings = new HashMap<>();
        activeEnemies = new HashMap<>();
        tileMap = new LevelMap(config.tileMap);
        this.creator = creator;
    }

    @Override
    public Map<Integer, GameActor> getBuildings() {
        return activeBuildings;
    }

    @Override
    public Map<Integer, GameActor> getEnemies() {
        return activeEnemies;
    }

    @Override
    public LevelMap getMap() {
        return tileMap;
    }

    @Override
    public int getCurrency() {
        return inLevelCurrency;
    }

    @Override
    public void addCurrency(int currency) {
        inLevelCurrency += currency;
    }

    @Override
    public Creator getCreator() {
        return creator;
    }
}
