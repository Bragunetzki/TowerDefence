package com.mygdx.towerdefence.client.clientCommands;

public class ConstructBuildingClientCommand implements ClientCommand {
    private final int id, gridX, gridY;
    public ConstructBuildingClientCommand(int id, int gridX, int gridY) {
        this.id = id;
        this.gridX = gridX;
        this.gridY = gridY;
    }
    @Override
    public String getString() {
        return "{\"cmd\":\"constructBuilding\", \"id\":" + id+", \"gridX\":" + gridX + ", \"gridY\":" + gridY + "}\n";
    }
}
