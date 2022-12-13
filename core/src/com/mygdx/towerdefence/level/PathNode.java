package com.mygdx.towerdefence.level;

import com.badlogic.gdx.math.Vector2;

import java.util.List;

public class PathNode {
    public Vector2 position;
    public List<PathNode> connections;
    public boolean isBuildable;
}
