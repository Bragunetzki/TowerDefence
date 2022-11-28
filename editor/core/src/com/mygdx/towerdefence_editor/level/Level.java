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
    //после того как добавлю класс enemy, вместо String поставлю соответствующий тип
    private LevelMap map;
}
