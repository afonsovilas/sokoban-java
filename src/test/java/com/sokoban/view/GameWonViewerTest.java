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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class GameWonViewerTest {
    private Screen screen;
    private TextGraphics graphics;
    private Menu menu;
    private GameWonViewer viewer;

    @BeforeEach
    void setUp() {
        screen = Mockito.mock(Screen.class);
        graphics = Mockito.mock(TextGraphics.class);
        menu = Mockito.mock(Menu.class);

        // Definimos um ecrã fixo para calcularmos as coordenadas esperadas à mão
        // Ecrã 80x24.
        // BOX_WIDTH = 45, BOX_HEIGHT = 15 (constantes na classe)
        when(screen.getTerminalSize()).thenReturn(new TerminalSize(80, 24));

        viewer = new GameWonViewer(menu);
    }

    @Test
    void testDrawBoxPositionAndTitle() {

        when(menu.getNumberEntries()).thenReturn(0);


        viewer.draw(screen, graphics);

        verify(graphics).setBackgroundColor(TextColor.Factory.fromString("#4E342E"));
        verify(graphics).fillRectangle(eq(new TerminalPosition(17, 4)), eq(new TerminalSize(45, 15)), eq(' '));

        verify(graphics).putString(eq(new TerminalPosition(32, 7)), eq("GAME COMPLETED!"));
        verify(graphics).putString(eq(new TerminalPosition(29, 9)), eq("You Passed All Levels"));
    }

    @Test
    void testDrawEntriesLoop() {

        when(menu.getNumberEntries()).thenReturn(2);

        when(menu.getEntryText(0)).thenReturn("Menu");
        when(menu.isSelected(0)).thenReturn(true);

        when(menu.getEntryText(1)).thenReturn("Exit");
        when(menu.isSelected(1)).thenReturn(false);


        viewer.draw(screen, graphics);

        verify(graphics).putString(eq(new TerminalPosition(35, 13)), eq("[ Menu ]"));
        verify(graphics).setBackgroundColor(TextColor.Factory.fromString("#FF9800"));

        verify(graphics).putString(eq(new TerminalPosition(35, 15)), eq("  Exit  "));
    }
}
