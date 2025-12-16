package com.sokoban.model;

import com.sokoban.controller.Command;

import java.util.Arrays;
import java.util.List;

public class Menu {

    public record MenuEntry(String text, Command command) {}

    private final List<MenuEntry> entries;
    private int currentSelection = 0;


    public Menu(List<MenuEntry> entries) {
        this.entries = entries;
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

    public String getEntryText(int i) {
        return entries.get(i).text();
    }

    public Command getCurrentCommand() {
        return entries.get(currentSelection).command();
    }

    public boolean isSelected(int i) {
        return currentSelection == i;
    }

    public int getNumberEntries() {
        return entries.size();
    }
}