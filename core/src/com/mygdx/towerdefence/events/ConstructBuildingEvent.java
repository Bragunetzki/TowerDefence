package com.mygdx.towerdefence.events;

import com.badlogic.gdx.math.MathUtils;
import com.mygdx.towerdefence.framework.screens.LevelScreen;
import com.mygdx.towerdefence.gameactor.Building;

public class ConstructBuildingEvent implements StateEvent {
    int buildingID, tileX, tileY;

    public ConstructBuildingEvent(int buildingID, int tileX, int tileY) {
        this.buildingID = buildingID;
        this.tileX = tileX;
        this.tileY = tileY;
    }

    @Override
    public void execute(StateHolder state) {
        int cost = state.getCreator().getBuildingConfig(buildingID).cost;

        if (cost > state.getCurrency()) return;

        Building newBuilding = state.getCreator().getNewBuilding(buildingID);
        newBuilding.setPosition( state.getMap().mapArr[tileX][tileY].x, state.getMap().mapArr[tileX][tileY].y);
        int refID = MathUtils.random(10000);
        while (state.getEnemies().containsKey(refID)) {
            refID = MathUtils.random(10000);
        }
        newBuilding.setRefID(refID);
        state.getBuildings().put(refID, newBuilding);
        LevelScreen.eventQueue.addViewEvent(new ConstructBuildingViewEvent(tileX, tileY));
    }
}
