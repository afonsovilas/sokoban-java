package com.sokoban.view;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.sokoban.model.Hero;
import com.sokoban.model.Level;
import com.sokoban.model.Wall;
import com.sokoban.view.LevelViewer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class LevelViewerTest {
    private TextGraphics mainGraphics;
    private TextGraphics subGraphics;
    private Level level;
    private LevelViewer viewer;

    @BeforeEach
    void setUp() {
        mainGraphics = mock(TextGraphics.class);
        subGraphics = mock(TextGraphics.class);

        when(mainGraphics.newTextGraphics(any(TerminalPosition.class), any(TerminalSize.class)))
                .thenReturn(subGraphics);

        level = mock(Level.class);
        when(level.getWidth()).thenReturn(10);
        when(level.getHeight()).thenReturn(10);

        when(level.getWalls()).thenReturn(new ArrayList<>());
        when(level.getBoxes()).thenReturn(new ArrayList<>());
        when(level.getTargets()).thenReturn(new ArrayList<>());

        viewer = new LevelViewer(level);
    }

    @Test
    void testDrawBorderAndBackground() {

        viewer.draw(mainGraphics, 80, 40);
        verify(mainGraphics).setBackgroundColor(TextColor.Factory.fromString("#2C1A0B"));

        verify(mainGraphics).fillRectangle(
                eq(new TerminalPosition(33, 14)),
                any(TerminalSize.class),
                eq(' ')
        );

        verify(mainGraphics).newTextGraphics(
                eq(new TerminalPosition(35, 15)),
                eq(new TerminalSize(10, 10))
        );
    }

    @Test
    void testDrawElementsDelegation() {
        Wall wall = new Wall(1, 1);
        List<Wall> walls = new ArrayList<>();
        walls.add(wall);
        when(level.getWalls()).thenReturn(walls);

        viewer.draw(mainGraphics, 80, 40);
        verify(subGraphics).putString(any(TerminalPosition.class), eq("#"));
    }

    @Test
    void testDrawHero() {
        Hero hero = new Hero(5, 5);
        when(level.getHero()).thenReturn(hero);

        viewer.draw(mainGraphics, 80, 40);
        verify(subGraphics).setForegroundColor(TextColor.Factory.fromString("#FFFF33")); // Amarelo
        verify(subGraphics).putString(eq(new TerminalPosition(5, 5)), eq("X"));
    }
}
