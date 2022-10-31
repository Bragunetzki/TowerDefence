package com.mygdx.towerdefence;

import com.badlogic.gdx.math.Vector2;

import java.util.LinkedList;
import java.util.Queue;

public class WaveGenerator {
    private float waveTimer;
    private float enemyTimer;
    private final Queue<Integer> enemyQueue;
    private final Queue<Float> enemyDelays;
    private final Queue<Vector2> enemyPositions;
    private final Queue<Wave> waveQueue;
    private Wave nextWave;
    private final GameController gameController;

    public WaveGenerator(GameController gameController, Queue<Wave> waves) {
        enemyQueue = new LinkedList<>();
        enemyDelays = new LinkedList<>();
        enemyPositions = new LinkedList<>();
        waveQueue = new LinkedList<>(waves);
        nextWave = waveQueue.remove();
        waveTimer = nextWave.getWaveTime();
        enemyTimer = nextWave.getEnemyTime();
        this.gameController = gameController;
    }

    public void update(float delta) {
        if (waveTimer <= 0) {
            for (int i = 0; i < nextWave.getEnemyIDs().size(); i++)
                enemyDelays.add(nextWave.getEnemyTime());
            if (enemyQueue.isEmpty()) {
                resetEnemyTimer(enemyDelays.remove());
            }
            enemyQueue.addAll(nextWave.getEnemyIDs());
            for (int i = 0; i < nextWave.getEnemyIDs().size(); i++)
                enemyPositions.add(nextWave.getEnemySpawn());

            if (!waveQueue.isEmpty()) {
                nextWave = waveQueue.remove();
            }
            resetWaveTimer();
        }

        if (enemyTimer <= 0) {
            if (!enemyQueue.isEmpty()) {
                gameController.addEnemy(enemyQueue.remove(), enemyPositions.remove());
                if (!enemyDelays.isEmpty())
                    resetEnemyTimer(enemyDelays.remove());
            }
        }

        waveTimer -= delta;
        enemyTimer -= delta;
    }

    private void resetWaveTimer() {
        waveTimer = nextWave.getWaveTime();
    }

    private void resetEnemyTimer(float value) {
        enemyTimer = value;
    }
}
