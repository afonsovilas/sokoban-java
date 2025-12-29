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

class MenuViewerTest {
    private Screen screen;
    private TextGraphics graphics;
    private Menu menu;
    private MenuViewer viewer;

    @BeforeEach
    void setUp() {
        screen = Mockito.mock(Screen.class);
        graphics = Mockito.mock(TextGraphics.class);
        menu = Mockito.mock(Menu.class);

        when(screen.getTerminalSize()).thenReturn(new TerminalSize(80, 24));
        viewer = new MenuViewer(menu);
    }

    @Test
    void testDrawTitleExactPosition() {
        when(menu.getNumberEntries()).thenReturn(0);
        viewer.draw(screen, graphics);

        verify(graphics).putString(
                eq(new TerminalPosition(34, 6)),
                eq("SOKOBAN GAME")
        );
    }

    @Test
    void testDrawEntriesExactPositionAndFormatting() {

        when(menu.getNumberEntries()).thenReturn(3);

        when(menu.getEntryText(0)).thenReturn("Play");
        when(menu.isSelected(0)).thenReturn(true);

        when(menu.getEntryText(1)).thenReturn("Exit");
        when(menu.isSelected(1)).thenReturn(false);

        when(menu.getEntryText(2)).thenReturn("Help");

        viewer.draw(screen, graphics);

        verify(graphics).putString(
                eq(new TerminalPosition(35, 10)),
                eq("-> Play <-")
        );

        verify(graphics).putString(
                eq(new TerminalPosition(35, 11)),
                eq("   Exit   ")
        );

        verify(graphics).setForegroundColor(TextColor.Factory.fromString("#FF9800"));
        verify(graphics, times(2)).setForegroundColor(TextColor.Factory.fromString("#D7CCC8"));
    }
}
