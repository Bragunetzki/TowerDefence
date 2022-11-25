package com.mygdx.towerdefence.config;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.towerdefence.level.PathNode;

import java.util.List;

public class LevelConfig {
    public int ID;
    public int startingCurrency;
    public List<WaveConfig> waves;
    public String backgroundTextureName;
    public List<Vector2> tilePositions;
    public int baseTileIndex;
    public PathNode nodeGraph; //This is the parent node of the graph, a.k.a. the "entry point" for the enemies.
}
