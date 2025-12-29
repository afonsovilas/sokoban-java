package com.sokoban.states;

import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.sokoban.Game;
import com.sokoban.controller.Command;
import com.sokoban.model.Menu;
import net.jqwik.api.*;
import org.mockito.Mockito;

import java.io.IOException;

import static org.mockito.Mockito.*;

class WinMenuStateTest {

    private final Menu menuMock = Mockito.mock(Menu.class);
    private final Game gameMock = Mockito.mock(Game.class);
    private final Command commandMock = Mockito.mock(Command.class);

    private final WinMenuState state = new WinMenuState(menuMock);

    WinMenuStateTest() {
        when(menuMock.getCurrentCommand()).thenReturn(commandMock);
    }

    @Property
    void navigationKeysShouldChangeSelection(@ForAll("navigationKeys") KeyStroke key) throws IOException {
        clearInvocations(menuMock, gameMock);

        state.step(gameMock, key);

        if (key.getKeyType() == KeyType.ArrowUp) {
            verify(menuMock).previousEntry();
        }
        if (key.getKeyType() == KeyType.ArrowDown) {
            verify(menuMock).nextEntry();
        }
    }

    @Property
    void enterKeyShouldExecuteCommand(@ForAll("enterKey") KeyStroke key) throws IOException {
        clearInvocations(menuMock, gameMock, commandMock);

        state.step(gameMock, key);

        verify(menuMock).getCurrentCommand();
        verify(commandMock).execute(gameMock);
    }

    @Provide
    Arbitrary<KeyStroke> navigationKeys() {
        return Arbitraries.of(new KeyStroke(KeyType.ArrowUp), new KeyStroke(KeyType.ArrowDown));
    }

    @Provide
    Arbitrary<KeyStroke> enterKey() {
        return Arbitraries.of(new KeyStroke(KeyType.Enter));
    }
}