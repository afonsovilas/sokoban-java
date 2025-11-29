package com.sokoban.view;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.Screen;
import com.sokoban.model.Menu;
import com.sokoban.view.MenuViewer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;

class MenuViewerTest {
    private Screen screen;
    private TextGraphics graphics;
    private MenuViewer viewer;
    private Menu menu;

    @BeforeEach
    void setUp() {

        screen = Mockito.mock(Screen.class);
        graphics = Mockito.mock(TextGraphics.class);
        when(screen.getTerminalSize()).thenReturn(new TerminalSize(40, 20));

        menu = new Menu();
        viewer = new MenuViewer(menu);
    }

    @Test
    void testDrawBackgroundAndTitle() {

        viewer.draw(screen, graphics);
        verify(graphics).setBackgroundColor(TextColor.Factory.fromString("#336699"));
        verify(graphics).fillRectangle(eq(new TerminalPosition(0, 0)), any(TerminalSize.class), eq(' '));
        verify(graphics).putString(any(TerminalPosition.class), eq("SOKOBAN GAME"));
    }

    @Test
    void testDrawSelectedOption() {

        viewer.draw(screen, graphics);
        verify(graphics, times(3)).setForegroundColor(TextColor.ANSI.WHITE);
        verify(graphics).putString(any(TerminalPosition.class), contains("-> Start Game <-"));
    }

    @Test
    void testDrawUnselectedOption() {

        viewer.draw(screen, graphics);
        verify(graphics).putString(any(TerminalPosition.class), eq("   Resume   "));
        verify(graphics).putString(any(TerminalPosition.class), eq("   Exit   "));
    }
}