package com.mygdx.towerdefence.events;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.towerdefence.framework.LevelView;
import com.mygdx.towerdefence.gameactor.Building;

public class SpawnBuildingEvent implements StateEvent {
    int buildingID, tileX, tileY;

    public SpawnBuildingEvent(int buildingID, int tileX, int tileY) {
        this.buildingID = buildingID;
        this.tileX = tileX;
        this.tileY = tileY;
    }

    @Override
    public void execute(StateHolder state) {
        Building newBuilding = state.getCreator().getNewBuilding(buildingID);
        newBuilding.setPosition(new Vector2(tileX * LevelView.TilE_SIZE, tileY * LevelView.TilE_SIZE));
        int refID = MathUtils.random(10000);
        while (state.getEnemies().containsKey(refID)) {
            refID = MathUtils.random(10000);
        }
        state.getBuildings().put(refID, newBuilding);
    }
}
