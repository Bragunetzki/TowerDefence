package com.mygdx.towerdefence_editor.level;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.List;

@Getter
@Setter
public class Level {

    private int defaultWaveInterval;
    private List<Wave> waves;
    private int startCurrency;
    private int reward;
    private HashMap<String, Float> enemyModifiers;
    private LevelMap map;
}
