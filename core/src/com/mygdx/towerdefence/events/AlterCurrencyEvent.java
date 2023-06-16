package com.mygdx.towerdefence.events;

public class AlterCurrencyEvent implements StateEvent {
    private final int value;

    public AlterCurrencyEvent(int value) {
        this.value = value;
    }

    @Override
    public void execute(StateHolder state) {
        if (state.getCurrency() + value < 0) return;
        state.addCurrency(value);
    }
}
