package com.mygdx.towerdefence.client.serverCommands;

import com.badlogic.gdx.utils.JsonValue;
import com.mygdx.towerdefence.events.eventClasses.SetCurrencyEvent;
import com.mygdx.towerdefence.framework.screens.LevelScreen;

public class MoneyChangedCommand implements ServerCommand {
    @Override
    public void execute(JsonValue msg) {
        int currency = msg.getInt("currency");
        LevelScreen.eventQueue.addStateEvent(new SetCurrencyEvent(currency));
    }
}
