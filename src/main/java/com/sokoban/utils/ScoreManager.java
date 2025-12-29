package com.sokoban.utils;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class ScoreManager {
    private static String fileName = "src/main/resources/levels/scores.txt";

    public static void setFileName(String path) {
        fileName = path;
    }

    public static void saveScore(int level, int steps) {
        Map<Integer, Integer> scores = loadScores();

        if (!scores.containsKey(level) || steps < scores.get(level)) {
            scores.put(level, steps);
            writeScores(scores);
        }
    }

    public static Map<Integer, Integer> loadScores() {
        Map<Integer, Integer> scores = new HashMap<>();
        File file = new File(fileName);
        if (!file.exists()) return scores;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length == 2) {
                    try {
                        scores.put(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]));
                    } catch (NumberFormatException ignored) {}
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return scores;
    }

    private static void writeScores(Map<Integer, Integer> scores) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (Map.Entry<Integer, Integer> entry : scores.entrySet()) {
                writer.write(entry.getKey() + ":" + entry.getValue());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}