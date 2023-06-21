package com.mygdx.towerdefence.client.serverCommands;

import com.badlogic.gdx.utils.JsonValue;


public interface ServerCommand {
    void execute(JsonValue msg);
}
