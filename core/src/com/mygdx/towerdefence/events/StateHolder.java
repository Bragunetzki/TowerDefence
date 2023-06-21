package com.mygdx.towerdefence.events;

import com.mygdx.towerdefence.config.Creator;
import com.mygdx.towerdefence.gameactor.GameActor;
import com.mygdx.towerdefence.level.LevelMapState;

import java.util.Map;

public interface StateHolder {
    Map<Integer, GameActor> getBuildings();
    Map<Integer, GameActor> getEnemies();
    LevelMapState getMap();
    int getCurrency();
    void addCurrency(int currency);
    void setCurrency(int currency);
    Creator getCreator();
}
