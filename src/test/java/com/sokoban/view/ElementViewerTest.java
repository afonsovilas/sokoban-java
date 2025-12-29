package com.sokoban.view;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.sokoban.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.verify;

class ElementViewerTest {
    private TextGraphics graphics;

    @BeforeEach
    void setUp() {
        graphics = Mockito.mock(TextGraphics.class);
    }

    @Test
    void testDrawHero() {
        Hero hero = new Hero(10, 10);
        HeroViewer viewer = new HeroViewer();

        viewer.draw(hero, graphics);

        verify(graphics).setBackgroundColor(TextColor.Factory.fromString("#3E2723"));
        verify(graphics).setForegroundColor(TextColor.Factory.fromString("#FFD700"));
        verify(graphics).enableModifiers(SGR.BOLD);

        verify(graphics).putString(new TerminalPosition(10, 10), "\u263A");
    }

    @Test
    void testDrawWall() {
        Wall wall = new Wall(1, 1);
        WallViewer viewer = new WallViewer();

        viewer.draw(wall, graphics);

        verify(graphics).setBackgroundColor(TextColor.Factory.fromString("#8D6E63"));
        verify(graphics).setForegroundColor(TextColor.Factory.fromString("#4E342E"));
        verify(graphics).putString(new TerminalPosition(1, 1), " ");
    }

    @Test
    void testDrawBoxOnTarget() {
        Box box = new Box(5, 5);
        box.setOnTarget(true);
        BoxViewer viewer = new BoxViewer();

        viewer.draw(box, graphics);

        verify(graphics).setBackgroundColor(TextColor.Factory.fromString("#3E2723"));
        verify(graphics).setForegroundColor(TextColor.Factory.fromString("#76FF03"));
        verify(graphics).putString(new TerminalPosition(5, 5), "\u25A3");
    }

    @Test
    void testDrawBoxNormal() {
        Box box = new Box(5, 5);
        box.setOnTarget(false);
        BoxViewer viewer = new BoxViewer();

        viewer.draw(box, graphics);

        verify(graphics).setBackgroundColor(TextColor.Factory.fromString("#3E2723"));

        verify(graphics).setForegroundColor(TextColor.Factory.fromString("#FF6D00"));
        verify(graphics).putString(new TerminalPosition(5, 5), "\u25A3");
    }

    @Test
    void testDrawTarget() {
        Target target = new Target(2, 2);
        TargetViewer viewer = new TargetViewer();

        viewer.draw(target, graphics);

        verify(graphics).setBackgroundColor(TextColor.Factory.fromString("#3E2723"));
        verify(graphics).setForegroundColor(TextColor.Factory.fromString("#FF1744"));
        verify(graphics).putString(new TerminalPosition(2, 2), "+");
    }

    @Test
    void testDrawPortal() {
        Portal portal = new Portal(3, 3);
        PortalViewer viewer = new PortalViewer();

        viewer.draw(portal, graphics);

        verify(graphics).setBackgroundColor(TextColor.Factory.fromString("#3E2723"));
        verify(graphics).setForegroundColor(TextColor.Factory.fromString("#B388FF"));
        verify(graphics).enableModifiers(SGR.BOLD);
        verify(graphics).putString(new TerminalPosition(3, 3), "\u25CE");
    }

    @Test
    void testDrawPowerUp() {
        PowerUp powerUp = new PowerUp(4, 4);
        PowerUpViewer viewer = new PowerUpViewer();

        viewer.draw(powerUp, graphics);

        verify(graphics).setBackgroundColor(TextColor.Factory.fromString("#3E2723"));
        verify(graphics).setForegroundColor(TextColor.Factory.fromString("#FFD700"));
        verify(graphics).putString(4, 4, "\u26A1");
    }
}