package com.mygdx.towerdefence.screens;

import com.mygdx.towerdefence.TowerDefenceGame;
import com.mygdx.towerdefence.events.EventQueue;
import com.mygdx.towerdefence.framework.LevelView;
import com.mygdx.towerdefence.level.LevelController;

public class LevelScreen extends BasicScreen {
    private final LevelController controller;
    private final LevelView levelView;

    public static final EventQueue eventQueue = new EventQueue();

    public LevelScreen(TowerDefenceGame game, int levelID) {
        super(game);
        //Сейчас игра не будет работать, т.к. Creator возвращает лишь нули.
        controller = new LevelController(game.getCreator(), levelID);
        levelView = new LevelView(this, game, levelID, controller.getLevelState().getMap().mapArr);
        eventQueue.subscribeState(controller.getLevelState());
        eventQueue.subscribeView(levelView);
        getStageMultiplexer().addStage(levelView);
    }

    @Override
    public void render(float delta) {
        controller.update(delta);
        levelView.update(controller.getLevelState());
        eventQueue.update();
        super.render(delta);
    }
}
