package com.mygdx.towerdefence.events.eventClasses;

import com.mygdx.towerdefence.config.Creator;
import com.mygdx.towerdefence.gameactor.GameActor;
import com.mygdx.towerdefence.level.LevelMapState;
import com.mygdx.towerdefence.level.WaveGenerator;

import java.util.Map;

public interface StateHolder {
    Map<Integer, GameActor> getBuildings();
    Map<Integer, GameActor> getEnemies();
    LevelMapState getMap();
    int getCurrency();
    void addCurrency(int currency);
    void setCurrency(int currency);
    Creator getCreator();
    WaveGenerator getWaveGenerator();

    void markLastEnemySpawn();

    boolean isLastEnemySpawned();
}
