package com.mygdx.towerdefence.events;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.towerdefence.config.config_classes.EnemyConfig;
import com.mygdx.towerdefence.framework.screens.LevelScreen;
import com.mygdx.towerdefence.gameactor.GameActor;
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
            GameActor enemy = state.getEnemies().get(refID);
            if (enemy == null) return;
            EnemyConfig config = state.getCreator().getEnemyConfig(enemy.getID());
            LevelScreen.eventQueue.addStateEvent(new AlterCurrencyEvent(config.reward));
            state.getEnemies().remove(refID);
        } else {
            GameActor building = state.getBuildings().get(refID);
            if (building == null) return;
            int id = building.getID();
            Vector2 pos = building.getPosition();
            Tile tile = state.getMap().positionToTile(pos.x, pos.y);
            state.getBuildings().remove(refID);
            LevelScreen.eventQueue.addViewEvent(new BuildingDestroyedViewEvent(tile.gridX, tile.gridY));
            if (id == 0) {
                LevelScreen.eventQueue.addStateEvent(new LevelEndEvent(false, 0));
            }
        }
    }
}
