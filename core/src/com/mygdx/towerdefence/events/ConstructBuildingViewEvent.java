package com.mygdx.towerdefence.events;

import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.mygdx.towerdefence.sprite.BuildingTile;

public class ConstructBuildingViewEvent implements ViewEvent {
    int x, y;
    public ConstructBuildingViewEvent(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public void execute(ViewHolder view) {
        BuildingTile tile = view.getTile(x, y);
        tile.setTouchable(Touchable.disabled);
    }
}
