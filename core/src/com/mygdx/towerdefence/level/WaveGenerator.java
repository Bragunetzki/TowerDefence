package com.mygdx.towerdefence.level;

import com.badlogic.gdx.math.Path;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.towerdefence.config.LevelConfig;
import com.mygdx.towerdefence.config.WaveConfig;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class WaveGenerator {
    private final Queue<WaveConfig> waves;
    private WaveConfig activeWave;
    private final LevelController controller;
    private boolean isActive;
    private boolean isWaveActive = false;
    private float waveTimer;
    private float enemyTimer;
    private final Random random;
    private int enemiesDepleted;
    private final PathNode spawnNode;

    public WaveGenerator(LevelController controller, LevelConfig levelConfig) {
        waves = new LinkedList<>(levelConfig.waves);
        isActive = false;
        this.controller = controller;
        random = new Random();
        spawnNode = controller.levelState.nodeGraph;
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
            controller.addEnemy(enemyID, spawnNode);
            enemiesDepleted++;
            if (enemiesDepleted >= activeWave.enemyCount) {
                isWaveActive = false;
                activeWave = waves.remove();
                waveTimer = activeWave.waveDelay;
            }
            enemyTimer = activeWave.enemyInterval;
        }
        else if (waveTimer <= 0) {
            isWaveActive = true;
            enemiesDepleted = 0;
            enemyTimer = activeWave.enemyInterval;
        }
    }
}
