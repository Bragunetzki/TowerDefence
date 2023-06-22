package com.mygdx.towerdefence.events.eventClasses;

public class SetCurrencyEvent implements StateEvent {
    private final int currency;
    public SetCurrencyEvent(int currency) {
        this.currency = currency;
    }

    @Override
    public void execute(StateHolder state) {
        state.setCurrency(currency);
    }
}
