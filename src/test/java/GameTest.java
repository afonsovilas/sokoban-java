package com.sokoban;

import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.sokoban.states.GameState;
import com.sokoban.states.State;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class GameTest {
    private Game game;
    private Screen screen;

    @BeforeEach
    void setUp() {
        screen = mock(Screen.class);
        game = new Game(screen);
    }

    @Test
    void testSetState() {
        State state = mock(State.class);
        game.setState(state);

    }

    @Test
    void testSaveAndGetGameState() {
        GameState gameState = mock(GameState.class);

        game.saveGameState(gameState);

        assert(game.getSavedGame() == gameState);
    }

    @Test
    void testRunLoop() throws IOException {
        State state = mock(State.class);
        game.setState(state);

        when(screen.newTextGraphics()).thenReturn(mock(TextGraphics.class));
        when(screen.readInput()).thenReturn(new KeyStroke(KeyType.ArrowUp));

        doAnswer(invocation -> {
            game.setState(null);
            return null;
        }).when(state).step(any(Game.class), any(KeyStroke.class));

        game.run();

        verify(state).draw(any(Screen.class), any(TextGraphics.class));
        verify(screen).readInput();
        verify(state).step(eq(game), any(KeyStroke.class));
        verify(screen).close();
    }
}
