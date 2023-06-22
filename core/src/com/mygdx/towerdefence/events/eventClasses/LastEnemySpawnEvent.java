package com.mygdx.towerdefence.events.eventClasses;

public class LastEnemySpawnEvent implements StateEvent {
    @Override
    public void execute(StateHolder state) {
        state.markLastEnemySpawn();
    }
}
