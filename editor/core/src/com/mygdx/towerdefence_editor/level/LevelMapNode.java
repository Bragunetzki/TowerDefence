package com.mygdx.towerdefence_editor.level;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class LevelMapNode {

    private int id;
    private int x;
    private int y;
    private List<Integer> connections;

    public LevelMapNode(int id, int x, int y) {
        this.id = id;
        this.x = x;
        this.y = y;
        connections = new ArrayList<>();
    }

    public void connectWithAnotherNode(int nodeId) {
        connections.add(nodeId);
    }
}
