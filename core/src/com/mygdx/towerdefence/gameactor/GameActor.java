package com.mygdx.towerdefence.gameactor;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.towerdefence.gameactor.action.Action;
import com.mygdx.towerdefence.gameactor.priority.Priority;

public interface GameActor {
    int getHealth();
    int getHealthMax();
    int getID();
    Priority getPriority();
    Vector2 getPosition();
    void setPosition(float x, float y);
    String getName();
    Action getAction();
    int applyDamage(int damage);
    void act(float delta);
    void kill();
    boolean isActive();
    GameActor getTarget();
    void setTarget(GameActor target);
    ActorType getType();
    void setRefID(int refID);
    int getRefID();
}
