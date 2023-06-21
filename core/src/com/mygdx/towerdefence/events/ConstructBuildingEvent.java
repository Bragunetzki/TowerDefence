package com.mygdx.towerdefence.events;

import com.badlogic.gdx.math.MathUtils;
import com.mygdx.towerdefence.framework.screens.LevelScreen;
import com.mygdx.towerdefence.gameactor.Building;
import com.mygdx.towerdefence.level.Tile;

public class ConstructBuildingEvent implements StateEvent {
    int buildingID, tileX, tileY, refID;

    public ConstructBuildingEvent(int buildingID, int tileX, int tileY, int refID) {
        this.buildingID = buildingID;
        this.tileX = tileX;
        this.tileY = tileY;
        this.refID = refID;
    }

    public ConstructBuildingEvent(int buildingID, int tileX, int tileY) {
        this(buildingID, tileX, tileY, -1);
    }

    @Override
    public void execute(StateHolder state) {
        int cost = state.getCreator().getBuildingConfig(buildingID).cost;
        if (cost > state.getCurrency()) return;

        Building newBuilding = state.getCreator().getNewBuilding(buildingID);
        Tile targetTile = state.getMap().mapArr[tileX][tileY];
        newBuilding.setPosition( targetTile.x, targetTile.y);
        LevelScreen.eventQueue.addStateEvent(new AlterCurrencyEvent(-cost));

        if (refID == -1) {
            int refID = MathUtils.random(10000);
            while (state.getEnemies().containsKey(refID)) {
                refID = MathUtils.random(10000);
            }
            newBuilding.setRefID(refID);
            state.getBuildings().put(refID, newBuilding);
        }
        else {
            newBuilding.setRefID(refID);
            state.getBuildings().put(refID, newBuilding);
        }
        LevelScreen.eventQueue.addViewEvent(new ConstructBuildingViewEvent(tileX, tileY));
    }
}
