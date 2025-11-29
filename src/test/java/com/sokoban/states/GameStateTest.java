package com.sokoban.states;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.sokoban.Game;
import com.sokoban.states.GameState;
import com.sokoban.model.Level;
import com.sokoban.states.MenuState;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;
import static org.mockito.Mockito.atLeastOnce;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class GameStateTest {

    private GameState state;
    private Level level;
    private Game game;
    private Screen screen;
    private TextGraphics graphics;

    @BeforeEach
    void setUp() {
        level = mock(Level.class);
        game = mock(Game.class);
        screen = mock(Screen.class);
        graphics = mock(TextGraphics.class);

        when(level.getWidth()).thenReturn(10);
        when(level.getHeight()).thenReturn(10);
        when(level.getWalls()).thenReturn(new ArrayList<>());
        when(level.getBoxes()).thenReturn(new ArrayList<>());
        when(level.getTargets()).thenReturn(new ArrayList<>());
        when(screen.getTerminalSize()).thenReturn(new TerminalSize(50, 50));

        state = new GameState(level, 1);
    }

    @Test
    void testDraw() {
        state.draw(screen, graphics);

        verify(screen).clear();
        verify(graphics, atLeastOnce()).setBackgroundColor(any());
        verify(graphics, atLeastOnce()).fillRectangle(any(), any(), anyChar());
    }

    @Test
    void testStepNullKey() {
        state.step(game, null);
        verify(level, never()).processKey(any());
    }

    @Test
    void testStepNormalKey() {

        when(level.isFinished()).thenReturn(false);
        state.step(game, new KeyStroke(KeyType.ArrowRight));
        verify(level).processKey(any(KeyStroke.class));
        verify(game, never()).setState(any());
    }

    @Test
    void testQuitToMenu() {
        state.step(game, new KeyStroke('q', false, false));
        verify(game).saveGameState(state);
        verify(game).setState(any(MenuState.class));
    }

    @Test
    void testStepLevelFinishedNextLevelExists() {

        when(level.isFinished()).thenReturn(true);
        state.step(game, new KeyStroke(KeyType.ArrowRight));
        verify(game).setState(any(GameState.class));
    }

    @Test
    void testStepLevelFinishedGameCompleted() {

        state = new GameState(level, 99);
        when(level.isFinished()).thenReturn(true);
        state.step(game, new KeyStroke(KeyType.ArrowRight));
        verify(game).setState(any(MenuState.class));
    }

}
