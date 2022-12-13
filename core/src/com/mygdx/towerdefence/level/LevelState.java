package com.mygdx.towerdefence.level;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.towerdefence.config.LevelConfig;
import com.mygdx.towerdefence.gameactor.Building;
import com.mygdx.towerdefence.gameactor.GameActor;

import java.util.*;

public class LevelState {
    private static class SearchNode {
        public SearchNode(PathNode node, SearchNode parent, float priority) {
            this.node = node;
            this.parent = parent;
            this.priority = priority;
        }

        PathNode node;
        SearchNode parent;
        float priority;
    }

    public Map<Integer, GameActor> activeBuildings;
    public Map<Integer, GameActor> activeEnemies;
    public List<PathNode> nodeGraph;
    public int inLevelCurrency;

    public LevelState(LevelConfig config) {
        inLevelCurrency = config.startingCurrency;
        activeBuildings = new HashMap<>();
        activeEnemies = new HashMap<>();
        nodeGraph = config.nodeGraph;
    }

    public PathNode[] getPath(PathNode startNode, PathNode targetNode) {
        Queue<SearchNode> frontier = new PriorityQueue<>(10, new Comparator<SearchNode>() {
            @Override
            public int compare(SearchNode o1, SearchNode o2) {
                return (int) (o1.priority - o2.priority);
            }
        });
        frontier.add(new SearchNode(startNode, null, 0));
        HashMap<PathNode, Float> costSoFar = new HashMap<>();

        while (!frontier.isEmpty()) {
            SearchNode q = frontier.remove();

            if (q.node == targetNode) {
                List<PathNode> path = new LinkedList<>();
                while (q.parent != null) {
                    path.add(q.node);
                    q = q.parent;
                }
                path.add(q.node);
                Collections.reverse(path);
                return path.toArray(new PathNode[0]);
            }

            if (q.node.isBuildable) continue; //ignore children of building plots.

            for (PathNode next : q.node.connections) {
                float newCost = costSoFar.get(q.node) + next.position.dst(q.node.position);
                if (!costSoFar.containsKey(next) || newCost < costSoFar.get(next)) {
                    costSoFar.put(next, newCost);
                    float priority = newCost + next.position.dst(targetNode.position);
                    frontier.add(new SearchNode(next, q, priority));
                }
            }
        }

        return null;
    }
}
