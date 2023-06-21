package com.mygdx.towerdefence.menu.tech_tree.actors;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

public class TechButtonListener extends InputListener {
    private final TechButton parent;

    public TechButtonListener(TechButton parent) {
        this.parent = parent;
    }

    @Override
    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        parent.showDialog();
        return super.touchDown(event, x, y, pointer, button);
    }
}
