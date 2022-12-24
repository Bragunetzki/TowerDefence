package com.mygdx.towerdefence.gameactor;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.towerdefence.action.Action;
import com.mygdx.towerdefence.priority.Priority;

public interface GameActor {
    int getHealth();
    int getHealthMax();
    int getID();
    Priority getPriority();
    Vector2 getPosition();
    void setPosition(Vector2 position);
    String getName();
    Action getAction();
    int applyDamage(int damage);
    void act(float delta);
    void kill();
    boolean isActive();
    GameActor getTarget();
    void setTarget(GameActor target);
    ActorType getType();
}
