package com.mygdx.towerdefence.client.serverCommands;

import com.badlogic.gdx.utils.JsonValue;
import com.mygdx.towerdefence.events.eventClasses.SpawnEnemyEvent;
import com.mygdx.towerdefence.framework.screens.LevelScreen;

public class SpawnEnemyCommand implements ServerCommand {

    @Override
    public void execute(JsonValue msg) {
        int id = msg.getInt("id");
        int gridX = msg.getInt("gridX");
        int gridY = msg.getInt("gridY");
        int refID = msg.getInt("refID");

        LevelScreen.eventQueue.addStateEvent(new SpawnEnemyEvent(id, gridX, gridY, refID));
    }
}
