package com.mygdx.towerdefence.client.clientCommands;

public class DemolishBuildingClientCommand implements ClientCommand {
    private final int refID;

    public DemolishBuildingClientCommand(int refID) {
        this.refID = refID;
    }
    @Override
    public String getString() {
        return "{\"cmd\":\"demolishBuilding\", \"refID\":" + refID + "}\n";
    }
}
