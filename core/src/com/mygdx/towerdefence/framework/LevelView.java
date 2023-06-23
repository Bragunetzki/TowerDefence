package com.mygdx.towerdefence.framework;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.mygdx.towerdefence.TowerDefenceGame;
import com.mygdx.towerdefence.client.Client;
import com.mygdx.towerdefence.config.Creator;
import com.mygdx.towerdefence.config.config_classes.BuildingConfig;
import com.mygdx.towerdefence.config.config_classes.BuildingUpgradeConfig;
import com.mygdx.towerdefence.config.config_classes.LevelConfig;
import com.mygdx.towerdefence.config.config_classes.UpgradeConfig;
import com.mygdx.towerdefence.events.eventClasses.StateHolder;
import com.mygdx.towerdefence.events.ViewHolder;
import com.mygdx.towerdefence.framework.screens.BasicScreen;
import com.mygdx.towerdefence.framework.screens.LevelScreen;
import com.mygdx.towerdefence.gameactor.Building;
import com.mygdx.towerdefence.gameactor.GameActor;
import com.mygdx.towerdefence.inputListeners.BuildingListener;
import com.mygdx.towerdefence.level.Tile;
import com.mygdx.towerdefence.menu.LevelSelectionScreen;
import com.mygdx.towerdefence.menu.MainMenuScreen;
import com.mygdx.towerdefence.sprite.BuildingTileSprite;
import com.mygdx.towerdefence.sprite.GameActorView;
import com.mygdx.towerdefence.sprite.Ice;
import com.mygdx.towerdefence.sprite.Projectile;

import java.util.*;

public class LevelView extends Stage implements ViewHolder {
    public static final float WORLD_SIZE_X = 1920;
    public static final float WORLD_SIZE_Y = 1080;
    public static final float GRID_CORNER_X = 460;
    public static final float GRID_CORNER_Y = 40;
    public final static float TilE_SIZE = 100;

    private final Map<Integer, GameActorView> enemies;
    private final Map<Integer, GameActorView> buildings;
    private final AssetLoader assets;
    private final Creator creator;
    private final Texture[][] mapTextures;
    private final Tile[][] map;
    private final List<BuildingTileSprite> tileList;
    private final Label currencyLabel;
    private final Label timerLabel;
    private final TowerDefenceGame game;
    private final int levelID;
    private final Client client;
    private boolean trackTimer;

    public LevelView(BasicScreen screen, TowerDefenceGame game, int levelID, Tile[][] map, Client client) {
        super(screen.getViewport());
        this.levelID = levelID;
        enemies = new HashMap<>();
        buildings = new HashMap<>();
        this.game = game;
        assets = game.getAssetLoader();
        creator = game.getCreator();
        LevelConfig levelConfig = creator.getLevelConfig(levelID);
        tileList = new LinkedList<>();
        currencyLabel = new Label("Currency: ", assets.getSkin());
        currencyLabel.setPosition(50, 30);
        timerLabel = new Label("Timer: ", assets.getSkin());
        timerLabel.setPosition(50, WORLD_SIZE_Y * 0.95f);
        addActor(currencyLabel);
        addActor(timerLabel);
        this.map = map;
        this.client = client;
        trackTimer = true;

        Texture backgroundTexture = assets.getTexture(levelConfig.backgroundTextureName);
        Texture plotTexture = assets.getTexture(levelConfig.plotTextureName);
        Texture roadTexture = assets.getTexture(levelConfig.roadTextureName);
        Texture claimedTexture = assets.getTexture(levelConfig.claimedPlotTextureName);

        mapTextures = new Texture[map.length][map[0].length];

        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                BuildingTileSprite tile;
                switch (map[i][j].type) {
                    case Background:
                        mapTextures[i][j] = backgroundTexture;
                        break;
                    case Plot:
                        tile = new BuildingTileSprite(new TextureRegion(plotTexture), i, j, TilE_SIZE, TilE_SIZE, client.getPlayerNum() == 1);
                        tile.setPosition(map[i][j].x, map[i][j].y);
                        addActor(tile);
                        tileList.add(tile);
                        mapTextures[i][j] = null;
                        break;
                    case ClaimedPlot:
                        tile = new BuildingTileSprite(new TextureRegion(claimedTexture), i, j, TilE_SIZE, TilE_SIZE, client.getPlayerNum() == 2);
                        tile.setPosition(map[i][j].x, map[i][j].y);
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
        getBatch().begin();
        drawMap();
        getBatch().end();
        super.draw();
    }

    private void drawMap() {
        for (int i = 0; i < mapTextures.length; i++) {
            for (int j = 0; j < mapTextures[0].length; j++) {
                if (mapTextures[i][j] != null) {
                    this.getBatch().draw(mapTextures[i][j], map[i][j].x, map[i][j].y, TilE_SIZE, TilE_SIZE);
                }
            }
        }
    }

    @Override
    public void update(StateHolder state) {
        syncActors(enemies, state.getEnemies(), true);
        syncActors(buildings, state.getBuildings(), false);
        currencyLabel.setText("Currency: " + state.getCurrency());

        if (trackTimer)
            setTimerLabel(state.getWaveGenerator().getWaveTimer());
    }

    public void setTimerLabel(float time) {
        timerLabel.setText("Timer: " + (int) time);
    }

    public void setTrackTimer(boolean value) {
        this.trackTimer = value;
    }

    @Override
    public void showEndDialog(final boolean victory, final int reward) {
        final Dialog dialog = new Dialog("Game Over", assets.getSkin()) {
            @Override
            protected void result(Object object) {
                client.shutDown();
                game.getGameState().alterInGameCurrency(reward);
                LevelScreen.eventQueue.clearAll();
                game.getScreen().dispose();
                if (victory && !client.isOnline()) {
                    game.getGameState().setLevelsPassed(levelID + 1);
                }

                if ((boolean) object) {
                    if (client.isOnline())
                        game.setScreen(new MainMenuScreen(game));
                    else
                        game.setScreen(new LevelSelectionScreen(game));
                } else {
                    game.setScreen(new LevelScreen(game, levelID, false));
                }
                this.hide(null);
            }
        };
        if (victory) {
            dialog.text("You Win!\nYour reward: " + reward);
            dialog.button("Ok", true);
        } else {
            dialog.text("You Lost!");
            dialog.button("Ok", true);
            dialog.button("Try again", false);
        }
        dialog.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        dialog.show(this, null);
        dialog.setPosition(LevelScreen.WORLD_SIZE_X / 2 - dialog.getWidth() / 2, LevelScreen.WORLD_SIZE_Y / 2 - dialog.getHeight());
    }

    @Override
    public void showConstructionDialog(final int tileX, final int tileY) {
        final Map<Integer, BuildingConfig> buildings = creator.getBuildingMap();
        final Dialog dialog = new Dialog("BuildingSelection", assets.getSkin()) {
            @Override
            protected void result(Object object) {
                client.constructBuilding((Integer) object, tileX, tileY);
                this.hide(null);
            }
        };
        dialog.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (x < 0 || x > dialog.getWidth() || y < 0 || y > dialog.getHeight()) {
                    dialog.hide(null);
                }
                return true;
            }
        });
        dialog.text("Select Building:");
        for (int i : buildings.keySet()) {
            if (i == 0) continue;
            dialog.button(buildings.get(i).name + ": " + buildings.get(i).cost, i);
            dialog.getButtonTable().row();
        }
        dialog.setPosition(map[tileX][tileY].x, map[tileX][tileY].y);
        dialog.show(this, null);
    }

    @Override
    public void showBuildingDialog(final int refID, final int ID) {
        final List<BuildingUpgradeConfig> upgrades = creator.getBuildingConfig(ID).upgrades;
        final Dialog dialog = new Dialog("BuildingMenu", assets.getSkin()) {
            @Override
            protected void result(Object object) {
                if ((Integer) object == 0) {
                    int demolitionReturn = creator.getBuildingConfig(ID).demolitionCurrency;
                    client.demolishBuilding(refID, demolitionReturn);
                } else {
                    client.upgradeBuilding(refID, (Integer) object - 1);
                }
                this.hide(null);
            }
        };
        dialog.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (x < 0 || x > dialog.getWidth() || y < 0 || y > dialog.getHeight()) {
                    dialog.hide(null);
                }
                return true;
            }
        });
        dialog.text("Choose what to do:");
        for (int i = 0; i < upgrades.size(); i++) {
            BuildingUpgradeConfig upgrade = upgrades.get(i);
            StringBuilder upgradeText = new StringBuilder();
            for (UpgradeConfig modifier : upgrade.upgrades) {
                upgradeText.append(modifier.upgradedParameter).append(" ").append(modifier.modifier).append(" ");
            }

            dialog.button(upgradeText + ": " + upgrade.cost, i + 1);
            dialog.getButtonTable().row();
        }
        dialog.button("Demolish!", 0);
        dialog.setPosition(buildings.get(refID).getX(), buildings.get(refID).getY());
        dialog.show(this, null);
    }

    @Override
    public BuildingTileSprite getTile(int tileX, int tileY) {
        for (BuildingTileSprite tile : tileList) {
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
        addActor(new Projectile(texture, x, y, TilE_SIZE / 5, TilE_SIZE / 5, damage, target, targetRefID, targetsEnemy));
    }

    @Override
    public void addIce(float duration, float x, float y) {
        TextureRegion texture = new TextureRegion(assets.getTexture("sprites/ice.png"));
        addActor(new Ice(texture, x, y, TilE_SIZE, TilE_SIZE, duration));
    }


    //да, это костыль, но вы сами не хотели обобщать врагов и башен
    private void syncActors(Map<Integer, GameActorView> sprites, Map<Integer, GameActor> actors, boolean isEnemy) {
        for (int refID : actors.keySet()) {
            GameActor actor = actors.get(refID);

            if (sprites.containsKey(refID)) { //update existing actor accordingly
                sprites.get(refID).setPosition(actor.getPosition().x, actor.getPosition().y);
                sprites.get(refID).setHealthBarPercentage((float) actor.getHealth() / (float) actor.getHealthMax() * 100);
                if (!isEnemy) {
                    Building building = (Building) actor;
                    if (!building.isConstructed()) {
                        sprites.get(refID).setColor(Color.ORANGE);
                    } else {
                        sprites.get(refID).setColor(Color.WHITE);
                    }
                }
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
        String textureName = creator.getEnemyConfig(actor.getID()).spriteName;
        TextureRegion texture = new TextureRegion(assets.getEnemyTexture(textureName));
        if (actor.getID() == 2) {
            enemies.put(refID, new GameActorView(texture, assets.getSkin(), actor.getPosition().x, actor.getPosition().y, TilE_SIZE * 1.05f, TilE_SIZE * 1.05f));
        } else {
            enemies.put(refID, new GameActorView(texture, assets.getSkin(), actor.getPosition().x, actor.getPosition().y, TilE_SIZE * 0.8f, TilE_SIZE * 0.8f));
        }
        addActor(enemies.get(refID));
    }

    private void addBuildingSprite(GameActor actor, int refID) {
        String textureName = creator.getBuildingConfig(actor.getID()).spriteName;
        TextureRegion texture = new TextureRegion(assets.getBuildingTexture(textureName));
        GameActorView building = new GameActorView(texture, assets.getSkin(), actor.getPosition().x, actor.getPosition().y, TilE_SIZE * 0.95f, TilE_SIZE * 0.95f);

        int gridX = (int) ((actor.getPosition().x - LevelView.GRID_CORNER_X) / LevelView.TilE_SIZE);
        int gridY = (int) ((actor.getPosition().y - LevelView.GRID_CORNER_Y) / LevelView.TilE_SIZE);
        BuildingTileSprite tile = getTile(gridX, gridY);

        if (actor.getID() != 0) {
            if (tile.isOwned()) {
                building.addListener(new BuildingListener(refID, actor.getID()));
            }
        }
        buildings.put(refID, building);
        addActor(building);
    }
}
