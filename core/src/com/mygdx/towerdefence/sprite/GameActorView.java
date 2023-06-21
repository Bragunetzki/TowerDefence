package com.mygdx.towerdefence.sprite;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.mygdx.towerdefence.framework.LevelView;

public class GameActorView extends Group {
    private final TextureRegion texture;
    ProgressBar healthBar;


    public GameActorView(TextureRegion texture, Skin skin, float x, float y, float width, float height) {
        this.texture = texture;
        setSize(width, height);
        //setOrigin(width/2, height/2);
        setPosition(x, y);
        healthBar = new ProgressBar(0, 100, 1, false, skin, "default-horizontal");
        healthBar.setSize(width, width / 4);
        healthBar.getStyle().background.setMinHeight(10);
        healthBar.getStyle().knobBefore.setMinHeight(10);
        addActor(healthBar);
        healthBar.setPosition(-getWidth()/2 + LevelView.TilE_SIZE/2, getHeight()* 1.2f);
        healthBar.setAnimateDuration(100);
        healthBar.setValue(100);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.setColor(getColor());
        if (texture != null) {
            batch.draw(texture, getX() - getWidth()/2 + LevelView.TilE_SIZE/2, getY() + LevelView.TilE_SIZE/2 - getHeight()/2, getWidth() / 2, getHeight() / 2, getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
        }
        healthBar.setColor(Color.RED);
        super.draw(batch, parentAlpha);
        batch.setColor(Color.WHITE);
    }

    @Override
    public void act(float delta) {
        healthBar.updateVisualValue();
        super.act(delta);
    }

    public void setHealthBarPercentage(float value) {
        healthBar.setValue(value);
        if (healthBar.getValue() < 0) healthBar.setValue(0);
    }
}
