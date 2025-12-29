package com.sokoban.view;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.Screen;
import com.sokoban.model.Menu;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class WinMenuViewerTest {
    private Screen screen;
    private TextGraphics graphics;
    private Menu menu;
    private WinMenuViewer viewer;

    @BeforeEach
    void setUp() {
        screen = Mockito.mock(Screen.class);
        graphics = Mockito.mock(TextGraphics.class);
        menu = Mockito.mock(Menu.class);

        when(screen.getTerminalSize()).thenReturn(new TerminalSize(80, 24));

        viewer = new WinMenuViewer(menu);
    }

    @Test
    void testDrawBoxAndStaticTextExactPosition() {
        when(menu.getNumberEntries()).thenReturn(0);

        viewer.draw(screen, graphics);

        verify(graphics).fillRectangle(
                eq(new TerminalPosition(20, 4)), // (startX, startY)
                eq(new TerminalSize(40, 15)),    // Tamanho fixo
                eq(' ')
        );

        verify(graphics).putString(
                eq(new TerminalPosition(32, 7)),
                eq("CONGRATULATIONS!")
        );

        verify(graphics).putString(
                eq(new TerminalPosition(29, 9)),
                eq("You Passed this Level")
        );
    }

    @Test
    void testDrawButtonsLoopExactPosition() {

        when(screen.getTerminalSize()).thenReturn(new TerminalSize(80, 24));

        when(menu.getNumberEntries()).thenReturn(2);

        when(menu.getEntryText(0)).thenReturn("Next");
        when(menu.isSelected(0)).thenReturn(true);

        when(menu.getEntryText(1)).thenReturn("Exit");
        when(menu.isSelected(1)).thenReturn(false);

        viewer.draw(screen, graphics);

        verify(graphics).putString(
                eq(new TerminalPosition(36, 13)),
                eq("[ Next ]")
        );

        verify(graphics).putString(
                eq(new TerminalPosition(36, 15)),
                eq("  Exit  ")
        );

        verify(graphics).setBackgroundColor(TextColor.Factory.fromString("#FF9800"));

        verify(graphics, times(2)).setForegroundColor(TextColor.Factory.fromString("#D7CCC8"));
    }
}