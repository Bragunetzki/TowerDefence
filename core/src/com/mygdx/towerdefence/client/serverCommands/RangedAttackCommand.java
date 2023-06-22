package com.mygdx.towerdefence.client.serverCommands;

import com.badlogic.gdx.utils.JsonValue;
import com.mygdx.towerdefence.events.eventClasses.RangedAttackEvent;
import com.mygdx.towerdefence.framework.screens.LevelScreen;

public class RangedAttackCommand implements ServerCommand {
    @Override
    public void execute(JsonValue msg) {
        float x = msg.getFloat("x");
        float y = msg.getFloat("y");
        int targetRefID = msg.getInt("targetRefID");
        boolean isAttackerEnemy = msg.getBoolean("isAttackerEnemy");

        LevelScreen.eventQueue.addViewEvent(new RangedAttackEvent(0, x, y, targetRefID, !isAttackerEnemy));
    }
}
