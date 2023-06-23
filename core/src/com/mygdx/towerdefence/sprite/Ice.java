package com.mygdx.towerdefence.sprite;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Ice extends GameSprite {
    private float duration;

    public Ice(TextureRegion texture, float x, float y, float width, float height, float duration) {
        super(texture, x, y, width, height);
        this.duration = duration;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (duration == 0) {
            this.remove();
        } else {
            duration -= delta;
            if (duration < 0) {
                duration = 0;
            }
        }
    }

}
