package com.mygdx.towerdefence.framework;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.mygdx.towerdefence.TowerDefenceGame;
import com.mygdx.towerdefence.config.BuildingConfig;
import com.mygdx.towerdefence.config.Creator;
import com.mygdx.towerdefence.config.LevelConfig;
import com.mygdx.towerdefence.events.AlterCurrencyEvent;
import com.mygdx.towerdefence.events.ConstructBuildingEvent;
import com.mygdx.towerdefence.events.StateHolder;
import com.mygdx.towerdefence.events.ViewHolder;
import com.mygdx.towerdefence.gameactor.GameActor;
import com.mygdx.towerdefence.level.Tile;
import com.mygdx.towerdefence.screens.BasicScreen;
import com.mygdx.towerdefence.screens.LevelScreen;
import com.mygdx.towerdefence.sprite.BuildingTile;
import com.mygdx.towerdefence.sprite.GameActorView;
import com.mygdx.towerdefence.sprite.Projectile;

import java.util.*;

public class LevelView extends Stage implements ViewHolder {
    public static final float WORLD_SIZE_X = 1920;
    public static final float WORLD_SIZE_Y = 1080;
    public final static float TilE_SIZE = 50;

    private final Map<Integer, GameActorView> enemies;
    private final Map<Integer, GameActorView> buildings;
    private final AssetLoader assets;
    private final Creator creator;
    private final Texture[][] mapTextures;
    private final List<BuildingTile> tileList;
    private final Label currencyLabel;

    public LevelView(BasicScreen screen, TowerDefenceGame game, int levelID) {
        super(screen.getViewport());
        enemies = new HashMap<>();
        buildings = new HashMap<>();
        assets = game.getAssetLoader();
        creator = game.getCreator();
        LevelConfig levelConfig = creator.getLevelConfig(levelID);
        tileList = new LinkedList<>();
        currencyLabel = new Label("0", assets.getSkin());
        currencyLabel.setPosition(0, getHeight() * 0.9f);
        addActor(currencyLabel);

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
        syncActors(enemies, state.getEnemies(), true);
        syncActors(buildings, state.getBuildings(), false);
        currencyLabel.setText(state.getCurrency());
    }

    @Override
    public void showBuildingDialog(final int tileX, final int tileY) {
        final Map<Integer, BuildingConfig> buildings = creator.getBuildingMap();
        final Dialog dialog = new Dialog("BuildingSelection", assets.getSkin()) {
            @Override
            protected void result(Object object) {
                int cost = buildings.get((Integer) object).cost;
                LevelScreen.eventQueue.addStateEvent(new ConstructBuildingEvent((Integer) object, tileX, tileY));
                LevelScreen.eventQueue.addStateEvent(new AlterCurrencyEvent(-cost));
            }
        };
        dialog.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (x < 0 || x > dialog.getWidth() || y < 0 || y > dialog.getHeight()) {
                    dialog.hide();
                }
                return true;
            }
        });
        dialog.text("Select Building:");
        for (int i : buildings.keySet()) {
            if (i == 0) continue;
            dialog.button(buildings.get(i).name, i);
        }
        dialog.setPosition(TilE_SIZE * tileX, TilE_SIZE * tileY);
        dialog.show(this);
    }

    @Override
    public BuildingTile getTile(int tileX, int tileY) {
        for (BuildingTile tile : tileList) {
            if (tile.getTileX() == tileX && tile.getTileY() == tileY)
                return tile;
        }
        return null;
    }

    @Override
    public void addProjectile(int damage, float x, float y, int targetRefID, boolean targetsEnemy) {
        GameActorView target;
        if (targetsEnemy) {
            target = enemies.get(targetRefID);
        } else target = buildings.get(targetRefID);

        TextureRegion texture = new TextureRegion(assets.getTexture("sprites/projectile.png"));
        addActor(new Projectile(texture, x, y, TilE_SIZE/5, TilE_SIZE/5, damage, target, targetRefID, targetsEnemy));
    }

    //да, это костыль, но вы сами не хотели обобщать врагов и башен
    private void syncActors(Map<Integer, GameActorView> sprites, Map<Integer, GameActor> actors, boolean isEnemy) {
        for (int refID : actors.keySet()) {
            GameActor actor = actors.get(refID);

            if (sprites.containsKey(refID)) { //update existing actor accordingly
                sprites.get(refID).setPosition(actor.getPosition().x, actor.getPosition().y);
                sprites.get(refID).setHealthBarPercentage((float) actor.getHealth() / (float) actor.getHealthMax());
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
            GameActorView actor = sprites.get(refID);
            actor.remove();
            sprites.remove(refID);
        }
    }

    private void addEnemySprite(GameActor actor, int refID) {
        String textureName = creator.getEnemyConfig(actor.getID()).SpriteName;
        TextureRegion texture = new TextureRegion(assets.getEnemyTexture(textureName));
        enemies.put(refID, new GameActorView(texture, assets.getSkin(), actor.getPosition().x, actor.getPosition().y, TilE_SIZE * 0.5f, TilE_SIZE * 0.5f));
        addActor(enemies.get(refID));
    }

    private void addBuildingSprite(GameActor actor, int refID) {
        String textureName = creator.getBuildingConfig(actor.getID()).SpriteName;
        TextureRegion texture = new TextureRegion(assets.getBuildingTexture(textureName));
        buildings.put(refID, new GameActorView(texture, assets.getSkin(), actor.getPosition().x, actor.getPosition().y, TilE_SIZE * 0.8f, TilE_SIZE * 0.8f));
        addActor(buildings.get(refID));
    }
}
