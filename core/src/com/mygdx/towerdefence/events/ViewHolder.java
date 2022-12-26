package com.mygdx.towerdefence.events;

import com.mygdx.towerdefence.sprite.BuildingTileSprite;

public interface ViewHolder {
    void update(StateHolder state);

    void showBuildingDialog(int tileX, int tileY);

    BuildingTileSprite getTile(int tileX, int tileY);

    void addProjectile(int damage, float x, float y, int targetRefID, boolean targetsEnemy);
}
