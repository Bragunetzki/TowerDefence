package com.mygdx.towerdefence;

import com.badlogic.gdx.math.Vector2;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Wave {
    private final Queue<Integer> enemyIDs;
    private final float waveTime;
    private final Vector2 enemySpawn;
    private final float enemyTime;

    public Wave(List<Integer> enemyIDs, float waveTime, float enemyTime, Vector2 enemySpawn) {
        this.enemyIDs = new LinkedList<>(enemyIDs);
        this.waveTime = waveTime;
        this.enemyTime = enemyTime;
        this.enemySpawn = enemySpawn;
    }

    public Queue<Integer> getEnemyIDs() {
        return enemyIDs;
    }

    public float getWaveTime() {
        return waveTime;
    }

    public float getEnemyTime() {
        return enemyTime;
    }

    public Vector2 getEnemySpawn() {
        return enemySpawn;
    }
}
