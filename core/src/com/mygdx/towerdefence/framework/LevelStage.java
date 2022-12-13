package com.mygdx.towerdefence.framework;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.towerdefence.TowerDefenceGame;
import com.mygdx.towerdefence.config.Creator;
import com.mygdx.towerdefence.config.LevelConfig;
import com.mygdx.towerdefence.gameactor.GameActor;
import com.mygdx.towerdefence.level.LevelState;
import com.mygdx.towerdefence.level.PathNode;
import com.mygdx.towerdefence.screens.BasicScreen;
import com.mygdx.towerdefence.sprite.GameSprite;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LevelStage extends Stage {
    private final Map<Integer, GameSprite> enemies;
    private final Map<Integer, GameSprite> buildings;
    private final AssetLoader assets;
    private final Creator creator;
    private final Texture background;

    public LevelStage(BasicScreen screen, TowerDefenceGame game, int levelID) {
        super(screen.getViewport());
        enemies = new HashMap<>();
        buildings = new HashMap<>();
        assets = game.getAssetLoader();
        creator = game.getCreator();
        LevelConfig levelConfig = creator.getLevelConfig(levelID);

        background = assets.getTexture(levelConfig.backgroundTextureName);
        List<PathNode> buildPlots = new ArrayList<>(levelConfig.nodeGraph);

        List<PathNode> found = new ArrayList<>();
        for (PathNode node : buildPlots) {
            if (!node.isBuildable) found.add(node);
        }
        for (PathNode node : found) {
            buildPlots.remove(node);
        }
        for (PathNode plot : buildPlots) {
            TextureRegion t = new TextureRegion(assets.getBuildingTexture("plot"));
            GameSprite plotSprite = new GameSprite(t, plot.position.x, plot.position.y, t.getRegionWidth(), t.getRegionHeight());
            addActor(plotSprite);
        }
    }

    @Override
    public void draw() {
        this.getBatch().draw(background, 0, 0, BasicScreen.WORLD_SIZE_X, BasicScreen.WORLD_SIZE_Y);
        super.draw();
    }

    public void update(LevelState state) {
        syncSprites(enemies, state.activeEnemies, true);
        syncSprites(buildings, state.activeBuildings, false);
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
