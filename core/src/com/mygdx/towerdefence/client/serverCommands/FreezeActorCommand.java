package com.mygdx.towerdefence.client.serverCommands;

import com.badlogic.gdx.utils.JsonValue;
import com.mygdx.towerdefence.events.eventClasses.FreezeActorEvent;
import com.mygdx.towerdefence.framework.screens.LevelScreen;

public class FreezeActorCommand implements ServerCommand{
    @Override
    public void execute(JsonValue msg) {
        int refID = msg.getInt("refID");
        boolean isEnemy = msg.getBoolean("isEnemy");
        float duration = msg.getFloat("duration");
        LevelScreen.eventQueue.addStateEvent(new FreezeActorEvent(duration, refID, isEnemy));
    }
}
