package com.mygdx.towerdefence_editor.level;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class LevelList {

    private final List<Level> levels;

    private int selectedElementIndex;

    public LevelList() {
        levels = new ArrayList<>();
        selectedElementIndex = -1;
    }

    public void addNewLevel(int index) {
        levels.add(index, new Level());
        selectedElementIndex = index;
    }

    public Level getSelectedLevel() {
        return levels.get(selectedElementIndex);
    }

    public void deleteSelectedLevel() { //???
            levels.remove(selectedElementIndex);
            selectedElementIndex = -1;
    }
}
