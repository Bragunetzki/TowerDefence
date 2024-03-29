package com.mygdx.towerdefence.gameactor.action;

import com.mygdx.towerdefence.events.eventClasses.AlterCurrencyEvent;
import com.mygdx.towerdefence.gameactor.GameActor;
import com.mygdx.towerdefence.framework.screens.LevelScreen;

import java.util.Map;

public class GenerateCurrencyAction extends DoNothingAction {
    private final static String[] argList = new String[]{"value"};
    private int value;

    public GenerateCurrencyAction(float rate, float range, Map<String, Float> params) {
        super(rate, range, params);
        value = Math.round(params.get(argList[0]));
    }

    @Override
    public boolean call(GameActor caller, float delta, GameActor target) {
        LevelScreen.eventQueue.addStateEvent(new AlterCurrencyEvent(value));
        return true;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
