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
        int lives=3;
        try {
            URL resource = getClass().getClassLoader().getResource("levels/level" + levelNumber + ".txt");
            if (resource == null) {
                throw new IOException("Ficheiro não encontrado!");
            }

            BufferedReader br = new BufferedReader(new FileReader(resource.getFile()));

            for (String line; (line = br.readLine()) != null; ) {
                if (line.trim().startsWith("Lives:")) {
                    try {
                        lives = Integer.parseInt(line.split(":")[1].trim());
                    } catch (NumberFormatException e) {
                        System.err.println("Erro ao ler vidas: " + e.getMessage());
                    }
                    continue;
                }
                lines.add(line);
            }
            br.close();

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        String[] levelDesign = lines.toArray(new String[0]);

        Level level = new Level(levelDesign[0].length(), levelDesign.length);
        level.loadLevel(levelDesign);
        level.setLives(lives);
        level.initialize();
        return level;
    }
}