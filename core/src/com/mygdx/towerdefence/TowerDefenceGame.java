package com.mygdx.towerdefence;

import com.badlogic.gdx.Game;
import com.mygdx.towerdefence.config.Creator;
import com.mygdx.towerdefence.framework.AssetLoader;
import com.mygdx.towerdefence.framework.screens.LoadingScreen;

public class TowerDefenceGame extends Game {
    private AssetLoader assets;
    private Creator creator;

    @Override
    public void create() {
        assets = new AssetLoader();
        assets.loadAll();
        creator = new Creator();
        this.setScreen(new LoadingScreen(this));
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        assets.dispose();
        if (screen != null) screen.dispose();
    }

    public AssetLoader getAssetLoader() {
        return assets;
    }

    public Creator getCreator() {
        return creator;
    }
}
