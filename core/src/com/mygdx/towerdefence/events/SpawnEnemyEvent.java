package com.mygdx.towerdefence.events;

import com.badlogic.gdx.math.MathUtils;
import com.mygdx.towerdefence.gameactor.Enemy;

public class SpawnEnemyEvent implements StateEvent{
    int enemyID, gridX, gridY;

    public SpawnEnemyEvent(int enemyID, int gridX, int gridY) {
        this.enemyID = enemyID;
        this.gridX = gridX;
        this.gridY = gridY;
    }

    @Override
    public void execute(StateHolder state) {
        Enemy newEnemy = state.getCreator().getNewEnemy(enemyID);
        newEnemy.setPosition(state.getMap().mapArr[gridX][gridY].x, state.getMap().mapArr[gridX][gridY].y);
        newEnemy.setMoveTarget(newEnemy.getPosition());
        int refID = MathUtils.random(10000);
        while (state.getEnemies().containsKey(refID)) {
            refID = MathUtils.random(10000);
        }
        newEnemy.setRefID(refID);
        state.getEnemies().put(refID, newEnemy);
    }
}
