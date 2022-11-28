package com.mygdx.towerdefence_editor;

import com.mygdx.towerdefence_editor.tower.Tower;
import com.mygdx.towerdefence_editor.tower.TowerUpgrade;

import java.util.ArrayList;
import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
        Tower basic = new Tower();
        basic.setName("Basic");
        basic.setCost(10);
        basic.setDemolitionCurrency(5);
        basic.setMaxHealth(100);
        basic.setActionType(Tower.ActionType.ATTACK);
        basic.setRange(10);
        basic.setRate(5);

        HashMap<Tower.ActionParameter, Float> lvl1mod = new HashMap<>();
        lvl1mod.put(Tower.ActionParameter.RANGE, (float) 1.1);
        ArrayList<TowerUpgrade> lvl1 = new ArrayList<>();
        lvl1.add(new TowerUpgrade(2, lvl1mod));
        basic.setUpgrades(lvl1);

        Serializer serializer = new Serializer();
        serializer.serialize("tower.json", basic);
    }
}
