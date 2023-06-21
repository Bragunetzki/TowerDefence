package com.mygdx.towerdefence.client;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.net.Socket;
import com.badlogic.gdx.net.SocketHints;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.Logger;
import com.mygdx.towerdefence.client.serverCommands.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Map;

public class Client {
    public static final Logger logger = new Logger("Client", Logger.INFO);
    private final Map<String, ServerCommand> commandMap;
    private final String addr;
    private final int port;
    private BufferedReader in;
    private OutputStreamWriter out;
    private final JsonReader jsonReader;
    private int userID;

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
        jsonReader = new JsonReader();
    }

    public void start() {
        SocketHints hints = new SocketHints();
        Socket socket = Gdx.net.newClientSocket(Net.Protocol.TCP, addr, port, hints);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new OutputStreamWriter(socket.getOutputStream());

        new ReadingThread().start();
        new WritingThread().start();
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
                /*
                String userWord;
                time = new Date(); // текущая дата
                dt1 = new SimpleDateFormat("HH:mm:ss"); // берем только время до секунд
                dtime = dt1.format(time); // время
                userWord = inputUser.readLine(); // сообщения с консоли
                if (userWord.equals("stop")) {
                    out.write("stop" + "\n");
                    ClientSomthing.this.downService(); // харакири
                    break; // выходим из цикла если пришло "stop"
                } else {
                    out.write("(" + dtime + ") " + nickname + ": " + userWord + "\n"); // отправляем на сервер
                }
                out.flush(); // чистим
                 */
            }
        }
    }
}
