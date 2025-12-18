package com.sokoban.view;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.sokoban.model.*;

public class LevelViewer {
    private final Level level;

    public LevelViewer(Level level) {
        this.level = level;
    }

    public void draw(TextGraphics graphics, int screenWidth, int screenHeight) {
        int offsetX = (screenWidth - level.getWidth()) / 2;
        int offsetY = (screenHeight - level.getHeight()) / 2;

        graphics.setBackgroundColor(TextColor.Factory.fromString("#3E2723"));

        int borderX = 2;
        int borderY = 1;
        graphics.fillRectangle(
                new TerminalPosition(offsetX - borderX, offsetY - borderY),
                new TerminalSize(level.getWidth() + (borderX * 2), level.getHeight() + (borderY * 2)),
                ' '
        );

        graphics.setForegroundColor(TextColor.Factory.fromString("#FFFFFF"));
        graphics.enableModifiers(SGR.BOLD);

        String stepText = "Steps: " + level.getSteps();
        graphics.putString(
                new TerminalPosition(offsetX + level.getWidth() - stepText.length() + borderX, offsetY - borderY - 2),
                stepText
        );

        graphics.setForegroundColor(TextColor.Factory.fromString("#FF1744"));
        StringBuilder hearts = new StringBuilder();
        for (int i = 0; i < level.getLives(); i++) {
            hearts.append("\u2764 ");
        }

        if (level.getLives() == 0) {
            graphics.setForegroundColor(TextColor.Factory.fromString("#B0BEC5"));
            hearts.append("0");
        }

        String livesText = "Lives: " + hearts.toString();
        graphics.putString(
                new TerminalPosition(offsetX - borderX, offsetY - borderY - 2),
                livesText
        );

        graphics.disableModifiers(SGR.BOLD);

        TextGraphics centeredGraphics = graphics.newTextGraphics(
                new TerminalPosition(offsetX, offsetY),
                new TerminalSize(level.getWidth(), level.getHeight())
        );

        TargetViewer targetViewer = new TargetViewer();
        for (Target target : level.getTargets()) targetViewer.draw(target,centeredGraphics);

        PortalViewer portalViewer = new PortalViewer();
        for (Portal portal : level.getPortals()) portalViewer.draw(portal,centeredGraphics);

        PowerUpViewer powerUpViewer = new PowerUpViewer();
        for (PowerUp powerUp : level.getPowerUps()) powerUpViewer.draw(powerUp,centeredGraphics);

        WallViewer wallViewer = new WallViewer();
        for (Wall wall : level.getWalls()) wallViewer.draw(wall, centeredGraphics);

        BoxViewer boxViewer = new BoxViewer();
        for (Box box : level.getBoxes())  boxViewer.draw(box,centeredGraphics);

        if (level.getHero() != null) {
            new HeroViewer().draw(level.getHero(), centeredGraphics);
        }

        graphics.setForegroundColor(TextColor.Factory.fromString("#D7CCC8"));
        graphics.disableModifiers(SGR.BOLD);

        String helpText = "[Q] MENU   [R] RESET   [U] UNDO";

        graphics.putString(
                new TerminalPosition((screenWidth - helpText.length()) / 2, screenHeight - 8),
                helpText
        );
    }
}
