package com.mygdx.towerdefence.events;

import com.mygdx.towerdefence.sprite.BuildingTile;

public interface ViewHolder {
    void update(StateHolder state);

    void showBuildingDialog(int tileX, int tileY);

    BuildingTile getTile(int tileX, int tileY);

    void addProjectile(int damage, float x, float y, int targetRefID, boolean targetsEnemy);
}
