package com.sokoban.view;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.Screen;
import com.sokoban.model.LevelMenu;
import com.sokoban.model.Menu;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

// ADICIONEI ESTES IMPORTS QUE FALTAVAM
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class MenusViewTest {
    private Screen screen;
    private TextGraphics graphics;
    private TerminalSize terminalSize;

    @BeforeEach
    void setUp() {
        screen = mock(Screen.class);
        graphics = mock(TextGraphics.class);
        terminalSize = new TerminalSize(80, 24);
        when(screen.getTerminalSize()).thenReturn(terminalSize);
    }

    @Test
    void testLevelMenuGridMathStrict() {
        when(screen.getTerminalSize()).thenReturn(new TerminalSize(80, 24));

        LevelMenu model = mock(LevelMenu.class);
        when(model.getNumberLevels()).thenReturn(3);
        when(model.getEntryTitle(anyInt())).thenReturn("Lvl");
        when(model.isUnlocked(anyInt())).thenReturn(true);

        LevelMenuViewer viewer = new LevelMenuViewer(model);
        viewer.draw(screen, graphics);

        verify(graphics).fillRectangle(eq(new TerminalPosition(15, 8)), eq(new TerminalSize(22, 5)), eq(' '));
        verify(graphics).fillRectangle(eq(new TerminalPosition(43, 8)), eq(new TerminalSize(22, 5)), eq(' '));
        verify(graphics).fillRectangle(eq(new TerminalPosition(15, 15)), eq(new TerminalSize(22, 5)), eq(' '));
    }

    @Test
    void testLevelMenuLockedItem() {
        LevelMenu model = mock(LevelMenu.class);
        when(model.getNumberLevels()).thenReturn(1);
        when(model.isUnlocked(0)).thenReturn(false);
        when(model.getEntryTitle(0)).thenReturn("Level 2");

        LevelMenuViewer viewer = new LevelMenuViewer(model);
        viewer.draw(screen, graphics);

        verify(graphics).setBackgroundColor(TextColor.Factory.fromString("#5D4037"));
        verify(graphics).setForegroundColor(TextColor.Factory.fromString("#8D6E63"));

        verify(graphics).putString(any(TerminalPosition.class), contains("Level 2 [Locked]"));
    }

    @Test
    void testScoreBoardEmpty() {
        ScoreBoardViewer viewer = new ScoreBoardViewer(Map.of());

        viewer.draw(screen, graphics);
        verify(graphics).putString(any(TerminalPosition.class), eq("No scores yet!"));
    }

    @Test
    void testScoreBoardPopulated() {
        Map<Integer, Integer> scores = Map.of(1, 100);
        ScoreBoardViewer viewer = new ScoreBoardViewer(scores);

        viewer.draw(screen, graphics);

        String expectedLine = "Level 1 ...... 100 steps";
        verify(graphics).putString(any(TerminalPosition.class), eq(expectedLine));
    }

    @Test
    void testGameWonViewerLayout() {
        Menu menu = mock(Menu.class);
        when(menu.getNumberEntries()).thenReturn(1);
        when(menu.getEntryText(0)).thenReturn("Exit");
        when(menu.isSelected(0)).thenReturn(true);

        GameWonViewer viewer = new GameWonViewer(menu);
        viewer.draw(screen, graphics);

        verify(graphics).setForegroundColor(TextColor.Factory.fromString("#FFD54F"));
        verify(graphics).putString(any(TerminalPosition.class), eq("GAME COMPLETED!"));

        verify(graphics).putString(any(TerminalPosition.class), eq("[ Exit ]"));
    }
}