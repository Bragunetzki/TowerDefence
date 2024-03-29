package com.mygdx.towerdefence.events;

import com.mygdx.towerdefence.events.eventClasses.StateHolder;
import com.mygdx.towerdefence.sprite.BuildingTileSprite;

public interface ViewHolder {
    void update(StateHolder state);

    void showConstructionDialog(int tileX, int tileY);

    void showBuildingDialog(int refID, int id);

    BuildingTileSprite getTile(int tileX, int tileY);

    void addProjectile(int damage, float x, float y, int targetRefID, boolean targetsEnemy);

    void showEndDialog(boolean victory, int reward);

    void setTimerLabel(float time);
}
