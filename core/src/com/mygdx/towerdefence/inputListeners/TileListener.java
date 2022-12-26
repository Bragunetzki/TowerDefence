package com.mygdx.towerdefence.inputListeners;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.mygdx.towerdefence.events.TileTouchedEvent;
import com.mygdx.towerdefence.screens.LevelScreen;
import com.mygdx.towerdefence.sprite.BuildingTileSprite;

public class TileListener extends InputListener {
    BuildingTileSprite parent;
    public TileListener(BuildingTileSprite parent) {
        this.parent = parent;
    }

    @Override
    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        LevelScreen.eventQueue.addViewEvent(new TileTouchedEvent(parent.getTileX(), parent.getTileY()));
        return true;
    }
}
