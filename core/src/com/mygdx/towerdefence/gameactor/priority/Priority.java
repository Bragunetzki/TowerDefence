package com.mygdx.towerdefence.gameactor.priority;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.towerdefence.gameactor.GameActor;

import java.util.Collection;

public interface Priority {
    GameActor chooseTarget(Vector2 position, Collection<GameActor> actors);
}
