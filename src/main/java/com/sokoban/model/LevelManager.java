package com.sokoban.model;

public class LevelManager {
    private int maxUnlockedLevel;

    public LevelManager() {

        this.maxUnlockedLevel = 1;
    }

    public LevelManager(int savedLevel) {
        this.maxUnlockedLevel = savedLevel;
    }

    public void unlockNextLevel(int currentCompletedLevel) {
        if (currentCompletedLevel == maxUnlockedLevel) {
            maxUnlockedLevel++;
        }
    }

    public boolean isLevelUnlocked(int level) {
        return level <= maxUnlockedLevel;
    }

    public int getMaxUnlockedLevel() {
        return maxUnlockedLevel;
    }
}
