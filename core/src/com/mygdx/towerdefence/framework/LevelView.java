package com.mygdx.towerdefence.framework;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.mygdx.towerdefence.TowerDefenceGame;
import com.mygdx.towerdefence.config.BuildingConfig;
import com.mygdx.towerdefence.config.Creator;
import com.mygdx.towerdefence.config.LevelConfig;
import com.mygdx.towerdefence.events.SpawnBuildingEvent;
import com.mygdx.towerdefence.events.StateHolder;
import com.mygdx.towerdefence.events.ViewHolder;
import com.mygdx.towerdefence.gameactor.GameActor;
import com.mygdx.towerdefence.level.Tile;
import com.mygdx.towerdefence.screens.BasicScreen;
import com.mygdx.towerdefence.screens.LevelScreen;
import com.mygdx.towerdefence.sprite.BuildingTile;
import com.mygdx.towerdefence.sprite.GameSprite;

import java.util.*;

public class LevelView extends Stage implements ViewHolder {
    public final static float TilE_SIZE = 50;
    private final Map<Integer, GameSprite> enemies;
    private final Map<Integer, GameSprite> buildings;
    private final AssetLoader assets;
    private final Creator creator;
    private final Texture[][] mapTextures;
    private final List<BuildingTile> tileList;
    public static final float WORLD_SIZE_X = 1920;
    public static final float WORLD_SIZE_Y = 1080;

    public LevelView(BasicScreen screen, TowerDefenceGame game, int levelID) {
        super(screen.getViewport());
        enemies = new HashMap<>();
        buildings = new HashMap<>();
        assets = game.getAssetLoader();
        creator = game.getCreator();
        LevelConfig levelConfig = creator.getLevelConfig(levelID);
        tileList = new LinkedList<>();

        Texture backgroundTexture = assets.getTexture(levelConfig.backgroundTextureName);
        Texture plotTexture = assets.getTexture(levelConfig.plotTextureName);
        Texture roadTexture = assets.getTexture(levelConfig.roadTextureName);

        Tile[][] map = levelConfig.tileMap;
        mapTextures = new Texture[map.length][map[0].length];

        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                switch (map[i][j].type) {
                    case Background:
                        mapTextures[i][j] = backgroundTexture;
                        break;
                    case Plot:
                        BuildingTile tile = new BuildingTile(new TextureRegion(plotTexture), i, j, TilE_SIZE, TilE_SIZE);
                        addActor(tile);
                        tileList.add(tile);
                        mapTextures[i][j] = null;
                        break;
                    case Road:
                        mapTextures[i][j] = roadTexture;
                        break;
                }
            }
        }
    }

    @Override
    public void draw() {
        for (int i = 0; i < mapTextures.length; i++) {
            for (int j = 0; j < mapTextures[0].length; j++) {
                if (mapTextures[i][j] != null)
                    this.getBatch().draw(mapTextures[i][j], i * TilE_SIZE, j * TilE_SIZE, (i + 1) * TilE_SIZE, (j + 1) * TilE_SIZE);
            }
        }
        super.draw();
    }

    @Override
    public void update(StateHolder state) {
        syncSprites(enemies, state.getEnemies(), true);
        syncSprites(buildings, state.getBuildings(), false);
    }

    @Override
    public void showBuildingDialog(final int tileX, final int tileY) {
        final Map<Integer, BuildingConfig> buildings = creator.getBuildingMap();
        final Dialog dialog = new Dialog("BuildingSelection", assets.getSkin()) {
            @Override
            protected void result(Object object) {
                LevelScreen.eventQueue.addStateEvent(new SpawnBuildingEvent((Integer) object, tileX, tileY));
            }
        };
        dialog.text("Select Building:");
        for (int i : buildings.keySet()) {
            if (i == 0) continue;
            dialog.button(buildings.get(i).name, i);
        }
        dialog.setPosition(TilE_SIZE * tileX, TilE_SIZE * tileY);
        dialog.show(this);
    }

    //да, это костыль, но вы сами не хотели обобщать врагов и башен
    private void syncSprites(Map<Integer, GameSprite> sprites, Map<Integer, GameActor> actors, boolean isEnemy) {
        for (int refID : actors.keySet()) {
            GameActor actor = actors.get(refID);

            if (sprites.containsKey(refID)) { //update existing actor accordingly
                sprites.get(refID).setPosition(actor.getPosition().x, actor.getPosition().y);
            } else { //no corresponding actor sprite exists
                if (isEnemy)
                    addEnemySprite(actor, refID);
                else addBuildingSprite(actor, refID);
            }
        }

        //remove nonexisting actors;
        List<Integer> removal = new ArrayList<>();
        for (int refID : sprites.keySet()) {
            if (!actors.containsKey(refID)) {
                removal.add(refID);
            }
        }
        for (Integer refID : removal) {
            GameSprite actor = sprites.get(refID);
            actor.remove();
            sprites.remove(refID);
        }
    }

    private void addEnemySprite(GameActor actor, int refID) {
        String textureName = creator.getEnemyConfig(actor.getID()).SpriteName;
        TextureRegion texture = new TextureRegion(assets.getEnemyTexture(textureName));
        enemies.put(refID, new GameSprite(texture, actor.getPosition().x, actor.getPosition().y, texture.getRegionWidth(), texture.getRegionHeight()));
        addActor(enemies.get(refID));
    }

    private void addBuildingSprite(GameActor actor, int refID) {
        String textureName = creator.getBuildingConfig(actor.getID()).SpriteName;
        TextureRegion texture = new TextureRegion(assets.getBuildingTexture(textureName));
        buildings.put(refID, new GameSprite(texture, actor.getPosition().x, actor.getPosition().y, texture.getRegionWidth(), texture.getRegionHeight()));
        addActor(buildings.get(refID));
    }
}
