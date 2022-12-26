package com.mygdx.towerdefence.config;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.towerdefence.level.Tile;

import java.util.List;

public class LevelConfig {
    public int id;
    public String backgroundTextureName;
    public String plotTextureName;
    public String roadTextureName;
    public List<WaveConfig> waves;
    public int startingCurrency;
    public Tile[][] tileMap;
    public Vector2 baseTileCoords;
    public Vector2 spawnerCoords;
}
