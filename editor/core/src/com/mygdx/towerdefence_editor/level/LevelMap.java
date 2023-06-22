package com.mygdx.towerdefence_editor.level;

import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Getter
@Setter
public class LevelMap {

    private Map<Integer, LevelMapNode> nodes;
    private List<Integer[]> nodeConnections; // список ребер

    private int baseNodeID;
    private int spawnerNodeID;

    private String backgroundTextureFileName;
    private String pathTextureFileName;
    private String tileTextureFileName;

    public LevelMap() {
        nodes = new HashMap<>();
        nodeConnections = new ArrayList<>();
        // дефолтные база и спавнер?
    }

    public void addNode(int x, int y) {
        nodes.put(nodes.size(), new LevelMapNode(x, y));
    }

    public void connectNodes(int nodeID1, int nodeID2) {
        nodeConnections.add(new Integer[]{nodeID1, nodeID2});
    }

    public void removeNode(int nodeID) {
        nodes.remove(nodeID);
        Iterator<Integer[]> iterator = nodeConnections.iterator();
        while (iterator.hasNext()) {
            Integer[] connection = iterator.next();
            if (connection[0] == nodeID || connection[1] == nodeID) {
                iterator.remove();
            }
        }
        // базу и спавнер убрать нельзя (?)

        nodes.put(nodeID, nodes.get(nodes.size() - 1));
        // ставит на место убранного элемента последний элемент в коллекции

        for (Integer[] connection : nodeConnections) {
            if (connection[0] == nodes.size() - 1) {
                connection[0] = nodeID;
            }
            if (connection[1] == nodes.size() - 1) {
                connection[1] = nodeID;
            }
        }
        // обновляет связи в соответствии с новым номером элемента
    }

    public void makeNodeTile(int nodeID) {
        nodes.get(nodeID).setTile(true);
    }

}
