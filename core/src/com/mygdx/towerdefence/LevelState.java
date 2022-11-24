package com.mygdx.towerdefence;

import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.List;

public class LevelState {
    public List<GameActor> activeBuildings;
    public List<GameActor> activeEnemies;
    public List<Vector2> nodes;
    public int inLevelCurrency;

    public LevelState() {
        inLevelCurrency = 0;
        activeBuildings = new ArrayList<>();
        activeEnemies = new ArrayList<>();
    }

    //TODO: pathfinding
    public Vector2 getClosestNode(Vector2 myPosition, Vector2 targetPosition) {
        return null;
    }
}
