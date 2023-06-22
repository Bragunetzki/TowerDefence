package com.mygdx.towerdefence.sprite;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.towerdefence.events.DamageActorEvent;
import com.mygdx.towerdefence.framework.screens.LevelScreen;

public class Projectile extends GameSprite {
    private final int damage;
    private final GameActorView target;
    private final int targetRefID;
    private final boolean targetsEnemy;

    public Projectile(TextureRegion texture, float x, float y, float width, float height, int damage, GameActorView target, int refID, boolean targetsEnemy) {
        super(texture, x, y, width, height);
        this.damage = damage;
        this.target = target;
        this.targetRefID = refID;
        this.targetsEnemy = targetsEnemy;
    }

    @Override
    public void act(float delta) {
        if (collidesWithTarget()) {
            LevelScreen.eventQueue.addStateEvent(new DamageActorEvent(damage, targetRefID, targetsEnemy));
            this.remove();
        }
        if (isOutOfBounds()) {
            this.remove();
        }
        Vector2 targetPos = new Vector2(target.getX(), target.getY());
        Vector2 projPos = new Vector2(getX(), getY());
        Vector2 direction = targetPos.sub(projPos);
        projPos.add(direction.nor().scl(1000).scl(delta));
        setPosition(projPos.x, projPos.y);
        super.act(delta);
    }

    private boolean collidesWithTarget() {
        if (target == null) return false;
        boolean a = (getX() >= target.getX() - target.getWidth() / 2);
        boolean c = (getY() >= target.getY() - target.getHeight() / 2);
        boolean b = (getX() <= target.getX() + target.getWidth() / 2);
        boolean d = (getY() <= target.getY() + target.getHeight() / 2);
        return a && b && c && d;
    }

    private boolean isOutOfBounds() {
        return getX() > LevelScreen.WORLD_SIZE_X || getX() < 0 || getY() > LevelScreen.WORLD_SIZE_Y || getY() < 0;
    }
}
