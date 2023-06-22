package com.mygdx.towerdefence.game_state;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mygdx.towerdefence.TowerDefenceGame;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class GameState {
    private transient final GameStateCreator creator;
    private final File saveFile;

    private int inGameCurrency;
    private int levelsPassed;
    private List<Integer> purchasedUpgrades;

    public GameState(TowerDefenceGame game) {
        creator = (GameStateCreator) game.getCreator();
        saveFile = new File("save_file.json");

        if (saveFile.exists()) {
            load();
        } else {
            inGameCurrency = 200;
            levelsPassed = 0;
            purchasedUpgrades = new ArrayList<>();
        }
    }

    public int getInGameCurrency() {
        return inGameCurrency;
    }

    public void alterInGameCurrency(int currency) {
        this.inGameCurrency += currency;
        save();
    }

    public int getLevelsPassed() {
        return levelsPassed;
    }

    public void setLevelsPassed(int levelsPassed) {
        this.levelsPassed = levelsPassed;
        save();
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
        save();
    }

    private void load() {
        Gson gson = new Gson();
        try {
            Reader reader = new FileReader(saveFile);
            Type type = new TypeToken<GameStateConfig>() {
            }.getType();
            GameStateConfig config = gson.fromJson(reader, type);
            this.inGameCurrency = config.inGameCurrency;
            this.levelsPassed = config.levelsPassed;
            this.purchasedUpgrades = config.purchasedUpgrades;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void save() {
        Gson gson = new Gson();
        Writer writer;
        try {
            writer = new FileWriter(saveFile, false);
            gson.toJson(new GameStateConfig(inGameCurrency, levelsPassed, purchasedUpgrades), writer);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            System.out.println("Exception thrown while writing to save file.");
            e.printStackTrace();
        }

    }
}
