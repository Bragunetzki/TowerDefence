package com.mygdx.towerdefence.framework;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class AssetLoader {
    private final AssetManager assets;
    private final BitmapFont font;
    private final Skin skin;

    public AssetLoader() {
        skin = new Skin(Gdx.files.internal("styles/uiskin.json"), new TextureAtlas(Gdx.files.internal("styles/uiskin.atlas")));
        assets = new AssetManager();
        font = new BitmapFont();
    }

    public void loadAll() {

    }

    public BitmapFont getFont() {
        return font;
    }

    public Texture getTexture(String filename) {
        return assets.get(filename, Texture.class);
    }

    public Texture getEnemyTexture(String textureName) {
        return getTexture("sprites/enemies/" + textureName);
    }

    public Texture getBuildingTexture(String textureName) {
        return getTexture("sprites/buildings/" + textureName);
    }

    public void dispose() {
        assets.dispose();
        font.dispose();
    }

    public boolean isLoaded() {
        return assets.update();
    }

    public float progress() {
        return assets.getProgress();
    }

    public Skin getSkin() {
        return skin;
    }
}
