package com.mygdx.towerdefence.client.serverCommands;

import com.badlogic.gdx.utils.JsonValue;

public class MineCurrencyCommand implements ServerCommand {
    @Override
    public void execute(JsonValue msg) {
        int refID = msg.getInt("refID");
        int amount = msg.getInt("amount");
        return;
    }
}
