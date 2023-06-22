package com.mygdx.towerdefence.client.clientCommands;

public class UpgradeBuildingClientCommand implements ClientCommand {
    private final int refID, upgradeNum;
    public UpgradeBuildingClientCommand(int refID, int upgradeNum) {
        this.refID = refID;
        this.upgradeNum = upgradeNum;
    }


    @Override
    public String getString() {
        return "{\"cmd\":upgradeBuilding, \"refID\":" + refID + ", \"upgradeID\": " + upgradeNum + "}\n";
    }
}
