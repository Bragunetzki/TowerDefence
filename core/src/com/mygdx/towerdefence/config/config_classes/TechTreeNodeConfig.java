package com.mygdx.towerdefence.config.config_classes;
import java.util.List;

public class TechTreeNodeConfig {
    public int id;
    public int cost;
    public List<UpgradeConfig> upgrades;
    public List<Integer> parentIds;
}
