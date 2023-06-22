package com.mygdx.towerdefence.client.serverCommands;

import com.badlogic.gdx.utils.JsonValue;
import com.mygdx.towerdefence.events.eventClasses.DamageActorEvent;
import com.mygdx.towerdefence.framework.screens.LevelScreen;

public class DamageActorCommand implements ServerCommand {
    @Override
    public void execute(JsonValue msg) {
        int refID = msg.getInt("refID");
        boolean isEnemy = msg.getBoolean("isEnemy");
        int damage = msg.getInt("damage");
        LevelScreen.eventQueue.addStateEvent(new DamageActorEvent(damage, refID, isEnemy));
    }
}
