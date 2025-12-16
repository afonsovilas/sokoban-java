package com.sokoban.view;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.Screen;
import com.sokoban.model.LevelMenu;

public class LevelMenuViewer {
    private final LevelMenu model;

    public LevelMenuViewer(LevelMenu model) {
        this.model = model;
    }

    public void draw(Screen screen, TextGraphics graphics) {

        graphics.setBackgroundColor(TextColor.Factory.fromString("#3E2723"));
        graphics.fillRectangle(new TerminalPosition(0, 0), screen.getTerminalSize(), ' ');

        int width = screen.getTerminalSize().getColumns();
        int height = screen.getTerminalSize().getRows();

        graphics.setForegroundColor(TextColor.Factory.fromString("#4DD0E1"));
        graphics.enableModifiers(SGR.BOLD);
        graphics.putString(new TerminalPosition((width - 11) / 2, 3), "LEVELS MENU");
        graphics.disableModifiers(SGR.BOLD);

        int columns = 2;
        int boxWidth = 22;
        int boxHeight = 5;
        int paddingX = 6;
        int paddingY = 2;

        int totalGridWidth = (columns * boxWidth) + ((columns - 1) * paddingX);
        int startX = (width - totalGridWidth) / 2;
        int startY = 8;

        for (int i = 0; i < model.getNumberLevels(); i++) {
            int col = i % columns;
            int row = i / columns;

            int x = startX + (col * (boxWidth + paddingX));
            int y = startY + (row * (boxHeight + paddingY));

            drawLevelBox(graphics, x, y, boxWidth, boxHeight, i);
        }

        String footer = "[ENTER] SELECT |  [Q] MENU";

        graphics.setBackgroundColor(TextColor.Factory.fromString("#3E2723"));
        graphics.setForegroundColor(TextColor.Factory.fromString("#BCAAA4"));
        graphics.putString(new TerminalPosition((width - footer.length()) / 2, height - 8), footer);
    }

    private void drawLevelBox(TextGraphics graphics, int x, int y, int width, int height, int index) {
        boolean isSelected = model.isSelected(index);
        boolean isUnlocked = model.isUnlocked(index);

        String text = model.getEntryTitle(index);
        String backgroundColor;
        String textColor;

        if (isUnlocked) {
            if (isSelected) {

                backgroundColor = "#FF9800";
                textColor = "#3E2723";
            } else {

                backgroundColor = "#D7CCC8";
                textColor = "#3E2723";
            }
        } else {

            backgroundColor = "#5D4037";
            textColor = "#8D6E63";
            text += " \uD83D\uDD12";
        }

        graphics.setBackgroundColor(TextColor.Factory.fromString(backgroundColor));
        graphics.setForegroundColor(TextColor.Factory.fromString(textColor));
        graphics.fillRectangle(new TerminalPosition(x, y), new TerminalSize(width, height), ' ');

        if (isSelected) graphics.enableModifiers(SGR.BOLD);

        int textX = x + (width - text.length()) / 2;
        int textY = y + (height / 2);

        graphics.putString(new TerminalPosition(textX, textY), text);

        graphics.disableModifiers(SGR.BOLD);
    }
}