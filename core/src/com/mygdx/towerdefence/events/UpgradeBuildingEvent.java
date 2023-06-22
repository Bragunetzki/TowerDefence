package com.mygdx.towerdefence.events;

import com.mygdx.towerdefence.config.config_classes.BuildingConfig;
import com.mygdx.towerdefence.config.config_classes.BuildingUpgradeConfig;
import com.mygdx.towerdefence.framework.screens.LevelScreen;
import com.mygdx.towerdefence.gameactor.Building;

public class UpgradeBuildingEvent implements StateEvent {
    private final int refID, upgradeID;

    public UpgradeBuildingEvent(int refID, int upgradeID) {
        this.refID = refID;
        this.upgradeID = upgradeID;
    }

    @Override
    public void execute(StateHolder state) {
        Building building = (Building) state.getBuildings().get(refID);
        BuildingConfig config = state.getCreator().getBuildingConfig(building.getID());
        BuildingUpgradeConfig upgrade = config.upgrades.get(upgradeID);
        if (upgrade.cost > state.getCurrency()) return;

        LevelScreen.eventQueue.addStateEvent(new AlterCurrencyEvent(-upgrade.cost));
        building.applyUpgrade(upgrade, upgradeID);
    }
}
