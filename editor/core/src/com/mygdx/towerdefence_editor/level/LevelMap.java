package com.mygdx.towerdefence_editor.level;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class LevelMap {

    private List<LevelMapNode> pathGraph;
    private List<Integer> tileIndexes; // так ли это вообще должно выглядеть?
    private int baseNodeIndex;
    private int spawnerNodeIndex;

    private String backgroundTextureFileName;
    private String pathTextureFileName;
    private String tileTextureFileName;

    public LevelMap() {
        pathGraph = new ArrayList<>();
        tileIndexes = new ArrayList<>();
        // дефолтные база и спавнер?
    }

    public void addNode(int x, int y) {
        pathGraph.add(new LevelMapNode(pathGraph.size(), x, y));
    }

    public void connectNodes(int nodeIndex1, int nodeIndex2) {
        pathGraph.get(nodeIndex1).connectWithAnotherNode(nodeIndex2);
        pathGraph.get(nodeIndex2).connectWithAnotherNode(nodeIndex1);
    }

    public void removeNode(int nodeIndex) {
        pathGraph.remove(nodeIndex);
        // вершину нужно убрать из connections других вершин
        // вершину нужно убрать из списка тайлов, если она там
        // базу и спавнер убрать нельзя (?)
    }

    public void makeNodeTile(int nodeIndex) {
        tileIndexes.add(nodeIndex);
    }

}
