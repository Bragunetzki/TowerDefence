package com.mygdx.towerdefence.menu;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.mygdx.towerdefence.TowerDefenceGame;
import com.mygdx.towerdefence.framework.screens.BasicScreen;
import com.mygdx.towerdefence.framework.screens.LevelScreen;
import com.mygdx.towerdefence.menu.tech_tree.TechTreeMainScreen;

public class MainMenuScreen extends BasicScreen {
    private final Table table;
    private Button singlePlayerButton;
    private Button multiPlayerButton;
    private Button techTreeButton;

    public MainMenuScreen(TowerDefenceGame game) {
        super(game);
        Stage stage = new Stage(getViewport());
        table = new Table();
        table.setBounds(0, 0, BasicScreen.WORLD_SIZE_X, BasicScreen.WORLD_SIZE_Y);
        table.center();
        stage.addActor(table);
        addGameName();
        addSinglePlayerButton();
        addMultiPlayerButton();
        addTechTreeButton();
        getStageMultiplexer().addStage(stage);
    }

    @Override
    public void render(float delta) {
        if (singlePlayerButton.isPressed()) {
            game.setScreen(new LevelSelectionScreen(game));
        }

        if (multiPlayerButton.isPressed()) {
            game.setScreen(new LevelScreen(game, 2, true));
        }

        if (techTreeButton.isPressed()) {
            game.setScreen(new TechTreeMainScreen(game));
        }

        super.render(delta);
    }

    private void addGameName() {
        Label gameName = new Label("KABANCHIKI",
                new Label.LabelStyle(game.getAssetLoader().getTitleFont(), Color.MAGENTA));
        gameName.setFontScale(2);
        table.add(gameName).padBottom(100);
        table.row();
    }

    private void addSinglePlayerButton() {
        singlePlayerButton = new TextButton("Singleplayer", game.getAssetLoader().getSkin());
        singlePlayerButton.addListener(new InputListener());
        table.add(singlePlayerButton).pad(20);
        table.row();
    }

    private void addMultiPlayerButton() {
        multiPlayerButton = new TextButton("Multiplayer", game.getAssetLoader().getSkin());
        multiPlayerButton.addListener(new InputListener());
        table.add(multiPlayerButton).pad(20);
        table.row();
    }

    private void addTechTreeButton() {
        techTreeButton = new TextButton("Technology Tree", game.getAssetLoader().getSkin());
        techTreeButton.addListener(new InputListener());
        table.add(techTreeButton).pad(20);
    }
}
