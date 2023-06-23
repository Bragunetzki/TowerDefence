package com.mygdx.towerdefence.client.serverCommands;

import com.badlogic.gdx.utils.JsonValue;
import com.mygdx.towerdefence.events.eventClasses.FreezingEffectEvent;
import com.mygdx.towerdefence.framework.screens.LevelScreen;

public class FreezeCommand implements ServerCommand{
    @Override
    public void execute(JsonValue msg) {
        float x = msg.getInt("x");
        float y = msg.getInt("y");
        float duration = msg.getFloat("duration");
        LevelScreen.eventQueue.addViewEvent(new FreezingEffectEvent(duration, x, y));
    }
}
