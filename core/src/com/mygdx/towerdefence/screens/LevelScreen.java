package com.mygdx.towerdefence.screens;

import com.mygdx.towerdefence.TowerDefenceGame;
import com.mygdx.towerdefence.level.LevelController;

public class LevelScreen extends GenericScreen {
    private final LevelController controller;

    public LevelScreen(TowerDefenceGame game, int levelID) {
        super(game);
        //Сейчас игра не будет работать, т.к. Creator возвращает лишь нули.
        controller = new LevelController(game.getCreator(),  0);
    }

    @Override
    public void render(float delta) {
        controller.update(delta);
        super.render(delta);
    }
}
