package com.mygdx.towerdefence.screens;

import com.mygdx.towerdefence.TowerDefenceGame;
import com.mygdx.towerdefence.framework.LevelStage;
import com.mygdx.towerdefence.level.LevelController;

public class LevelScreen extends BasicScreen {
    private final LevelController controller;
    private final LevelStage levelStage;
    public static final float WORLD_SIZE_X = 1920;
    public static final float WORLD_SIZE_Y = 1080;

    public LevelScreen(TowerDefenceGame game, int levelID) {
        super(game);
        //Сейчас игра не будет работать, т.к. Creator возвращает лишь нули.
        controller = new LevelController(game.getCreator(),  levelID);
        levelStage = new LevelStage(this, game, levelID);
        getStageMultiplexer().addStage(levelStage);
    }

    @Override
    public void render(float delta) {
        controller.update(delta);
        levelStage.update(controller.getLevelState());
        super.render(delta);
    }
}
