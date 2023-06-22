package com.mygdx.towerdefence.level;

import com.mygdx.towerdefence.framework.TileType;

public class Tile {
    public final TileType type;
    public final float x, y;
    public final int gridX, gridY;
    public final boolean isOwned;

    public Tile(TileType type, float x, float y, int gridX, int gridY) {
        this.type = type;
        this.x = x;
        this.y = y;
        this.gridX = gridX;
        this.gridY = gridY;
        this.isOwned = true;
    }
}
