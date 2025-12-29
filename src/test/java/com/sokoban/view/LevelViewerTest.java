package com.sokoban.view;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.sokoban.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.Collections;
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

        when(level.getWalls()).thenReturn(Collections.emptyList());
        when(level.getBoxes()).thenReturn(Collections.emptyList());
        when(level.getTargets()).thenReturn(Collections.emptyList());
        when(level.getPortals()).thenReturn(Collections.emptyList());
        when(level.getPowerUps()).thenReturn(Collections.emptyList());

        viewer = new LevelViewer(level);
    }

    @Test
    void testMapCenteringMath() {

        when(level.getWidth()).thenReturn(10);
        when(level.getHeight()).thenReturn(10);

        viewer.draw(mainGraphics, 80, 24);

        verify(mainGraphics).newTextGraphics(
                eq(new TerminalPosition(35, 7)),
                eq(new TerminalSize(10, 10))
        );

        verify(mainGraphics).fillRectangle(
                eq(new TerminalPosition(33, 6)),
                any(TerminalSize.class),
                eq(' ')
        );
    }

    @Test
    void testHelpTextPosition() {

        when(level.getWidth()).thenReturn(10);
        when(level.getHeight()).thenReturn(10);

        viewer.draw(mainGraphics, 80, 24);

        verify(mainGraphics).putString(
                eq(new TerminalPosition(24, 14)),
                eq("[Q] MENU   [R] RESET   [U] UNDO")
        );
    }

    @Test
    void testDrawAllElementTypes() {

        when(level.getWidth()).thenReturn(10);
        when(level.getHeight()).thenReturn(10);

        Wall wall = new Wall(1, 1);
        Target target = new Target(2, 2);
        Box box = new Box(3, 3);
        Hero hero = new Hero(4, 4);

        when(level.getWalls()).thenReturn(List.of(wall));
        when(level.getTargets()).thenReturn(List.of(target));
        when(level.getBoxes()).thenReturn(List.of(box));
        when(level.getHero()).thenReturn(hero);


        viewer.draw(mainGraphics, 80, 24);

        verify(subGraphics).putString(eq(new TerminalPosition(1, 1)), eq(" "));

        verify(subGraphics).putString(eq(new TerminalPosition(2, 2)), eq("+"));

        verify(subGraphics).putString(eq(new TerminalPosition(3, 3)), eq("\u25A3"));

        verify(subGraphics).putString(eq(new TerminalPosition(4, 4)), eq("\u263A"));
    }

    @Test
    void testDrawHUDExactPosition() {

        when(level.getWidth()).thenReturn(10);
        when(level.getHeight()).thenReturn(10);
        when(level.getSteps()).thenReturn(0);
        when(level.getLives()).thenReturn(3);
        when(level.getPowerUpTime()).thenReturn(0L);

        viewer.draw(mainGraphics, 80, 24);

        verify(mainGraphics).putString(eq(new TerminalPosition(45, 4)), eq("Steps: 0"));

        verify(mainGraphics).putString(eq(new TerminalPosition(27, 4)), contains("Lives:"));
    }
}