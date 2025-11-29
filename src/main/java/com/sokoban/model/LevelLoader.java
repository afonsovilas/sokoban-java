package com.sokoban.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class LevelLoader {

    public Level loadLevel(int levelNumber) {
        List<String> lines = new ArrayList<>();

        try {
            URL resource = getClass().getClassLoader().getResource("levels/level" + levelNumber + ".txt");
            if (resource == null) {
                throw new IOException("Ficheiro não encontrado!");
            }

            BufferedReader br = new BufferedReader(new FileReader(resource.getFile()));

            for (String line; (line = br.readLine()) != null; ) {
                lines.add(line);
            }
            br.close();

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        String[] levelDesign = lines.toArray(new String[0]);

        return new Level(levelDesign[0].length(), levelDesign.length) {
            {
                loadLevel(levelDesign);
                initialize();
            }
        };
    }
}