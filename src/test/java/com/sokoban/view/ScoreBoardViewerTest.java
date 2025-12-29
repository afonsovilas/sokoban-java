package com.sokoban.view;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.Screen;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ScoreBoardViewerTest {
    private Screen screen;
    private TextGraphics graphics;

    @BeforeEach
    void setUp() {
        screen = Mockito.mock(Screen.class);
        graphics = Mockito.mock(TextGraphics.class);

        when(screen.getTerminalSize()).thenReturn(new TerminalSize(80, 24));
    }

    @Test
    void testDrawStaticElementsExactPosition() {

        ScoreBoardViewer viewer = new ScoreBoardViewer(Map.of());

        viewer.draw(screen, graphics);

        verify(graphics).putString(
                eq(new TerminalPosition(33, 6)),
                eq("HIGHEST SCORES")
        );

        verify(graphics).putString(
                eq(new TerminalPosition(36, 12)),
                eq("[Q] MENU")
        );
    }

    @Test
    void testDrawEmptyMessageExactPosition() {
        ScoreBoardViewer viewer = new ScoreBoardViewer(Map.of());

        viewer.draw(screen, graphics);

        verify(graphics).putString(
                eq(new TerminalPosition(33, 10)),
                eq("No scores yet!")
        );
    }

    @Test
    void testDrawScoresLoopExactPosition() {

        Map<Integer, Integer> scores = new LinkedHashMap<>();
        scores.put(1, 100);
        scores.put(2, 200);

        ScoreBoardViewer viewer = new ScoreBoardViewer(scores);

        when(screen.getTerminalSize()).thenReturn(new TerminalSize(80, 24));

        viewer.draw(screen, graphics);

        verify(graphics).putString(
                eq(new TerminalPosition(28, 10)),
                eq("Level 1 ...... 100 steps")
        );

        verify(graphics).putString(
                eq(new TerminalPosition(28, 12)),
                eq("Level 2 ...... 200 steps")
        );
    }
}