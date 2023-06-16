package com.mygdx.towerdefence.inputListeners;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.mygdx.towerdefence.events.BuildingTouchedEvent;
import com.mygdx.towerdefence.framework.screens.LevelScreen;

public class BuildingListener extends InputListener {
    private final int refID;
    private final int id;
    public BuildingListener(int refID, int id) {
        this.refID = refID;
        this.id = id;
    }

    @Override
    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        LevelScreen.eventQueue.addViewEvent(new BuildingTouchedEvent(refID, id));
        return true;
    }
}
