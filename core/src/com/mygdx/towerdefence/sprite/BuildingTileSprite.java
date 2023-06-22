package com.mygdx.towerdefence.sprite;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.towerdefence.inputListeners.Hoverable;
import com.mygdx.towerdefence.inputListeners.HoverableListener;
import com.mygdx.towerdefence.inputListeners.TileListener;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.scaleTo;
import static com.mygdx.towerdefence.framework.LevelView.TilE_SIZE;

public class BuildingTileSprite extends GameSprite implements Hoverable {
    private final int tileX;
    private final int tileY;
    private boolean hoverable;

    private final boolean isClaimed;

    public BuildingTileSprite(TextureRegion texture, int tileX, int tileY, float width, float height, boolean isOwned) {
        super(texture, TilE_SIZE * tileX, TilE_SIZE * tileY, width, height);
        this.isClaimed = isOwned;
        if (isOwned) {
            addListener(new HoverableListener(this));
            addListener(new TileListener(this));
        }
        this.tileX = tileX;
        this.tileY = tileY;
        hoverable = isOwned;
    }

    @Override
    public boolean isHoverable() {
        return hoverable;
    }

    @Override
    public void hover() {
        addAction(scaleTo(1.05f, 1.05f, 0.2f));
    }

    @Override
    public void unhover() {
        addAction(scaleTo(1, 1, 0.2f));
    }

    @Override
    public void setHoverable(boolean hoverable) {
        this.hoverable = hoverable;
    }

    public int getTileX() {
        return tileX;
    }

    public int getTileY() {
        return tileY;
    }

    public boolean isOwned() {
        return isClaimed;
    }
}
