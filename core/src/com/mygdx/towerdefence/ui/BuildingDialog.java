package com.mygdx.towerdefence.ui;

import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Align;

public class BuildingDialog extends Dialog {
    private final float dialog_width = 200;
    private final float dialog_height = 100;
    private final float dialog_padding = 20;
    private final float button_height = 40;
    private final float button_width = 80;
    private final float button_pad_h = 15;


    public BuildingDialog(String title, Skin skin) {
        super(title, skin);
        setup();
    }

    private void setup() {
        padLeft(dialog_padding);
        padRight(dialog_padding);
        padBottom(dialog_padding);
        getButtonTable().defaults().height(button_height);
        getContentTable().defaults().width(dialog_width);

        setModal(true);
        setMovable(false);
        setResizable(false);
    }

    @Override
    public Dialog text(String text) {
        Label label = new Label(text, getSkin());
        label.setAlignment(Align.center);
        label.setWrap(true);
        label.setWidth(dialog_width-dialog_padding*2);
        text(label);
        return this;
    }
}
