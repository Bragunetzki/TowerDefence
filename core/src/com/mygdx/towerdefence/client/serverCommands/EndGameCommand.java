package com.mygdx.towerdefence.client.serverCommands;

import com.badlogic.gdx.utils.JsonValue;
import com.mygdx.towerdefence.events.eventClasses.LevelEndEvent;
import com.mygdx.towerdefence.framework.screens.LevelScreen;

public class EndGameCommand implements ServerCommand {
    @Override
    public void execute(JsonValue msg) {
        boolean victory = msg.getBoolean("victory");
        int reward = msg.getInt("reward");
        LevelScreen.eventQueue.addStateEvent(new LevelEndEvent(victory, reward));
    }
}
