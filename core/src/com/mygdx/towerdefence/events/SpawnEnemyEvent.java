package com.mygdx.towerdefence.events;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.towerdefence.framework.LevelView;
import com.mygdx.towerdefence.gameactor.Enemy;

public class SpawnEnemyEvent implements StateEvent{
    int enemyID, tileX, tileY;

    public SpawnEnemyEvent(int enemyID, int tileX, int tileY) {
        this.enemyID = enemyID;
        this.tileX = tileX;
        this.tileY = tileY;
    }

    @Override
    public void execute(StateHolder state) {
        Enemy newEnemy = state.getCreator().getNewEnemy(enemyID);
        newEnemy.setPosition(new Vector2(tileX * LevelView.TilE_SIZE, tileY * LevelView.TilE_SIZE));
        newEnemy.setMoveTarget(newEnemy.getPosition());
        int refID = MathUtils.random(10000);
        while (state.getEnemies().containsKey(refID)) {
            refID = MathUtils.random(10000);
        }
        state.getEnemies().put(refID, newEnemy);
    }
}
