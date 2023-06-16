package com.mygdx.towerdefence.sprite;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;

public class GameSprite extends Group {
    private final TextureRegion texture;

    public GameSprite() {
        this.texture = null;
    }

    public GameSprite(TextureRegion texture, float x, float y, float width, float height) {
        this.texture = texture;
        setSize(width, height);
        setOrigin(width / 2, height / 2);
        setPosition(x, y);
        setBounds(x, y, width, height);
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
        super.act(delta);
        setBounds(getX(), getY(), getWidth(), getHeight());
    }
}
