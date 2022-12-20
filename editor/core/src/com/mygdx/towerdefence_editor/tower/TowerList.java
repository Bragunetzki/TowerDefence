package com.mygdx.towerdefence_editor.tower;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class TowerList {

    private final List<Tower> towers;

    private int selectedElementIndex;
    // если -1, то никакой элемент не выбран
    // не нужно в json

    public TowerList() {
        towers = new ArrayList<>();
        towers.add(new Tower("BASE", 100, 0, "", new ArrayList<TowerUpgrade>(),
                0, Tower.ActionType.NONE, 0, 0, null));
        selectedElementIndex = -1;
    }

    public void addNewTower() {
        towers.add(new Tower());
        selectedElementIndex = towers.size() - 1;
    }

    public Tower getSelectedTower() {
        return towers.get(selectedElementIndex);
    }

    /*
    public void updateSelectedTower() {
        // getSelectedLevel передает ссылку или копию? будет ли этот объект меняться сам?
    }
     */

    public void deleteSelectedTower() { //???
        if (selectedElementIndex != 0) {
            towers.remove(selectedElementIndex);
            selectedElementIndex = -1;
        }
    }
}
