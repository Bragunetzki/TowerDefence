package com.mygdx.towerdefence.framework.screens;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.mygdx.towerdefence.TowerDefenceGame;
import com.mygdx.towerdefence.menu.MainMenuScreen;

public class LoadingScreen extends BasicScreen {
    private final ProgressBar bar;

    public LoadingScreen(TowerDefenceGame game) {
        super(game);
        bar = new ProgressBar(0, 100, 1, false, game.getAssetLoader().getSkin(), "default-horizontal");
        bar.setSize(BasicScreen.WORLD_SIZE_X / 3, 20);
        bar.setPosition((BasicScreen.WORLD_SIZE_X - bar.getWidth()) / 2, (BasicScreen.WORLD_SIZE_Y - bar.getHeight()) / 2);
        bar.setAnimateDuration(0);
        Stage stage = new Stage(getViewport());
        stage.addActor(bar);
        bar.setValue(0);
        getStageMultiplexer().addStage(stage);
    }

    @Override
    public void render(float delta) {
        if (game.getAssetLoader().isLoaded()) {
            //game.setScreen(new LevelScreen(game, 0));
            game.setScreen(new MainMenuScreen(game));
        } else {
            super.render(delta);
            bar.setValue(game.getAssetLoader().progress() * 100);
            bar.updateVisualValue();
        }
    }
}
