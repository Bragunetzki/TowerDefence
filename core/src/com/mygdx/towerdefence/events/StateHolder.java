package com.mygdx.towerdefence.events;

import com.mygdx.towerdefence.config.Creator;
import com.mygdx.towerdefence.gameactor.GameActor;
import com.mygdx.towerdefence.level.LevelMap;

import java.util.Map;

public interface StateHolder {
    Map<Integer, GameActor> getBuildings();
    Map<Integer, GameActor> getEnemies();
    LevelMap getMap();
    int getCurrency();
    void addCurrency(int currency);
    Creator getCreator();
}
