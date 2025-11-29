package com.sokoban.view;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.sokoban.model.Level;
import com.sokoban.model.Box;
import com.sokoban.model.Target;
import com.sokoban.model.Wall;

public class LevelViewer {
    private final Level level;

    public LevelViewer(Level level) {
        this.level = level;
    }

    public void draw(TextGraphics graphics, int screenWidth, int screenHeight) {
        int offsetX = (screenWidth - level.getWidth()) / 2;
        int offsetY = (screenHeight - level.getHeight()) / 2;

        graphics.setBackgroundColor(TextColor.Factory.fromString("#2C1A0B"));

        int borderX = 2;
        int borderY = 1;
        graphics.fillRectangle(
                new TerminalPosition(offsetX - borderX, offsetY - borderY),
                new TerminalSize(level.getWidth() + (borderX * 2), level.getHeight() + (borderY * 2)),
                ' '
        );


        TextGraphics centeredGraphics = graphics.newTextGraphics(
                new TerminalPosition(offsetX, offsetY),
                new TerminalSize(level.getWidth(), level.getHeight())
        );

        for (Wall wall : level.getWalls()) wall.draw(centeredGraphics);
        for (Target target : level.getTargets()) target.draw(centeredGraphics);

        for (Box box : level.getBoxes()) {
            boolean isOnTarget = false;
            for (Target target : level.getTargets()) {
                if (box.getPosition().equals(target.getPosition())) {
                    isOnTarget = true;
                    break;
                }
            }
            box.setOnTarget(isOnTarget);
            box.draw(centeredGraphics);
        }

        if (level.getHero() != null) {
            level.getHero().draw(centeredGraphics);
        }
    }
}
