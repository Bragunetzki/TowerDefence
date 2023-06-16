package com.mygdx.towerdefence.events;

import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.mygdx.towerdefence.sprite.BuildingTileSprite;

public class BuildingDestroyedViewEvent implements ViewEvent {
    private final int x, y;

    public BuildingDestroyedViewEvent(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public void execute(ViewHolder view) {
        BuildingTileSprite tile = view.getTile(x, y);
        tile.setTouchable(Touchable.enabled);
        tile.setHoverable(true);
    }
}
