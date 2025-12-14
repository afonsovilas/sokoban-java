package com.sokoban.view;

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
    }
}
