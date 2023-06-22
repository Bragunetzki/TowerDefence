package com.mygdx.towerdefence_editor;

import com.mygdx.towerdefence_editor.level.LevelMap;
import com.mygdx.towerdefence_editor.tower.Tower;
import com.mygdx.towerdefence_editor.tower.TowerUpgrade;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        LevelMap map = new LevelMap();
        map.addNode(0, 0);
        map.addNode(1, 1);
        map.addNode(2, 2);
        map.addNode(3,3);
        map.connectNodes(0, 1);
        map.connectNodes(0, 2);
        map.connectNodes(3, 1);
        map.connectNodes(0, 3);


        List<Integer[]> nodeConnections = map.getNodeConnections();
        for (Integer[] i : nodeConnections) {
            System.out.println(i[0] + "  " + i[1]);
        }
        System.out.println();

        map.removeNode(0);


        nodeConnections = map.getNodeConnections();
        for (Integer[] i : nodeConnections) {
            System.out.println(i[0] + "  " + i[1]);
        }
    }
}
