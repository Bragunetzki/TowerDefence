package com.mygdx.towerdefence.game_state;

import com.mygdx.towerdefence.TowerDefenceGame;

import java.util.ArrayList;
import java.util.List;

public class GameState {
    private final GameStateCreator creator;

    private int inGameCurrency;
    private int levelsPassed;
    private final List<Integer> purchasedUpgrades;

    public GameState(TowerDefenceGame game) {
        creator = (GameStateCreator) game.getCreator();
        inGameCurrency = 200;
        levelsPassed = 0;
        purchasedUpgrades = new ArrayList<>();
    }

    public int getInGameCurrency() {
        return inGameCurrency;
    }

    public void alterInGameCurrency(int currency) {
        this.inGameCurrency += currency;
    }

    public int getLevelsPassed() {
        return levelsPassed;
    }

    public void setLevelsPassed(int levelsPassed) {
        this.levelsPassed = levelsPassed;
    }

    public boolean isUpgradeUnlocked(List<Integer> parentIds) {
        if (parentIds.isEmpty()) {
            return true;
        }
        for (Integer parentId : parentIds) {
            if (!purchasedUpgrades.contains(parentId)) {
                return false;
            }
        }
        return true;
    }

    public boolean isUpgradePurchased(int upgradeId) {
        return purchasedUpgrades.contains(upgradeId);
    }

    public void purchaseUpgrade(int towerId, int upgradeId) {
        purchasedUpgrades.add(upgradeId);
        creator.updateBuildingConfig(towerId, upgradeId);
    }
}
