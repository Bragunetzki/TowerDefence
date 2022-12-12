package com.mygdx.towerdefence.config;

import com.mygdx.towerdefence.level.PathNode;

import java.util.List;

public class LevelConfig {
    public int ID;
    public String backgroundTextureName;
    public int baseTileIndex;
    public List<WaveConfig> waves;
    public int startingCurrency;
    public PathNode nodeGraph;
    public int spawnerNodeIndex;
}
