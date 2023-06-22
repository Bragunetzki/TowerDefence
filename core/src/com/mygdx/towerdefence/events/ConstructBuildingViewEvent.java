package com.mygdx.towerdefence.events;

import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.mygdx.towerdefence.sprite.BuildingTileSprite;

public class ConstructBuildingViewEvent implements ViewEvent {
    int x, y;
    public ConstructBuildingViewEvent(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public void execute(ViewHolder view) {
        BuildingTileSprite tile = view.getTile(x, y);
        if (tile.isOwned()) {
            tile.setTouchable(Touchable.disabled);
            tile.setHoverable(false);
        }
    }
}
