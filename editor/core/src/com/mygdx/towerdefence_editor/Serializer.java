package com.mygdx.towerdefence_editor;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import com.google.gson.*;

public class Serializer {

    private final Gson gson;

    public Serializer() {
        gson = new GsonBuilder().setPrettyPrinting().create();
    }

    public void serialize(String filePath, Object object) {
        File file = new File(filePath);
        Writer writer = null;
        try {
            writer = new FileWriter(file, false);
            gson.toJson(object, writer);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            System.out.println("Exception thrown while writing to file " + filePath);
            e.printStackTrace();
        }
    }
}
