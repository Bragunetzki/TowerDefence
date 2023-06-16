package com.mygdx.towerdefence.gameactor.action;

import com.mygdx.towerdefence.events.RangedAttackEvent;
import com.mygdx.towerdefence.framework.LevelView;
import com.mygdx.towerdefence.gameactor.ActorType;
import com.mygdx.towerdefence.gameactor.GameActor;
import com.mygdx.towerdefence.framework.screens.LevelScreen;

import java.util.Map;

public class BasicAttackAction extends DoNothingAction {
    private final float range;
    private final int damage;
    public final static String[] argList = new String[]{"damage"};

    public BasicAttackAction(float rate, float range, Map<String, Float> params) {
        super(rate, range, params);
        this.range = range;
        damage = Math.round(params.get(argList[0]));
    }

    @Override
    public boolean call(GameActor caller, float delta, GameActor target) {
        if (target == null) return false;
        boolean targetsEnemy = (target.getType() == ActorType.Enemy);

        if (target.getPosition().dst(caller.getPosition()) <= range) {
            if (range <= LevelView.TilE_SIZE / 2)
                target.applyDamage(damage);
            else
                LevelScreen.eventQueue.addViewEvent(new RangedAttackEvent(damage, caller.getPosition().x, caller.getPosition().y, target.getRefID(), targetsEnemy));
            return true;
        }
        return false;
    }
}
