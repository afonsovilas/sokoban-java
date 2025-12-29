package com.sokoban;

import com.googlecode.lanterna.screen.Screen;
import com.sokoban.states.GameState;
import com.sokoban.states.State;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GameTest {
    private Game game;
    private Screen screenMock;
    private State stateMock;

    @BeforeEach
    void setUp() {
        screenMock = Mockito.mock(Screen.class);
        stateMock = Mockito.mock(State.class);
        game = new Game(screenMock, stateMock);
    }

    @Test
    void setStateShouldUpdateInternalState() throws NoSuchFieldException, IllegalAccessException {
        State newState = Mockito.mock(State.class);

        game.setState(newState);

        Field field = Game.class.getDeclaredField("state");
        field.setAccessible(true);
        State actualState = (State) field.get(game);

        assertEquals(newState, actualState, "O estado do jogo devia ter sido atualizado");
    }

    @Test
    void saveGameStateShouldStoreState() {
        GameState gameStateMock = Mockito.mock(GameState.class);

        game.saveGameState(gameStateMock);

        assertEquals(gameStateMock, game.getSavedGame(), "Devia guardar o jogo corretamente");
    }
}