package com.mygdx.towerdefence.level;

import com.mygdx.towerdefence.config.Creator;
import com.mygdx.towerdefence.config.config_classes.LevelConfig;
import com.mygdx.towerdefence.events.eventClasses.StateHolder;
import com.mygdx.towerdefence.gameactor.GameActor;

import java.util.HashMap;
import java.util.Map;

import static com.mygdx.towerdefence.framework.LevelView.*;

public class LevelState implements StateHolder {
    private final Map<Integer, GameActor> activeBuildings;
    private final Map<Integer, GameActor> activeEnemies;
    private final LevelMapState tileMap;
    private int inLevelCurrency;
    private final Creator creator;

    public final float mapCornerX;
    public final float mapCornerY;
    private final WaveGenerator waveGenerator;

    private boolean lastEnemySpawned;

    public LevelState(Creator creator, LevelConfig config, WaveGenerator waveGenerator) {
        inLevelCurrency = config.startingCurrency;
        activeBuildings = new HashMap<>();
        activeEnemies = new HashMap<>();
        tileMap = new LevelMapState(config.tileMap);
        mapCornerX = (WORLD_SIZE_X - (config.tileMap.length * TilE_SIZE)) / 2;
        mapCornerY = (WORLD_SIZE_Y - (config.tileMap[0].length * TilE_SIZE)) / 2;
        this.creator = creator;
        this.waveGenerator = waveGenerator;
        lastEnemySpawned = false;
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
    public LevelMapState getMap() {
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
    public void setCurrency(int currency) {
        this.inLevelCurrency = currency;
    }

    @Override
    public Creator getCreator() {
        return creator;
    }

    public WaveGenerator getWaveGenerator() {
        return waveGenerator;
    }

    @Override
    public void markLastEnemySpawn() {
        lastEnemySpawned = true;
    }

    @Override
    public boolean isLastEnemySpawned() {
        return lastEnemySpawned;
    }
}
