package com.mygdx.towerdefence.client.serverCommands;

import com.badlogic.gdx.utils.JsonValue;
import com.mygdx.towerdefence.events.eventClasses.ConstructBuildingEvent;
import com.mygdx.towerdefence.framework.screens.LevelScreen;

public class ConstructBuildingCommand implements ServerCommand {
    @Override
    public void execute(JsonValue msg) {
        int refID = msg.getInt("refID");
        int id = msg.getInt("id");
        int gridX = msg.getInt("gridX");
        int gridY = msg.getInt("gridY");
        LevelScreen.eventQueue.addStateEvent(new ConstructBuildingEvent(id, gridX, gridY, refID));
    }
}
