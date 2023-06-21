package com.mygdx.towerdefence.events;

import com.badlogic.gdx.math.MathUtils;
import com.mygdx.towerdefence.gameactor.Enemy;

public class SpawnEnemyEvent implements StateEvent{
    int enemyID, gridX, gridY, refID;

    public SpawnEnemyEvent(int enemyID, int gridX, int gridY, int refID) {
        this.enemyID = enemyID;
        this.gridX = gridX;
        this.gridY = gridY;
        this.refID = refID;
    }

    public SpawnEnemyEvent(int enemyID, int gridX, int gridY) {
        this(enemyID, gridX, gridY, -1);
    }

    @Override
    public void execute(StateHolder state) {
        Enemy newEnemy = state.getCreator().getNewEnemy(enemyID);
        newEnemy.setPosition(state.getMap().mapArr[gridX][gridY].x, state.getMap().mapArr[gridX][gridY].y);
        newEnemy.setMoveTarget(newEnemy.getPosition());

        if (refID == -1) {
            int refID = MathUtils.random(10000);
            while (state.getEnemies().containsKey(refID)) {
                refID = MathUtils.random(10000);
            }
            newEnemy.setRefID(refID);
            state.getEnemies().put(refID, newEnemy);
        }
        else {
            newEnemy.setRefID(refID);
            state.getEnemies().put(refID, newEnemy);
        }
    }
}
