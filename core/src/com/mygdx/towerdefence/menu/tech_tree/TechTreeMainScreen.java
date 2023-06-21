package com.mygdx.towerdefence.menu.tech_tree;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.mygdx.towerdefence.TowerDefenceGame;
import com.mygdx.towerdefence.config.config_classes.TechTreeConfig;
import com.mygdx.towerdefence.config.config_classes.TechTreeNodeConfig;
import com.mygdx.towerdefence.framework.screens.BasicScreen;
import com.mygdx.towerdefence.menu.MainMenuScreen;
import com.mygdx.towerdefence.menu.tech_tree.actors.GraphLine;
import com.mygdx.towerdefence.menu.tech_tree.actors.TowerButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TechTreeMainScreen extends BasicScreen {
    private final TowerDefenceGame game;

    private final TechTreeConfig techTree;

    private final Stage stage;
    private Button goBackButton;

    public TechTreeMainScreen(TowerDefenceGame game) {
        super(game);
        this.game = game;
        stage = new Stage(getViewport());

        addGoBackButton();
        addMainLabel();
        addCurrency();

        techTree = game.getCreator().getTechTree();
        drawMainTree();

        getStageMultiplexer().addStage(stage);
    }

    @Override
    public void render(float delta) {
        if (goBackButton.isPressed()) {
            game.setScreen(new MainMenuScreen(game));
        }
        super.render(delta);
    }

    private void addGoBackButton() {
        goBackButton = new TextButton("Back to main menu", game.getAssetLoader().getSkin());
        goBackButton.addListener(new InputListener());
        goBackButton.setPosition(60, stage.getHeight() - 100);
        stage.addActor(goBackButton);
    }

    private void addMainLabel() {
        Label label = new Label("Technology Tree",
                new Label.LabelStyle(game.getAssetLoader().getFont(), Color.GRAY));
        label.setFontScale(2);
        label.setPosition((stage.getWidth()) / 2 - label.getWidth(), stage.getHeight() - 90);
        stage.addActor(label);
    }

    private void addCurrency() {
        Label currency = new Label(((Integer) game.getGameState().getInGameCurrency()).toString(),
                new Label.LabelStyle(game.getAssetLoader().getFont(), Color.GRAY));
        currency.setFontScale(1.2f);
        currency.setPosition(160, 100);
        stage.addActor(currency);
    }

    private void drawMainTree() {
        Button centralNode = new ImageButton(game.getAssetLoader().getSkin());
        centralNode.setSize(65, 65);
        centralNode.setPosition(stage.getWidth() / 2, stage.getHeight() / 2 - 100);

        List<Actor> nodes = new ArrayList<>();
        Map<Integer, List<TechTreeNodeConfig>> rootNodes = techTree.rootNodes;
        if (rootNodes.containsKey(-1)) {
            TowerButton button = new TowerButton("All Towers", game.getAssetLoader().getSkin(), this, -1);
            button.setWidth(button.getLabel().getWidth() * 1.3f);
            nodes.add(button);
        }

        for (Integer towerId : rootNodes.keySet()) {
            if (towerId == -1) continue;
            String buildingName = game.getCreator().getBuildingConfig(towerId).name;
            TowerButton towerButton = new TowerButton(buildingName, game.getAssetLoader().getSkin(), this, towerId);
            towerButton.setWidth(towerButton.getLabel().getWidth() * 1.3f);
            nodes.add(towerButton);
        }

        layout(centralNode, nodes);

        GraphLine graphLine = new GraphLine(game.getAssetLoader().getTexture("line.png"));
        for (Actor node : nodes) {
            stage.addActor(graphLine.getLineToCenter(centralNode, node));
        }
        for (Actor node : nodes) {
            stage.addActor(node);
        }
        stage.addActor(centralNode);
    }

    private void layout(Actor center, List<Actor> nodes) {
        double pi = Math.PI * 2;
        float centerX = center.getX() + (center.getWidth() / 2);
        float centerY = center.getY() + (center.getHeight() / 2);

        for (int i = 0; i < nodes.size(); i++) {
            Actor node = nodes.get(i);
            float distance = 400 * (float) Math.log10(nodes.size());
            double theta = (pi / 4) + (pi / nodes.size()) * i;
            node.setPosition((float) (centerX + distance * 1.8 * Math.cos(theta) - (node.getWidth() / 2)),
                    (float) (centerY + distance * Math.sin(theta) - (node.getHeight() / 2)));
        }
    }

    public void goToTowerScreen(int towerId) {
        game.setScreen(new TechTreeTowerScreen(game, towerId, techTree.rootNodes.get(towerId)));
    }

}
