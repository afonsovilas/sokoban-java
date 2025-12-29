package com.sokoban.states;

import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.sokoban.Game;
import com.sokoban.controller.Command;
import com.sokoban.model.LevelManager;
import com.sokoban.model.LevelMenu;
import net.jqwik.api.*;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.mockito.Mockito.*;

class LevelMenuStateTest {

    private final LevelMenu menuMock = Mockito.mock(LevelMenu.class);
    private final LevelManager levelManagerMock = Mockito.mock(LevelManager.class);
    private final Game gameMock = Mockito.mock(Game.class);
    private final LevelMenuState state = new LevelMenuState(menuMock, levelManagerMock);


    @Test
    void stepShouldExecuteCommandWhenUnlocked() throws IOException {
        when(menuMock.getSelectedIndex()).thenReturn(0);
        when(menuMock.isUnlocked(0)).thenReturn(true);
        Command commandMock = mock(Command.class);
        when(menuMock.getSelectedCommand()).thenReturn(commandMock);

        state.step(gameMock, new KeyStroke(KeyType.Enter));

        verify(commandMock).execute(gameMock);
    }

    @Test
    void stepShouldNotExecuteCommandWhenLocked() throws IOException {
        when(menuMock.getSelectedIndex()).thenReturn(1);
        when(menuMock.isUnlocked(1)).thenReturn(false);

        state.step(gameMock, new KeyStroke(KeyType.Enter));

        verify(menuMock, never()).getSelectedCommand();
    }


    @Property
    void navigationKeysShouldAlwaysDelegateToModel(@ForAll("navigationKeys") KeyStroke key) throws IOException {
        clearInvocations(menuMock, gameMock);

        state.step(gameMock, key);

        if (key.getKeyType() == KeyType.ArrowUp) verify(menuMock).moveUp();
        if (key.getKeyType() == KeyType.ArrowDown) verify(menuMock).moveDown();
        if (key.getKeyType() == KeyType.ArrowLeft) verify(menuMock).moveLeft();
        if (key.getKeyType() == KeyType.ArrowRight) verify(menuMock).moveRight();
    }

    @Property
    void quitKeyShouldAlwaysReturnToMainMenu(@ForAll("quitKeys") KeyStroke key) throws IOException {
        clearInvocations(menuMock, gameMock);

        state.step(gameMock, key);

        ArgumentCaptor<State> stateCaptor = ArgumentCaptor.forClass(State.class);
        verify(gameMock).setState(stateCaptor.capture());

        assertInstanceOf(MenuState.class, stateCaptor.getValue());
    }

    @Property
    void randomKeysShouldBeIgnored(@ForAll("ignoredKeys") KeyStroke key) throws IOException {
        clearInvocations(menuMock, gameMock);

        state.step(gameMock, key);

        verify(menuMock, never()).moveUp();
        verify(menuMock, never()).moveDown();
        verify(menuMock, never()).moveLeft();
        verify(menuMock, never()).moveRight();
        verify(gameMock, never()).setState(any());
    }

    @Provide
    Arbitrary<KeyStroke> navigationKeys() {
        return Arbitraries.of(
                new KeyStroke(KeyType.ArrowUp),
                new KeyStroke(KeyType.ArrowDown),
                new KeyStroke(KeyType.ArrowLeft),
                new KeyStroke(KeyType.ArrowRight)
        );
    }

    @Provide
    Arbitrary<KeyStroke> quitKeys() {
        return Arbitraries.of('q', 'Q').map(c -> new KeyStroke(c, false, false));
    }

    @Provide
    Arbitrary<KeyStroke> ignoredKeys() {
        return Arbitraries.chars().alpha().filter(c -> c != 'q' && c != 'Q')
                .map(c -> new KeyStroke(c, false, false));
    }
}