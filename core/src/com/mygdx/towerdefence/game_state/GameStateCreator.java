package com.mygdx.towerdefence.game_state;

import com.mygdx.towerdefence.config.Creator;
import com.mygdx.towerdefence.config.config_classes.BuildingConfig;
import com.mygdx.towerdefence.config.config_classes.TechTreeNodeConfig;
import com.mygdx.towerdefence.config.config_classes.TechTreeUpgradeConfig;

import java.util.List;

public class GameStateCreator extends Creator {

    public void updateBuildingConfig(int towerId, int upgradeId) {
        List<TechTreeNodeConfig> techTreeNodeConfigs = this.getTechTree().rootNodes.get(towerId);
        TechTreeNodeConfig techTreeNodeConfig = null;
        for (TechTreeNodeConfig config : techTreeNodeConfigs) {
            if (config.id == upgradeId) {
                techTreeNodeConfig = config;
                break;
            }
        }
        if (techTreeNodeConfig == null) return;

        if (towerId != -1) {
            BuildingConfig buildingConfig = this.getBuildingMap().get(towerId);
            updateOneConfig(techTreeNodeConfig, buildingConfig);
        } else {
            for (BuildingConfig buildingConfig : this.getBuildingMap().values()) {
                updateOneConfig(techTreeNodeConfig, buildingConfig);
            }
        }
    }

    private void updateOneConfig(TechTreeNodeConfig techTreeNodeConfig, BuildingConfig buildingConfig) {
        for (TechTreeUpgradeConfig upgrade : techTreeNodeConfig.upgrades) {
            switch (upgrade.upgradedParameter) {
                case "maxHealth": {
                    buildingConfig.maxHealth *= upgrade.modifier;
                    continue;
                }
                case "cost": {
                    buildingConfig.cost *= upgrade.modifier;
                    continue;
                }
                case "demolitionCurrency": {
                    buildingConfig.demolitionCurrency *= upgrade.modifier;
                    continue;
                }
                case "actionRate": {
                    buildingConfig.actionRate *= upgrade.modifier;
                    continue;
                }
                case "actionRange": {
                    buildingConfig.actionRange *= upgrade.modifier;
                    continue;
                }
                default: {
                    if (buildingConfig.actionParams.containsKey(upgrade.upgradedParameter)) {
                        buildingConfig.actionParams.put(upgrade.upgradedParameter,
                                buildingConfig.actionParams.get(upgrade.upgradedParameter) * upgrade.modifier);
                    } else {
                        System.out.println(upgrade.upgradedParameter);
                    }
                }
            }
        }
        this.getBuildingMap().put(buildingConfig.id, buildingConfig);
    }

}
