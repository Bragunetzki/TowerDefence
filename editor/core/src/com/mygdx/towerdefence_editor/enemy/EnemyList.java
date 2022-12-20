package com.mygdx.towerdefence_editor.enemy;

import com.mygdx.towerdefence_editor.tower.Tower;
import com.mygdx.towerdefence_editor.tower.TowerUpgrade;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class EnemyList {

    private final List<Enemy> enemies;

    private int selectedElementIndex;
    // если -1, то никакой элемент не выбран
    // не нужно в json

    public EnemyList() {
        enemies = new ArrayList<>();
        selectedElementIndex = -1;
    }

    public void addNewEnemy() {
        enemies.add(new Enemy());
        selectedElementIndex = enemies.size() - 1;
    }

    public Enemy getSelectedEnemy() {
        return enemies.get(selectedElementIndex);
    }


    public void deleteSelectedTower() { //???
        if (selectedElementIndex != 0) {
            enemies.remove(selectedElementIndex);
            selectedElementIndex = -1;
        }
    }
}
