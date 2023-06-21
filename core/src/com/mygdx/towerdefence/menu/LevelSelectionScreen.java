package com.mygdx.towerdefence.menu;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.mygdx.towerdefence.TowerDefenceGame;
import com.mygdx.towerdefence.framework.screens.BasicScreen;
import com.mygdx.towerdefence.framework.screens.LevelScreen;


public class LevelSelectionScreen extends BasicScreen {
    private final TowerDefenceGame game;

    private final Stage stage;
    private final Table table;
    private Button goBackButton;

    public LevelSelectionScreen(TowerDefenceGame game) {
        super(game);
        this.game = game;
        stage = new Stage(getViewport());
        table = new Table();
        table.setBounds(0, 0, BasicScreen.WORLD_SIZE_X, BasicScreen.WORLD_SIZE_Y);
        stage.addActor(table);

        addGoBackButton();
        addLevelsList(game.getCreator().getLevelConfigMap().size());

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

    private void addLevelsList(int numberOfLevels) {
        Label label = new Label("Select a level:",
                new Label.LabelStyle(game.getAssetLoader().getFont(), Color.GRAY));
        label.setFontScale(2);
        table.add(label).pad(80).padTop(80);
        table.row();

        Table innerTable = new Table();

        for (int i = 0; i < numberOfLevels; i++) {
            TextButton button = new LevelButton(game, this, i);
            innerTable.add(button).pad(20).height(200);
        }

        ScrollPane scrollPane = new ScrollPane(innerTable);

        table.add(scrollPane).expandY().fillY();
    }

    public void startLevel(int levelID) {
        game.setScreen(new LevelScreen(game, levelID));
    }

}
