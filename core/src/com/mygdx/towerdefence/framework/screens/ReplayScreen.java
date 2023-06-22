package com.mygdx.towerdefence.framework.screens;

import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.JsonWriter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mygdx.towerdefence.TowerDefenceGame;
import com.mygdx.towerdefence.client.Client;
import com.mygdx.towerdefence.events.ViewEvent;
import com.mygdx.towerdefence.events.eventClasses.StateEvent;
import com.mygdx.towerdefence.framework.LevelView;
import com.mygdx.towerdefence.level.LevelController;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.LinkedList;
import java.util.Queue;

import static com.mygdx.towerdefence.framework.screens.LevelScreen.eventQueue;

public class ReplayScreen extends BasicScreen {
    Client client;
    private final LevelController controller;
    private final LevelView levelView;
    private final Queue<Float> stateTimeQueue = new LinkedList<>();
    private final Queue<Float> viewTimeQueue = new LinkedList<>();
    private final Queue<StateEvent> plannedStateEvents = new LinkedList<>();
    private final Queue<ViewEvent> plannedViewEvents = new LinkedList<>();
    private float timer = 0;

    public ReplayScreen(TowerDefenceGame game) {
        super(game);
        this.client = new Client("10.244.176.152", 5555);
        client.setPlayerNum(-1);
        int levelID = fillQueue();
        controller = new LevelController(game.getCreator(), levelID, false, true);
        levelView = new LevelView(this, game, levelID, controller.getLevelState().getMap().mapArr, client, true);
        eventQueue.subscribeState(controller.getLevelState());
        eventQueue.subscribeView(levelView);
        getStageMultiplexer().addStage(levelView);
    }

    @Override
    public void render(float delta) {
        timer += delta;
        if (!stateTimeQueue.isEmpty()) {
            float nextEventTime = stateTimeQueue.peek();
            while (nextEventTime <= timer) {
                StateEvent event = plannedStateEvents.remove();
                stateTimeQueue.remove();
                eventQueue.addStateEvent(event);
                if (stateTimeQueue.isEmpty())
                    break;
                nextEventTime = stateTimeQueue.peek();
            }
        }
        if (!viewTimeQueue.isEmpty()) {
            float nextEventTime = viewTimeQueue.peek();
            while (nextEventTime <= timer) {
                ViewEvent event = plannedViewEvents.remove();
                viewTimeQueue.remove();
                eventQueue.addViewEvent(event);
                if (viewTimeQueue.isEmpty())
                    break;
                nextEventTime = viewTimeQueue.peek();
            }
        }
        controller.update(delta);
        levelView.update(controller.getLevelState());
        eventQueue.update(delta);
        super.render(delta);
    }

    private int fillQueue() {
        int levelID = 0;
        JsonReader jsonReader = new JsonReader();
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("replay.json"));
            JsonValue json = jsonReader.parse(bufferedReader);
            if (json.has("levelID")) {
                levelID = json.getInt("levelID");
            }
            JsonValue jsonEvents = json.get("events");
            boolean readingName = true;
            Class<?> eventClass = null;
            for (JsonValue jsonElem = jsonEvents.child; jsonElem != null; jsonElem = jsonElem.next) {
                if (readingName) {
                    String name = jsonElem.getString("name");
                    float time = jsonElem.getFloat("time");
                    eventClass = Class.forName(name);
                    if (StateEvent.class.isAssignableFrom(eventClass)) {
                        stateTimeQueue.add(time);
                    }
                    else if (ViewEvent.class.isAssignableFrom(eventClass)) {
                        viewTimeQueue.add(time);
                    }
                    readingName = false;
                } else {
                    Object event = gson.fromJson(jsonElem.toJson(JsonWriter.OutputType.json), eventClass);
                    if (event instanceof StateEvent) {
                        plannedStateEvents.add((StateEvent) event);
                    }
                    else if (event instanceof ViewEvent) {
                        plannedViewEvents.add((ViewEvent) event);
                    }
                    readingName = true;
                }
            }
        } catch (FileNotFoundException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return levelID;
    }
}
