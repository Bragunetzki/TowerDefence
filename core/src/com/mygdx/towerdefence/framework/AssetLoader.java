package com.mygdx.towerdefence.framework;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import java.io.File;

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
        loadDir("assets");
    }

    private void loadDir(String pathname) {
        File[] files = (new File(pathname)).listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                if (file.getName().equals("configs") || file.getName().equals("styles")) continue;
                loadDir(file.getPath());
            } else {
                String path = file.getPath();
                String fileName = path.substring(7).replace("\\", "/");
                assets.load(fileName, Texture.class);
            }
        }

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
