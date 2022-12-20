package com.mygdx.towerdefence_editor.level;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LevelMapNode {

    private int x;
    private int y;
    private boolean isTile;

    public LevelMapNode(int x, int y) {
        this.x = x;
        this.y = y;
        this.isTile = false;
    }
}
