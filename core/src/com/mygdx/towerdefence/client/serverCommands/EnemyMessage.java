package com.mygdx.towerdefence.client.serverCommands;

public class EnemyMessage {
    public final int refID;
    public final int id;
    public final int health;
    public final float x;
    public final float y;

    public EnemyMessage(int refID, int id, int health, float x, float y) {
        this.refID = refID;
        this.id = id;
        this.health = health;
        this.x = x;
        this.y = y;
    }
}
