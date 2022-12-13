package com.mygdx.towerdefence.config;

import com.mygdx.towerdefence.level.PathNode;

import java.util.List;

public class LevelConfig {
    public int ID;
    public String backgroundTextureName;
    public List<WaveConfig> waves;
    public int startingCurrency;
    public List<PathNode> nodeGraph;
    public int baseTileIndex;
    public int spawnerNodeIndex;
}
