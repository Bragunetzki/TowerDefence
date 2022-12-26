package com.mygdx.towerdefence.sprite;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class GameActorView extends Group {
    private final TextureRegion texture;
    ProgressBar healthBar;


    public GameActorView(TextureRegion texture, Skin skin, float x, float y, float width, float height) {
        this.texture = texture;
        setSize(width, height);
        setOrigin(width/2, height/2);
        setPosition(x, y);
        healthBar = new ProgressBar(0, 100, 1, false, skin, "default-horizontal");
        healthBar.setSize(width, width / 5);
        healthBar.setPosition(x - width / 2, y + width / 2);
        healthBar.setAnimateDuration(100);
        healthBar.setColor(Color.RED);
        addActor(healthBar);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (texture != null) {
            batch.draw(texture, getX(), getY(), getWidth() / 2, getHeight() / 2, getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
        }
        super.draw(batch, parentAlpha);
    }

    @Override
    public void act(float delta) {
        healthBar.updateVisualValue();
        super.act(delta);
    }

    public void setHealthBarPercentage(float value) {
        healthBar.setValue(value);
    }
}
