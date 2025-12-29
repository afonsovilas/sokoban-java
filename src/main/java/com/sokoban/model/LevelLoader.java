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
            return null;
        }

        return createLevelFromLines(lines);
    }

    Level createLevelFromLines(List<String> lines) {
        int lives = 3;
        List<String> mapLines = new ArrayList<>();

        for (String line : lines) {
            if (line.trim().startsWith("Lives:")) {
                try {
                    lives = Integer.parseInt(line.split(":")[1].trim());
                } catch (NumberFormatException e) {
                    System.err.println("Erro ao ler vidas: " + e.getMessage());
                }
                continue;
            }
            mapLines.add(line);
        }

        if (mapLines.isEmpty()) return null;

        String[] levelDesign = mapLines.toArray(new String[0]);

        int width = 0;
        for (String s : levelDesign) width = Math.max(width, s.length());
        int height = levelDesign.length;

        Level level = new Level(width, height);

        for (int y = 0; y < height; y++) {
            String line = levelDesign[y];
            for (int x = 0; x < width; x++) {
                if (x >= line.length()) break;

                char c = line.charAt(x);
                Element element = ElementFactory.createElement(c, x, y);

                if (element != null) {
                    level.addElement(element);
                }
            }
        }

        level.setLives(lives);
        return level;
    }
}