package com.mygdx.towerdefence.framework.screens;

import com.mygdx.towerdefence.TowerDefenceGame;
import com.mygdx.towerdefence.client.Client;
import com.mygdx.towerdefence.events.EventQueue;
import com.mygdx.towerdefence.framework.LevelView;
import com.mygdx.towerdefence.level.LevelController;

public class LevelScreen extends BasicScreen {
    private final LevelController controller;
    private final LevelView levelView;

    public static final EventQueue eventQueue = new EventQueue();
    Client client;

    public LevelScreen(TowerDefenceGame game, int levelID, boolean isOnline) {
        super(game);
        this.client = new Client("10.244.176.152", 5555);
        if (isOnline) {
            client.start();
        }
        else {
            client.setPlayerNum(1);
        }

        while (client.getPlayerNum() == -1) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        controller = new LevelController(game.getCreator(), levelID, isOnline);
        levelView = new LevelView(this, game, levelID, controller.getLevelState().getMap().mapArr, client);
        if (isOnline)
            levelView.setTrackTimer(false);
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
