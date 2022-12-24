package com.mygdx.towerdefence.level;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.towerdefence.framework.LevelView;
import com.mygdx.towerdefence.framework.TileType;

import java.util.*;

public class LevelMap {
    private final Tile[][] mapArr;

    public LevelMap(Tile[][] mapArr) {
        this.mapArr = mapArr;
    }

    //TODO cleanup pathfinding
    public Vector2 getPath(Vector2 startPosition, Vector2 targetPosition) {
        Queue<SearchNode> frontier = new PriorityQueue<>(10, new Comparator<SearchNode>() {
            @Override
            public int compare(SearchNode o1, SearchNode o2) {
                return (int) (o1.priority - o2.priority);
            }
        });

        Tile startNode = positionToTile(startPosition.x, startPosition.y);
        int[] startXY = positionToTileXY(startPosition.x, startPosition.y);
        Tile targetNode = positionToTile(targetPosition.x, targetPosition.y);
        int[] targetXY = positionToTileXY(targetPosition.x, targetPosition.y);

        frontier.add(new SearchNode(startNode, startXY[0], startXY[1], null, 0));
        HashMap<Tile, Float> costSoFar = new HashMap<>();

        while (!frontier.isEmpty()) {
            SearchNode q = frontier.remove();

            if (q.node == targetNode) {
                List<Vector2> path = new LinkedList<>();
                while (q.parent != null) {
                    path.add(new Vector2(q.x, q.y));
                    q = q.parent;
                }
                path.add(new Vector2(q.x, q.y));
                Collections.reverse(path);
                return path.get(0);
            }

            if (q.node.type == TileType.Plot) continue; //ignore children of building plots.

            List<Vector2> neighborVecs = findNeighborCoordinates(q.x, q.y);
            List<Tile> neighborTiles = findNeighbors(q.x, q.y);

            for (int i = 0; i < neighborTiles.size(); i++) {
                Tile next = neighborTiles.get(i);
                Vector2 nextPos = neighborVecs.get(i);
                int[] nextXY = positionToTileXY(nextPos.x, nextPos.y);
                float newCost = costSoFar.get(q.node) + nextPos.dst(tileXYtoPosition(q.x, q.y));
                if (!costSoFar.containsKey(next) || newCost < costSoFar.get(next)) {
                    costSoFar.put(next, newCost);
                    float priority = newCost + nextPos.dst(targetPosition);
                    frontier.add(new SearchNode(next, nextXY[0], nextXY[1], q, priority));
                }
            }
        }

        return null;
    }
    private Tile positionToTile(float x, float y) {
        int tileX = (int) (x / LevelView.TilE_SIZE);
        int tileY = (int) (y / LevelView.TilE_SIZE);
        return mapArr[tileX][tileY];
    }

    private int[] positionToTileXY(float x, float y) {
        int tileX = (int) (x / LevelView.TilE_SIZE);
        int tileY = (int) (y / LevelView.TilE_SIZE);
        int[] res = new int[2];
        res[0] = tileX;
        res[1] = tileY;
        return res;
    }

    private Vector2 tileXYtoPosition(int x, int y) {
        return new Vector2((float) (x + 0.5) * LevelView.TilE_SIZE, (float) (y + 0.5) * LevelView.TilE_SIZE);
    }

    private List<Tile> findNeighbors(int x, int y) {
        List<Tile> neighborTiles = new LinkedList<>();
        if (mapArr[x][y + 1].type != TileType.Background)
            neighborTiles.add(mapArr[x][y + 1]);
        if (mapArr[x][y - 1].type != TileType.Background)
            neighborTiles.add(mapArr[x][y - 1]);
        if (mapArr[x + 1][y].type != TileType.Background)
            neighborTiles.add(mapArr[x + 1][y]);
        if (mapArr[x - 1][y].type != TileType.Background)
            neighborTiles.add(mapArr[x - 1][y]);
        return neighborTiles;
    }

    private List<Vector2> findNeighborCoordinates(int x, int y) {
        List<Vector2> neighborCoords = new LinkedList<>();
        if (mapArr[x][y + 1].type != TileType.Background)
            neighborCoords.add(tileXYtoPosition(x, y + 1));
        if (mapArr[x][y - 1].type != TileType.Background)
            neighborCoords.add(tileXYtoPosition(x, y - 1));
        if (mapArr[x + 1][y].type != TileType.Background)
            neighborCoords.add(tileXYtoPosition(x + 1, y));
        if (mapArr[x - 1][y].type != TileType.Background)
            neighborCoords.add(tileXYtoPosition(x - 1, y));
        return neighborCoords;
    }

    private static class SearchNode {

        public SearchNode(Tile node, int x, int y, SearchNode parent, float priority) {
            this.node = node;
            this.parent = parent;
            this.priority = priority;
            this.x = x;
            this.y = y;
        }
        Tile node;

        SearchNode parent;
        float priority;
        int x, y;
    }
}
