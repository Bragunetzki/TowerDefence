package com.mygdx.towerdefence.events;

import com.badlogic.gdx.utils.Pool;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class EventQueue {
    private final Queue<StateEvent> stateEvents;
    private final Queue<ViewEvent> viewEvents;
    private final List<StateHolder> stateSubscribers;
    private final List<ViewHolder> viewSubscribers;

    public EventQueue() {
        stateEvents = new LinkedList<>();
        viewEvents = new LinkedList<>();
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
}
