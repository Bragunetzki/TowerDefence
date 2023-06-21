package com.mygdx.towerdefence.menu.tech_tree.actors;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

public class TowerButtonListener extends InputListener {
    TowerButton parent;

    public TowerButtonListener(TowerButton parent) {
        this.parent = parent;
    }

    @Override
    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        parent.techTreeMainScreen.goToTowerScreen(parent.towerId);
        return true;
    }
}
