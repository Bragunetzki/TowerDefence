package com.mygdx.towerdefence.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mygdx.towerdefence.action.Action;
import com.mygdx.towerdefence.action.BasicAttackAction;
import com.mygdx.towerdefence.action.DoNothingAction;
import com.mygdx.towerdefence.gameactor.Building;
import com.mygdx.towerdefence.gameactor.Enemy;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Creator {
    HashMap<Integer, BuildingConfig> BuildingConfigs = new HashMap<>();
    HashMap<Integer, EnemyConfig> EnemyConfigs = new HashMap<>();
    HashMap<Integer, LevelConfig> LevelConfigs = new HashMap<>();
    HashMap<Integer, WaveConfig> WaveConfigs = new HashMap<>();
//    HashMap<ActionType, String> ActionMap = new HashMap<>();

    public void init() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
//        ActionMap = mapper.readValue(new File("com/mygdx/towerdefence/action/action-class.json"),
//                new TypeReference<HashMap<String, Integer>>() {
//                });

        ArrayList<BuildingConfig> bldCnfList = mapper.readValue(new File("com/mygdx/towerdefence/config/jsons/building-config.json"),
                new TypeReference<ArrayList<BuildingConfig>>() {
                });
        for (BuildingConfig conf : bldCnfList) {
            BuildingConfigs.put(conf.id, conf);
        }

        ArrayList<EnemyConfig> enmCnfList = mapper.readValue(new File("com/mygdx/towerdefence/config/jsons/enemy-config.json"),
                new TypeReference<ArrayList<EnemyConfig>>() {
                });
        for (EnemyConfig conf : enmCnfList) {
            EnemyConfigs.put(conf.id, conf);
        }

        ArrayList<LevelConfig> lvlCnfList = mapper.readValue(new File("com/mygdx/towerdefence/config/jsons/level-config.json"),
                new TypeReference<ArrayList<LevelConfig>>() {
                });
        for (LevelConfig conf : lvlCnfList) {
            LevelConfigs.put(conf.id, conf);
        }

        ArrayList<WaveConfig> waveCnfList = mapper.readValue(new File("com/mygdx/towerdefence/config/jsons/wave-config.json"),
                new TypeReference<ArrayList<WaveConfig>>() {
                });
        for (WaveConfig conf : waveCnfList) {
            WaveConfigs.put(conf.id, conf);
        }
    }

    public Building getNewBuilding(int ID) {
        BuildingConfig config = BuildingConfigs.get(ID);
        Action action = getNewAction(config.id);
        return new Building(config, action);
    }

    public Enemy getNewEnemy(int ID) {
        EnemyConfig config = EnemyConfigs.get(ID);
        Action action = getNewAction(config.id);
        return new Enemy(config, action);
    }

    public EnemyConfig getEnemyConfig(int ID) {
        return EnemyConfigs.get(ID);
    }

    public BuildingConfig getBuildingConfig(int ID) {
        return BuildingConfigs.get(ID);
    }

    public Action getNewAction(int ID) {
        Action action = null;
        if (BuildingConfigs.get(ID) != null) {
            BuildingConfig config = BuildingConfigs.get(ID);
            switch (config.actionType) {
                case Attack:
                    action = new BasicAttackAction(config.actionRate, config.actionRange, config.actionParams);
                    break;
                case Nothing:
                    action = new DoNothingAction(config.actionRate, config.actionRange, config.actionParams);
                    break;
            }
        }
        return action;
    }

    public LevelConfig getLevelConfig(int ID) {
        return LevelConfigs.get(ID);
    }
}
