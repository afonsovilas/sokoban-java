package com.sokoban.model;

import java.util.Arrays;
import java.util.List;

public class Menu {
    private final List<String> entries;
    private int currentSelection = 0;
    private final boolean isPaused;

    public Menu(boolean isPaused) {
        this.isPaused = isPaused;
        if (isPaused) {
            this.entries = Arrays.asList("Restart Game","Resume","Score Board", "Exit");
            this.currentSelection = 1;
        }
        else  {
            this.entries = Arrays.asList("Start Game","Score Board", "Exit");
            this.currentSelection = 0;
        }
    }

    public void nextEntry() {
        currentSelection++;
        if (currentSelection > entries.size() - 1)
            currentSelection = 0;
    }

    public void previousEntry() {
        currentSelection--;
        if (currentSelection < 0)
            currentSelection = entries.size() - 1;
    }

    public String getEntry(int i) {
        return entries.get(i);
    }

    public boolean isSelected(int i) {
        return currentSelection == i;
    }

    public int getNumberEntries() {
        return entries.size();
    }

    public boolean isSelectedExit() {
        return currentSelection == entries.size() - 1;
    }

    public boolean isSelectedResume() {
        return isPaused && currentSelection==1;}

    public boolean isSelectedStart() {
        return currentSelection==0;
    }

    public boolean isSelectedScoreBoard() {
        return getEntry(currentSelection).equals("Score Board");
    }
}