package com.mygdx.towerdefence;

import com.badlogic.gdx.math.Vector2;

import java.util.List;

public class LevelConfig {
    public int ID;
    public List<WaveConfig> waves;
    public String backgroundTextureName;
    public List<Vector2> tilePositions;
    public int baseTileIndex;
}
