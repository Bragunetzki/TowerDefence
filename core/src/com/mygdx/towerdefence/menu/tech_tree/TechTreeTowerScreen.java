package com.mygdx.towerdefence.menu.tech_tree;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.mygdx.towerdefence.TowerDefenceGame;
import com.mygdx.towerdefence.config.config_classes.TechTreeNodeConfig;
import com.mygdx.towerdefence.framework.screens.BasicScreen;
import com.mygdx.towerdefence.menu.tech_tree.actors.TechButton;

import java.util.*;
import java.util.List;

public class TechTreeTowerScreen extends BasicScreen {

    private final TowerDefenceGame game;
    private final Integer towerId;
    private final List<TechTreeNodeConfig> towerUpgradeNodes;

    private final Stage stage;
    private Table table;
    private TextButton towerButton;
    private Button goBackButton;
    private Label currency;

    public TechTreeTowerScreen(TowerDefenceGame game, Integer towerId, List<TechTreeNodeConfig> towerUpgradeNodes) {
        super(game);
        this.game = game;
        this.towerId = towerId;
        this.towerUpgradeNodes = towerUpgradeNodes;

        table = new Table();
        table.setBounds(0, 0, BasicScreen.WORLD_SIZE_X, BasicScreen.WORLD_SIZE_Y);
        stage = new Stage(getViewport());
        drawAll();
        stage.addActor(table);
        getStageMultiplexer().addStage(stage);
    }

    @Override
    public void render(float delta) {
        if (goBackButton.isPressed() || towerButton.isPressed()) {
            game.setScreen(new TechTreeMainScreen(game));
        }
        super.render(delta);
    }

    private void addGoBackButton() {
        goBackButton = new TextButton("Back", game.getAssetLoader().getSkin());
        goBackButton.addListener(new InputListener());
        goBackButton.setPosition(60, stage.getHeight() - 100);
        stage.addActor(goBackButton);
    }

    private void addCurrency() {
        currency = new Label(((Integer) game.getGameState().getInGameCurrency()).toString(),
                new Label.LabelStyle(game.getAssetLoader().getFont(), Color.GRAY));
        currency.setFontScale(1.2f);
        currency.setPosition(160, 100);
        stage.addActor(currency);
    }

    public void update() {
        stage.clear();
        currency.remove();
        goBackButton.remove();
        table.remove();
        table = new Table();
        table.setBounds(0, 0, BasicScreen.WORLD_SIZE_X, BasicScreen.WORLD_SIZE_Y);
        drawAll();
        stage.addActor(table);
    }

    private void drawAll() {
        addGoBackButton();
        addCurrency();
        drawTree();
    }

    private void drawTree() {
        String buildingName;
        if (towerId != -1) {
            buildingName = game.getCreator().getBuildingConfig(towerId).name;
        } else {
            buildingName = "All towers";
        }
        towerButton = new TextButton(buildingName, game.getAssetLoader().getSkin());
        towerButton.setWidth(towerButton.getLabel().getWidth() * 1.3f);

        if (towerId != -1) {
            Image buildingSprite = new Image(game.getAssetLoader().getBuildingTexture(game.getCreator().getBuildingConfig(towerId).spriteName));
            buildingSprite.setScale(towerButton.getHeight() / buildingSprite.getHeight());
//            buildingSprite.setPosition(60, stage.getHeight() / 2 - towerButton.getHeight() / 2);
            table.add(buildingSprite);
        }
        table.add(towerButton).padRight(25);

        List<TechTreeNodeConfig> currentColumn = new ArrayList<>();
        for (TechTreeNodeConfig node : towerUpgradeNodes) {
            if (node.parentIds.isEmpty()) {
                currentColumn.add(node);
            }
        }
        drawColumn(currentColumn);

        while (!currentColumn.isEmpty()) {
            List<Integer> previousColumnIds = new ArrayList<>();
            for (TechTreeNodeConfig techTreeNodeConfig : currentColumn) {
                previousColumnIds.add(techTreeNodeConfig.id);
            }
            currentColumn = new ArrayList<>();
            for (TechTreeNodeConfig node : towerUpgradeNodes) {
                if (isOnThisLevel(previousColumnIds, node.parentIds)) {
                    currentColumn.add(node);
                }
            }
            drawColumn(currentColumn);
        }
    }

    private void drawColumn(List<TechTreeNodeConfig> column) {

        VerticalGroup verticalGroup = new VerticalGroup();

        for (TechTreeNodeConfig config : column) {
            TechButton button = new TechButton(game, this, config);
            verticalGroup.addActor(button);
        }
        verticalGroup.space(25);
        table.add(verticalGroup).expandY().pad(60);
    }

    private boolean isOnThisLevel(List<Integer> previousColumnIds, List<Integer> parentIds) {
        for (Integer parentId : parentIds) {
            if (previousColumnIds.contains(parentId)) {
                return true;
            }
        }
        return false;
    }

    public Integer getTowerId() {
        return towerId;
    }
}
