package com.mygdx.towerdefence.events;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.towerdefence.config.EnemyConfig;
import com.mygdx.towerdefence.framework.screens.LevelScreen;
import com.mygdx.towerdefence.level.Tile;

public class ActorDeathEvent implements StateEvent {
    private final int refID;
    private final boolean isEnemy;

    public ActorDeathEvent(int refID, boolean isEnemy) {
        this.refID = refID;
        this.isEnemy = isEnemy;
    }

    @Override
    public void execute(StateHolder state) {
        if (isEnemy) {
            EnemyConfig config = state.getCreator().getEnemyConfig(state.getEnemies().get(refID).getID());
            LevelScreen.eventQueue.addStateEvent(new AlterCurrencyEvent(config.reward));
            state.getEnemies().remove(refID);
        } else {
            Vector2 pos = state.getBuildings().get(refID).getPosition();
            Tile tile = state.getMap().positionToTile(pos.x, pos.y);
            state.getBuildings().remove(refID);
            LevelScreen.eventQueue.addViewEvent(new BuildingDestroyedViewEvent(tile.gridX, tile.gridY));
        }
    }
}
