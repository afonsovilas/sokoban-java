package com.sokoban.view;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.Screen;

import java.util.Map;

public class ScoreBoardViewer {
    private final Map<Integer, Integer> scores;

    public ScoreBoardViewer(Map<Integer, Integer> scores) {
        this.scores = scores;
    }

    public void draw(Screen screen, TextGraphics graphics) {
        graphics.setBackgroundColor(TextColor.Factory.fromString("#3E2723"));
        graphics.fillRectangle(new TerminalPosition(0, 0), screen.getTerminalSize(), ' ');

        int width = screen.getTerminalSize().getColumns();
        int height = screen.getTerminalSize().getRows();

        String title = "HIGHEST SCORES";
        graphics.setForegroundColor(TextColor.Factory.fromString("#FFD700"));
        graphics.enableModifiers(SGR.BOLD);
        graphics.putString(new TerminalPosition((width - title.length()) / 2, height / 4), title);

        graphics.disableModifiers(SGR.BOLD);
        graphics.setForegroundColor(TextColor.Factory.fromString("#FFFFFF"));

        int startY = (height / 4) + 4;

        if (scores.isEmpty()) {
            String msg = "No scores yet!";
            graphics.putString(new TerminalPosition((width - msg.length()) / 2, startY), msg);
        } else {
            int i = 0;
            for (Map.Entry<Integer, Integer> entry : scores.entrySet()) {
                String line = String.format("Level %d ...... %d steps", entry.getKey(), entry.getValue());
                graphics.putString(new TerminalPosition((width - line.length()) / 2, startY + i), line);
                i += 2;
            }
        }

        String exitMsg = "[Q] MENU";
        graphics.setForegroundColor(TextColor.Factory.fromString("#D7CCC8"));
        graphics.putString(new TerminalPosition((width - exitMsg.length()) / 2, height - 10), exitMsg);
    }
}