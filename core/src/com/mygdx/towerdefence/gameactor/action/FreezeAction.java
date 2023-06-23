package com.mygdx.towerdefence.gameactor.action;

import com.mygdx.towerdefence.events.eventClasses.FreezeActorEvent;
import com.mygdx.towerdefence.events.eventClasses.FreezingEffectEvent;
import com.mygdx.towerdefence.framework.screens.LevelScreen;
import com.mygdx.towerdefence.gameactor.ActorType;
import com.mygdx.towerdefence.gameactor.GameActor;

import java.util.Map;

public class FreezeAction extends DoNothingAction {
    private float duration;
    //private float range;
    public final static String[] argList = new String[]{"duration"};

    public FreezeAction(float rate, float range, Map<String, Float> params) {
        super(rate, range, params);
        duration = Math.round(params.get(argList[0]));
    }

    @Override
    public boolean call(GameActor caller, float delta, GameActor target) {
        if (target == null) return false;
        boolean targetsEnemy = (target.getType() == ActorType.Enemy);

        if (target.getPosition().dst(caller.getPosition()) <= getRange()) {
            LevelScreen.eventQueue.addViewEvent(new FreezingEffectEvent(duration, target.getPosition().x, target.getPosition().y));
            LevelScreen.eventQueue.addStateEvent(new FreezeActorEvent(duration, target.getRefID(), targetsEnemy));
            return true;
        }
        return false;
    }

    public float getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}
