package com.mygdx.towerdefence.menu.tech_tree.actors;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.mygdx.towerdefence.TowerDefenceGame;
import com.mygdx.towerdefence.config.config_classes.TechTreeNodeConfig;
import com.mygdx.towerdefence.config.config_classes.TechTreeUpgradeConfig;
import com.mygdx.towerdefence.game_state.GameState;
import com.mygdx.towerdefence.menu.tech_tree.TechTreeTowerScreen;

public class TechButton extends Button {

    private final Skin skin;
    private final BitmapFont font;
    private final TechTreeTowerScreen screen;
    private final TechTreeNodeConfig config;
    private final GameState gameState;

    public TechButton(TowerDefenceGame game, TechTreeTowerScreen screen, TechTreeNodeConfig config) {
        super(game.getAssetLoader().getSkin());
        this.font = game.getAssetLoader().getFont();
        this.skin = game.getAssetLoader().getSkin();
        this.screen = screen;
        this.config = config;
        this.addListener(new TechButtonListener(this));
        gameState = game.getGameState();

        String text = "";
        for (TechTreeUpgradeConfig upgrade : config.upgrades) {
            text = text.concat(upgrade.upgradedParameter + " x " + upgrade.modifier + "\n");
        }
        text = text.trim();
        this.add(new Label(text, new Label.LabelStyle(font, Color.WHITE)));

        if (!gameState.isUpgradeUnlocked(config.parentIds)) {
            this.setColor(Color.GRAY);
            this.getChildren().get(0).setColor(Color.GRAY);
        }

        if (gameState.isUpgradePurchased(config.id)) {
            this.setColor(Color.OLIVE);
//            this.getChildren().get(0).setColor(Color.GRAY);
        }
    }

    public TechTreeNodeConfig getConfig() {
        return config;
    }

    protected void showDialog() {
        if (gameState.isUpgradePurchased(config.id)) {
            showLockedDialog("You have already purchased this upgrade.");
        } else if (gameState.getInGameCurrency() < config.cost) {
            showLockedDialog("You don't have enough money to purchase this upgrade yet.");
        } else if (gameState.isUpgradeUnlocked(config.parentIds)) {
            showBuyingDialog();
        } else {
            showLockedDialog("This upgrade is locked.\nBuy previous upgrades to unlock this.");
        }
    }

    private void showBuyingDialog() {
        final Dialog dialog = new Dialog("", skin) {
            @Override
            protected void result(Object object) {
                if ((boolean) object) {
                    int cost = config.cost;
                    gameState.alterInGameCurrency(-cost);
                    gameState.purchaseUpgrade(screen.getTowerId(), config.id);
                    screen.update();
                }
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

        String text = "";
        for (TechTreeUpgradeConfig upgrade : config.upgrades) {
            text = text.concat(upgrade.upgradedParameter + " x " + upgrade.modifier + "\n");
        }
        text = text.concat("Cost: " + config.cost);
        dialog.text(text, new Label.LabelStyle(font, Color.WHITE));
        dialog.button("Buy", true);
        dialog.button("Cancel", false);
        dialog.show(this.getStage(), null);
        dialog.setPosition((this.getStage().getWidth() - dialog.getWidth()) / 2,
                (this.getStage().getHeight() - dialog.getHeight()) / 2);

    }

    private void showLockedDialog(String text) {

        final Dialog dialog = new Dialog("", skin) {
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

        dialog.text(text, new Label.LabelStyle(font, Color.WHITE));
        dialog.button("Cancel", false);
        dialog.show(this.getStage(), null);
        dialog.setPosition((this.getStage().getWidth() - dialog.getWidth()) / 2,
                (this.getStage().getHeight() - dialog.getHeight()) / 2);
    }
}
