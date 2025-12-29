package com.sokoban.states;

import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.sokoban.Game;
import com.sokoban.model.LevelManager;
import net.jqwik.api.*;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ScoreBoardStateTest {

    private final Game gameMock = Mockito.mock(Game.class);
    private final LevelManager levelManagerMock = Mockito.mock(LevelManager.class);
    private final ScoreBoardState state = new ScoreBoardState(new HashMap<>(), levelManagerMock);

    @Property
    void quitKeysShouldReturnToMainMenu(@ForAll("quitKeys") KeyStroke key) throws IOException {
        clearInvocations(gameMock);

        state.step(gameMock, key);

        ArgumentCaptor<State> stateCaptor = ArgumentCaptor.forClass(State.class);
        verify(gameMock).setState(stateCaptor.capture());

        assertInstanceOf(MenuState.class, stateCaptor.getValue());
    }

    @Property
    void ignoredKeysShouldDoNothing(@ForAll("ignoredKeys") KeyStroke key) throws IOException {
        clearInvocations(gameMock);

        state.step(gameMock, key);

        verify(gameMock, never()).setState(any());
    }

    @Provide
    Arbitrary<KeyStroke> quitKeys() {
        return Arbitraries.of('q', 'Q').map(c -> new KeyStroke(c, false, false));
    }

    @Provide
    Arbitrary<KeyStroke> ignoredKeys() {
        return Arbitraries.oneOf(
                Arbitraries.chars().alpha()
                        .filter(c -> c != 'q' && c != 'Q')
                        .map(c -> new KeyStroke(c, false, false)),
                Arbitraries.of(
                        new KeyStroke(KeyType.ArrowUp),
                        new KeyStroke(KeyType.ArrowDown),
                        new KeyStroke(KeyType.Enter),
                        new KeyStroke(KeyType.Escape)
                )
        );
    }
}