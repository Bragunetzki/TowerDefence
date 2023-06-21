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
        assets.load("sprites/enemies/boar.png", Texture.class);
        assets.load("sprites/enemies/boarRanger.png", Texture.class);
        assets.load("sprites/buildings/tower.png", Texture.class);
        assets.load("sprites/buildings/base.png", Texture.class);
        assets.load("sprites/buildings/mine.png", Texture.class);
        assets.load("background.png", Texture.class);
        assets.load("road.png", Texture.class);
        assets.load("plot.png", Texture.class);
        assets.load("sprites/projectile.png", Texture.class);
        assets.load("line.png", Texture.class);
    }

    public BitmapFont getFont() {
        return new BitmapFont(
                Gdx.files.internal("styles/font-export.fnt"),
                skin.getAtlas().findRegion("font-export")
        );
    }


    public BitmapFont getTitleFont() {
        return new BitmapFont(
                Gdx.files.internal("styles/font-title-export.fnt"),
                skin.getAtlas().findRegion("font-title-export")
        );
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
