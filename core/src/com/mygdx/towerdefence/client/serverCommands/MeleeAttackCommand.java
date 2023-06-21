package com.mygdx.towerdefence.client.serverCommands;

import com.badlogic.gdx.utils.JsonValue;

public class MeleeAttackCommand implements ServerCommand {
    @Override
    public void execute(JsonValue msg) {
        int attackerRefID = msg.getInt("attackerRefID");
        int targetRefID = msg.getInt("targetRefID");
        boolean isAttackerEnemy = msg.getBoolean("isAttackerEnemy");
        return;
    }
}
