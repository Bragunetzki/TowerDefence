package com.mygdx.towerdefence.events;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mygdx.towerdefence.events.eventClasses.*;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

public class EventQueue {
    private final LinkedBlockingQueue<StateEvent> stateEvents;
    private final LinkedBlockingQueue<ViewEvent> viewEvents;
    private final List<StateHolder> stateSubscribers;
    private final List<ViewHolder> viewSubscribers;
    private final List<Object> eventLog;
    private final List<Float> eventTimeLog;
    private final Gson gson;
    private float time;
    public EventQueue() {
        stateEvents = new LinkedBlockingQueue<>();
        viewEvents = new LinkedBlockingQueue<>();
        stateSubscribers = new LinkedList<>();
        viewSubscribers = new LinkedList<>();
        GsonBuilder builder = new GsonBuilder();
        gson = builder.create();
        eventLog = new ArrayList<>();
        eventTimeLog = new ArrayList<>();
        time = 0;
    }

    public void update(float delta) {
        time += delta;
        while (!stateEvents.isEmpty()) {
            StateEvent event = stateEvents.remove();
            eventLog.add(event);
            eventTimeLog.add(time);
            notifyStates(event);
        }
        while (!viewEvents.isEmpty()) {
            ViewEvent event = viewEvents.remove();
            eventLog.add(event);
            eventTimeLog.add(time);
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

    public void dispose(int levelID, boolean write) {
        if (write) {
            try {
                FileWriter writer = new FileWriter("replay.json");
                writer.write("{ \"levelID\": " + levelID + ", \"events\": [ ");
                for (int i = 0; i < eventLog.size(); i++) {
                    Object event = eventLog.get(i);
                    if (!shouldSerialize(event))
                        continue;
                    writer.write("{ \"name\": \"" + event.getClass().getName() + "\", \"time\": " + eventTimeLog.get(i) + " }, ");
                    writer.write(gson.toJson(event));
                    if (i < eventLog.size() - 1)
                        writer.write(", ");
                }
                writer.write(" ] }\n");
                writer.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        stateEvents.clear();
        viewEvents.clear();
        viewSubscribers.clear();
        stateSubscribers.clear();
    }

    private boolean shouldSerialize(Object event) {
        return !((event instanceof TileTouchedEvent) || (event instanceof ConstructBuildingViewEvent)
                || (event instanceof BuildingTouchedEvent)
                || (event instanceof BuildingDestroyedViewEvent));
    }
}
