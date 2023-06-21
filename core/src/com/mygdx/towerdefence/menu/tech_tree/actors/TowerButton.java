package com.mygdx.towerdefence.menu.tech_tree.actors;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.mygdx.towerdefence.menu.tech_tree.TechTreeMainScreen;

public class TowerButton extends TextButton {

    public final TechTreeMainScreen techTreeMainScreen;
    public final int towerId;

    public TowerButton(String text, Skin skin, TechTreeMainScreen techTreeMainScreen, int towerId) {
        super(text, skin);
        this.techTreeMainScreen = techTreeMainScreen;
        this.towerId = towerId;
        addListener(new TowerButtonListener(this));
    }
}
