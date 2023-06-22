package com.mygdx.towerdefence.events;

import com.mygdx.towerdefence.client.serverCommands.BuildingMessage;
import com.mygdx.towerdefence.client.serverCommands.EnemyMessage;
import com.mygdx.towerdefence.gameactor.Building;
import com.mygdx.towerdefence.gameactor.Enemy;
import com.mygdx.towerdefence.gameactor.GameActor;
import com.mygdx.towerdefence.level.Tile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ActorStateUpdateEvent implements StateEvent {
    private final List<EnemyMessage> enemyMessages;
    private final List<BuildingMessage> buildingMessages;

    public ActorStateUpdateEvent(List<EnemyMessage> enemyMessages, List<BuildingMessage> buildingMessages) {
        this.enemyMessages = enemyMessages;
        this.buildingMessages = buildingMessages;
    }

    //exists on server --> doesn't exist locally
    //exists on server --> exists locally
    //doesn't exist on server --> exists locally
    @Override
    public void execute(StateHolder state) {
        Map<Integer, GameActor> buildings = state.getBuildings();
        Map<Integer, GameActor> enemies = state.getEnemies();
        List<Integer> incomingRefIDs = new ArrayList<>();
        List<Integer> missingIDs = new ArrayList<>();

        for (EnemyMessage msg : enemyMessages) {
            int refID = msg.refID;
            incomingRefIDs.add(refID);
            //update existing
            if (enemies.containsKey(refID)) {
                Enemy e = (Enemy) enemies.get(refID);
                int damage = e.getHealth() - msg.health;
                e.applyDamage(damage);
                e.setPosition(msg.x, msg.y);
            }
            //create new
            else {
                Enemy newEnemy = state.getCreator().getNewEnemy(msg.id);
                newEnemy.setPosition(msg.x, msg.y);
                newEnemy.setMoveTarget(newEnemy.getPosition());
                newEnemy.setRefID(refID);
                state.getEnemies().put(refID, newEnemy);
            }
        }
        //delete missing
        for (int refID : enemies.keySet()) {
            if (!incomingRefIDs.contains(refID)) {
                missingIDs.add(refID);
            }
        }
        for (int refID : missingIDs) {
            enemies.remove(refID);
        }

        incomingRefIDs.clear();
        missingIDs.clear();

        for (BuildingMessage msg : buildingMessages) {
            int refID = msg.refID;
            incomingRefIDs.add(refID);
            //update existing
            if (buildings.containsKey(refID)) {
                Building e = (Building) buildings.get(refID);
                e.setHealth(msg.health);
                Tile targetTile = state.getMap().mapArr[msg.gridX][msg.gridY];
                e.setPosition( targetTile.x, targetTile.y);
                e.setBuildTime(msg.buildTimeRemaining);
            }
            //create new
            else {
                Building newBuilding = state.getCreator().getNewBuilding(msg.id);
                Tile targetTile = state.getMap().mapArr[msg.gridX][msg.gridY];
                newBuilding.setPosition(targetTile.x, targetTile.y);
                newBuilding.setRefID(refID);
                state.getBuildings().put(refID, newBuilding);
            }
        }
        //delete missing
        for (int refID : buildings.keySet()) {
            if (!incomingRefIDs.contains(refID)) {
                missingIDs.add(refID);
            }
        }
        for (int refID : missingIDs) {
            buildings.remove(refID);
        }
    }
}
