package com.sokoban.model;

import java.io.*;

public class LevelManager {
    private int maxUnlockedLevel;
    private final String filename;

    public LevelManager() {
        this("progress.txt");
    }
    public LevelManager(String filename) {
        this.filename = filename;
        this.maxUnlockedLevel = loadProgress();
    }

    public void unlockNextLevel(int currentCompletedLevel) {
        if (currentCompletedLevel == maxUnlockedLevel) {
            maxUnlockedLevel++;
            saveProgress();
        }
    }

    private void saveProgress() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(this.filename))) {
            writer.write(String.valueOf(maxUnlockedLevel));
        } catch (IOException e) {
            System.err.println("Não foi possível guardar o progresso: " + e.getMessage());
        }
    }

    private int loadProgress() {
        File file = new File(this.filename);
        if (!file.exists()) {
            return 1;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line = reader.readLine();
            if (line != null && !line.isEmpty()) {
                return Integer.parseInt(line.trim());
            }
        } catch (IOException | NumberFormatException e) {
            System.err.println("Erro ao ler o ficheiro de progresso. A reiniciar para nível 1.");
        }

        return 1;
    }

    public boolean isLevelUnlocked(int level) {
        return level <= maxUnlockedLevel;
    }

    public int getMaxUnlockedLevel() {
        return maxUnlockedLevel;
    }
}
