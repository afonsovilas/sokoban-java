package com.sokoban.view;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.sokoban.model.Box;
import com.sokoban.model.Hero;
import com.sokoban.model.Target;
import com.sokoban.model.Wall;
import com.sokoban.view.BoxViewer;
import com.sokoban.view.HeroViewer;
import com.sokoban.view.TargetViewer;
import com.sokoban.view.WallViewer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.any;
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
        verify(graphics).setBackgroundColor(TextColor.Factory.fromString("#2C1A0B"));
        verify(graphics).setForegroundColor(TextColor.Factory.fromString("#FFFF33"));
        verify(graphics).putString(new TerminalPosition(10, 10), "X");
    }

    @Test
    void testDrawWall() {
        Wall wall = new Wall(1, 1);
        WallViewer viewer = new WallViewer();

        viewer.draw(wall, graphics);

        verify(graphics).setBackgroundColor(TextColor.Factory.fromString("#2C1A0B"));
        verify(graphics).setForegroundColor(TextColor.Factory.fromString("#8B4513"));
        verify(graphics).putString(new TerminalPosition(1, 1), "#");
    }

    @Test
    void testDrawBoxOnTarget() {
        Box box = new Box(5, 5);
        box.setOnTarget(true);
        BoxViewer viewer = new BoxViewer();

        viewer.draw(box, graphics);

        verify(graphics).setBackgroundColor(TextColor.Factory.fromString("#2C1A0B"));
        verify(graphics).setForegroundColor(TextColor.Factory.fromString("#00FF00"));
        verify(graphics).putString(new TerminalPosition(5, 5), "O");
    }

    @Test
    void testDrawBoxNormal() {
        Box box = new Box(5, 5);
        box.setOnTarget(false);
        BoxViewer viewer = new BoxViewer();

        viewer.draw(box, graphics);

        verify(graphics).setForegroundColor(TextColor.Factory.fromString("#3399CC"));
        verify(graphics).putString(new TerminalPosition(5, 5), "O");
    }

    @Test
    void testDrawTarget() {
        Target target = new Target(2, 2);
        TargetViewer viewer = new TargetViewer();

        viewer.draw(target, graphics);

        verify(graphics).setBackgroundColor(TextColor.Factory.fromString("#2C1A0B"));
        verify(graphics).setForegroundColor(TextColor.Factory.fromString("#FF7F50"));
        verify(graphics).putString(new TerminalPosition(2, 2), "*");
    }
}
