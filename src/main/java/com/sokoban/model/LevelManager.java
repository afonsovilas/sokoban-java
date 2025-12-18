package com.sokoban.model;

import java.io.*;

public class LevelManager {
    private int maxUnlockedLevel;
    private static final String PROGRESS_FILE = "progress.txt";

    public LevelManager() {

        this.maxUnlockedLevel = loadProgress();
    }

    public void unlockNextLevel(int currentCompletedLevel) {
        if (currentCompletedLevel == maxUnlockedLevel) {
            maxUnlockedLevel++;
            saveProgress();
        }
    }

    private void saveProgress() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(PROGRESS_FILE))) {
            writer.write(String.valueOf(maxUnlockedLevel));
        } catch (IOException e) {
            System.err.println("Não foi possível guardar o progresso: " + e.getMessage());
        }
    }

    private int loadProgress() {
        File file = new File(PROGRESS_FILE);
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
