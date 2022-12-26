package com.mygdx.towerdefence.level;

import com.mygdx.towerdefence.config.LevelConfig;
import com.mygdx.towerdefence.config.WaveConfig;
import com.mygdx.towerdefence.events.SpawnEnemyEvent;
import com.mygdx.towerdefence.screens.LevelScreen;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class WaveGenerator {
    private final Queue<WaveConfig> waves;
    private WaveConfig activeWave;
    private boolean isActive;
    private boolean isWaveActive = false;
    private float waveTimer;
    private float enemyTimer;
    private final Random random;
    private int enemiesDepleted;
    private final int[] spawner;

    public WaveGenerator(LevelController controller, LevelConfig levelConfig) {
        waves = new LinkedList<>(levelConfig.waves);
        isActive = false;
        random = new Random();
        spawner = new int[2];
        spawner[0] = (int) levelConfig.spawnerCoords.x;
        spawner[1] = (int) levelConfig.spawnerCoords.y;
    }

    public void startGenerator() {
        activeWave = waves.remove();
        waveTimer = activeWave.waveDelay;
        enemyTimer = activeWave.enemyInterval;
        isWaveActive = false;
        isActive = true;
        enemiesDepleted = 0;
    }

    public void update(float delta) {
        if (!isActive) return;

        if (isWaveActive) enemyTimer -= delta;
        else {
            waveTimer -= delta;
        }

        if (enemyTimer <= 0) {
            int index = random.nextInt(activeWave.enemyTypes.size());
            int enemyID = activeWave.enemyTypes.get(index);
            LevelScreen.eventQueue.addStateEvent(new SpawnEnemyEvent(enemyID, spawner[0], spawner[1]));
            enemiesDepleted++;
            if (enemiesDepleted >= activeWave.enemyCount) {
                isWaveActive = false;
                activeWave = waves.remove();
                waveTimer = activeWave.waveDelay;
            }
            enemyTimer = activeWave.enemyInterval;
        } else if (waveTimer <= 0) {
            isWaveActive = true;
            enemiesDepleted = 0;
            enemyTimer = activeWave.enemyInterval;
        }
    }
}
