package com.mygdx.towerdefence.client.serverCommands;

import com.badlogic.gdx.utils.JsonValue;
import com.mygdx.towerdefence.events.eventClasses.ActorDeathEvent;
import com.mygdx.towerdefence.framework.screens.LevelScreen;

public class ActorDeathCommand implements ServerCommand {
    @Override
    public void execute(JsonValue msg) {
        int refID = msg.getInt("refID");
        boolean isEnemy = msg.getBoolean("isEnemy");
        LevelScreen.eventQueue.addStateEvent(new ActorDeathEvent(refID, isEnemy));
    }
}
