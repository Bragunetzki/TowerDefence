package com.mygdx.towerdefence.menu;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.mygdx.towerdefence.TowerDefenceGame;
import com.mygdx.towerdefence.game_state.GameState;

public class LevelButton extends TextButton {

    private final TowerDefenceGame game;
    public final LevelSelectionScreen levelSelectionScreen;
    public final int levelID;

    public LevelButton(TowerDefenceGame game, LevelSelectionScreen levelSelectionScreen, int levelID) {
        super("Level " + levelID, game.getAssetLoader().getSkin());
        this.game = game;
        this.levelSelectionScreen = levelSelectionScreen;
        this.levelID = levelID;
        addListener(new LevelButtonListener(this));

        GameState gameState = game.getGameState();
        if (gameState.getLevelsPassed() < levelID) {
            this.setColor(Color.GRAY);
            this.getChildren().get(0).setColor(Color.GRAY);
        }
    }

    public void startLevel() {
        GameState gameState = game.getGameState();
        if (gameState.getLevelsPassed() < levelID) {
            showLockedDialog();
        } else {
            levelSelectionScreen.startLevel(levelID);
        }
    }

    private void showLockedDialog() {
        final Dialog dialog = new Dialog("", game.getAssetLoader().getSkin()) {
            @Override
            protected void result(Object object) {
                this.hide(null);
            }
        };
        dialog.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (x < 0 || x > dialog.getWidth() || y < 0 || y > dialog.getHeight()) {
                    dialog.hide(null);
                }
                return true;
            }
        });

        dialog.text("This level is locked.\nWin previous level to unlock this.",
                new Label.LabelStyle(game.getAssetLoader().getFont(), Color.WHITE));
        dialog.button("Cancel", false);
        dialog.show(this.getStage(), null);
        dialog.setPosition((this.getStage().getWidth() - dialog.getWidth()) / 2,
                (this.getStage().getHeight() - dialog.getHeight()) / 2);
    }


}
