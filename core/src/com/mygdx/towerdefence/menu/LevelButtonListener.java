package com.mygdx.towerdefence.menu;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

public class LevelButtonListener extends InputListener {
    LevelButton parent;

    public LevelButtonListener(LevelButton parent) {
        this.parent = parent;
    }

    @Override
    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        parent.startLevel();
        return true;
    }
}
