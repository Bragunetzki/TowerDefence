package com.mygdx.towerdefence.client.serverCommands;

import com.badlogic.gdx.utils.JsonValue;
import com.mygdx.towerdefence.events.ActorStateUpdateEvent;
import com.mygdx.towerdefence.framework.screens.LevelScreen;

import java.util.ArrayList;
import java.util.List;

public class ActorStateUpdateCommand implements ServerCommand {
    @Override
    public void execute(JsonValue msg) {
        List<EnemyMessage> enemies = new ArrayList<>();
        List<BuildingMessage> buildings = new ArrayList<>();
        if (msg.has("enemies")) {
            JsonValue jsonEnemies = msg.get("enemies");
            for (JsonValue jsonEnemy = jsonEnemies.child; jsonEnemy != null; jsonEnemy = jsonEnemies.next) {
                int refID = jsonEnemy.getInt("refID");
                int id = jsonEnemy.getInt("id");
                int health = jsonEnemy.getInt("health");
                float x = jsonEnemy.getFloat("x");
                float y = jsonEnemy.getFloat("y");
                enemies.add(new EnemyMessage(refID, id, health, x, y));
            }
        }
        if (msg.has("buildings")) {
            JsonValue jsonBuildings = msg.get("buildings");
            for (JsonValue jsonBuilding = jsonBuildings.child; jsonBuilding != null; jsonBuilding = jsonBuildings.next) {
                int refID = jsonBuilding.getInt("refID");
                int id = jsonBuilding.getInt("id");
                int health = jsonBuilding.getInt("health");
                int gridX = jsonBuilding.getInt("gridX");
                int gridY = jsonBuilding.getInt("gridY");
                float buildTimeRemaining = jsonBuilding.getFloat("buildTimeRemaining");
                buildings.add(new BuildingMessage(refID, id, health, gridX, gridY, buildTimeRemaining));
            }
        }

        LevelScreen.eventQueue.addStateEvent(new ActorStateUpdateEvent(enemies, buildings));
    }
}
