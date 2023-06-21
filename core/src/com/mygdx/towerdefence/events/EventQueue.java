package com.mygdx.towerdefence.events;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

public class EventQueue {
    private final LinkedBlockingQueue<StateEvent> stateEvents;
    private final LinkedBlockingQueue<ViewEvent> viewEvents;
    private final List<StateHolder> stateSubscribers;
    private final List<ViewHolder> viewSubscribers;

    public EventQueue() {
        stateEvents = new LinkedBlockingQueue<>();
        viewEvents = new LinkedBlockingQueue<>();
        stateSubscribers = new LinkedList<>();
        viewSubscribers = new LinkedList<>();
    }

    public void update() {
        while (!stateEvents.isEmpty()) {
            StateEvent event = stateEvents.remove();
            notifyStates(event);
        }
        while (!viewEvents.isEmpty()) {
            ViewEvent event = viewEvents.remove();
            notifyViews(event);
        }
    }

    public void addViewEvent(ViewEvent event) {
        viewEvents.add(event);
    }

    public void addStateEvent(StateEvent event) { stateEvents.add(event); }

    public void subscribeView(ViewHolder view) {
        viewSubscribers.add(view);
    }

    public void subscribeState(StateHolder state) {
        stateSubscribers.add(state);
    }

    private void notifyViews(ViewEvent event) {
        for (ViewHolder view : viewSubscribers) {
            event.execute(view);
        }
    }

    private void notifyStates(StateEvent event) {
        for (StateHolder state : stateSubscribers) {
            event.execute(state);
        }
    }

    public void clearAll() {
        stateEvents.clear();
        viewEvents.clear();
    }
}
