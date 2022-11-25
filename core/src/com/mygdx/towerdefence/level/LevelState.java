package com.mygdx.towerdefence.level;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.towerdefence.gameactor.GameActor;
import com.mygdx.towerdefence.config.LevelConfig;

import java.util.ArrayList;
import java.util.List;

public class LevelState {
    public List<GameActor> activeBuildings;
    public List<GameActor> activeEnemies;
    public PathNode nodeGraph;
    public int inLevelCurrency;

    public LevelState(LevelConfig config) {
        inLevelCurrency = 0;
        activeBuildings = new ArrayList<>();
        activeEnemies = new ArrayList<>();
        nodeGraph = config.nodeGraph;
    }

    //TODO: pathfinding
    public PathNode getClosestNode(PathNode currentNode, Vector2 targetPosition) {
        return null;
    }
}
