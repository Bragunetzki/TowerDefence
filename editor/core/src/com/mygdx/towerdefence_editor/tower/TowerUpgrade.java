package com.mygdx.towerdefence_editor.tower;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class TowerUpgrade {
    private int price;
    private Map<Tower.ActionParameter, Float> modifiers;

    public TowerUpgrade() {
    }

    public TowerUpgrade(int price, Map<Tower.ActionParameter, Float> modifiers) {
        this.price = price;
        this.modifiers = modifiers;
    }
}
