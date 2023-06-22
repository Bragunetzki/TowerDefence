package com.mygdx.towerdefence.game_state;

import java.util.List;

public class GameStateConfig {
    public int inGameCurrency;
    public int levelsPassed;
    public List<Integer> purchasedUpgrades;

    public GameStateConfig(int inGameCurrency, int levelsPassed, List<Integer> purchasedUpgrades) {
        this.inGameCurrency = inGameCurrency;
        this.levelsPassed = levelsPassed;
        this.purchasedUpgrades = purchasedUpgrades;
    }
}
