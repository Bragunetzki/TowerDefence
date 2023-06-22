package com.mygdx.towerdefence.client;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.net.Socket;
import com.badlogic.gdx.net.SocketHints;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.Logger;
import com.mygdx.towerdefence.client.clientCommands.ClientCommand;
import com.mygdx.towerdefence.client.clientCommands.ConstructBuildingClientCommand;
import com.mygdx.towerdefence.client.clientCommands.DemolishBuildingClientCommand;
import com.mygdx.towerdefence.client.serverCommands.*;
import com.mygdx.towerdefence.events.ActorDeathEvent;
import com.mygdx.towerdefence.events.AlterCurrencyEvent;
import com.mygdx.towerdefence.events.ConstructBuildingEvent;
import com.mygdx.towerdefence.framework.screens.LevelScreen;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;

public class Client {
    public static final Logger logger = new Logger("Client", Logger.INFO);
    private final Map<String, ServerCommand> commandMap;
    private final String addr;
    private final int port;
    private BufferedReader in;
    private OutputStreamWriter out;
    private final JsonReader jsonReader;

    private final LinkedBlockingQueue<ClientCommand> commandQueue;
    private Socket socket = null;

    public Client(String addr, int port) {
        this.addr = addr;
        this.port = port;
        commandMap = new HashMap<>();
        commandMap.put("spawnEnemy", new SpawnEnemyCommand());
        commandMap.put("actorStates", new ActorStateUpdateCommand());
        commandMap.put("meleeAttack", new MeleeAttackCommand());
        commandMap.put("rangedAttack", new RangedAttackCommand());
        commandMap.put("mineCurrency", new MineCurrencyCommand());
        commandMap.put("actorDeath", new ActorDeathCommand());
        commandMap.put("constructBuilding", new ConstructBuildingCommand());
        commandMap.put("damageActor", new DamageActorCommand());
        commandMap.put("moneyChanged", new MoneyChangedCommand());
        commandMap.put("endGame", new EndGameCommand());

        commandQueue = new LinkedBlockingQueue<>();
        jsonReader = new JsonReader();
    }

    public void start() {
        SocketHints hints = new SocketHints();
        socket = Gdx.net.newClientSocket(Net.Protocol.TCP, addr, port, hints);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new OutputStreamWriter(socket.getOutputStream());

        new ReadingThread().start();
        new WritingThread().start();
    }

    private void shutDown() {
        try {
            if (socket.isConnected()) {
                socket.dispose();
                in.close();
                out.close();
            }
        } catch (IOException ignored) {}
    }

    public void pushCommand(ClientCommand command) {
        commandQueue.add(command);
    }

    public void constructBuilding(Integer id, int tileX, int tileY) {
        if (socket != null) {
            try {
                commandQueue.put(new ConstructBuildingClientCommand(id, tileX, tileY));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        else {
            LevelScreen.eventQueue.addStateEvent(new ConstructBuildingEvent(id, tileX, tileY));
        }
    }

    public void demolishBuilding(int refID, int demolitionReturn) {
        if (socket != null) {
            try {
                commandQueue.put(new DemolishBuildingClientCommand(refID));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        else {
            LevelScreen.eventQueue.addStateEvent(new ActorDeathEvent(refID, false));
            LevelScreen.eventQueue.addStateEvent(new AlterCurrencyEvent(demolitionReturn));
        }
    }

    private class ReadingThread extends Thread {
        @Override
        public void run() {
            while (true) {
                String line;
                try {
                    line = in.readLine();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                JsonValue msg = jsonReader.parse(line);

                logger.info(msg.toString());
                if (msg.has("cmd")) {
                    String commandName = msg.getString("cmd");
                    if (commandMap.containsKey(commandName)) {
                        commandMap.get(commandName).execute(msg);
                    } else {
                        logger.info("no command with name \"" + commandName + "\" found.");
                    }
                } else {
                    logger.info("No command could be read from server message.");
                }
            }
        }
    }

    public class WritingThread extends Thread {
        @Override
        public void run() {
            while (true) {
                ClientCommand cmd = commandQueue.poll();
                if (cmd != null) {
                    logger.info(cmd.getString());
                    try {
                        out.write(cmd.getString());
                        out.flush();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }
}
