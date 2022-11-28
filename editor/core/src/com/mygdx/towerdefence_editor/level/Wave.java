package com.mygdx.towerdefence_editor.level;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Wave {

    private int waveDelay;
    private List<Integer> enemyTypeIDs;
    private int enemyAmount;
    private int enemyFrequency;

    public Wave(int waveDelay) {
        enemyTypeIDs = new ArrayList<>();
        this.waveDelay = waveDelay;
    }

    public void addEnemyType(int enemyTypeID) {
        enemyTypeIDs.add(enemyTypeID);
    }

    public void removeEnemyType(int typeIndex) {
        enemyTypeIDs.remove(typeIndex);
    }

}
