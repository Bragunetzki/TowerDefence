package com.mygdx.towerdefence.client.serverCommands;

public class BuildingMessage {
    public final int refID;
    public final int id;
    public final int health;
    public final int gridX;
    public final int gridY;
    public final float buildTimeRemaining;

    public BuildingMessage(int refID, int id, int health, int gridX, int gridY, float buildTimeRemaining) {
        this.refID = refID;
        this.id = id;
        this.health = health;
        this.gridX = gridX;
        this.gridY = gridY;
        this.buildTimeRemaining = buildTimeRemaining;
    }
}
