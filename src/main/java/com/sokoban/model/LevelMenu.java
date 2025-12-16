package com.sokoban.model;

import com.sokoban.controller.Command;
import com.sokoban.controller.LoadLevelCommand;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class LevelMenu {

    public record LevelEntry(String title, Command command) {}

    private final List<LevelEntry> levels;
    private int currentSelection = 0;

    private final LevelManager levelManager;

    private static final int COLUMNS = 2;

    public LevelMenu(LevelManager levelManager) {
        this.levelManager = levelManager;
        this.levels = new ArrayList<>();
        loadAvailableLevels();
    }

    private void loadAvailableLevels() {
        int i = 1;
        while (true) {
            URL resource = getClass().getClassLoader().getResource("levels/level" + i + ".txt");
            if (resource != null) {
                levels.add(new LevelEntry("Level " + i, new LoadLevelCommand(i, levelManager)));
                i++;
            } else {
                break;
            }
        }
    }

    public void moveRight() {
        if (currentSelection % COLUMNS < COLUMNS - 1 && currentSelection + 1 < levels.size()) {
            currentSelection++;
        }
    }

    public void moveLeft() {
        if (currentSelection % COLUMNS > 0) {
            currentSelection--;
        }
    }

    public void moveDown() {
        if (currentSelection + COLUMNS < levels.size()) {
            currentSelection += COLUMNS;
        }
    }

    public void moveUp() {
        if (currentSelection - COLUMNS >= 0) {
            currentSelection -= COLUMNS;
        }
    }

    public boolean isSelected(int i) {
        return currentSelection == i;
    }

    public boolean isUnlocked(int i) {
        return (i + 1) <= levelManager.getMaxUnlockedLevel();
    }

    public int getNumberLevels() {
        return levels.size();
    }

    public String getEntryTitle(int i) {
        return levels.get(i).title();
    }

    public Command getSelectedCommand() {
        return levels.get(currentSelection).command();
    }

    public int getSelectedIndex() {
        return currentSelection;
    }
}