package com.sokoban.states;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.sokoban.Game;
import com.sokoban.model.Hero;
import com.sokoban.model.Level;
import com.sokoban.model.LevelManager;
import com.sokoban.model.Position;
import net.jqwik.api.*;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class GameStateTest {

    private final Level levelMock = Mockito.mock(Level.class);
    private final LevelManager levelManagerMock = Mockito.mock(LevelManager.class);
    private final Game gameMock = Mockito.mock(Game.class);

    private final GameState state = new GameState(levelMock, 1, levelManagerMock);

    @Test
    void drawShouldRenderLevelAndDelegatesToViewer() throws IOException {
        Screen screenMock = mock(Screen.class);
        TextGraphics graphicsMock = mock(TextGraphics.class);

        when(screenMock.getTerminalSize()).thenReturn(new TerminalSize(50, 20));
        when(levelMock.getWidth()).thenReturn(10);
        when(levelMock.getHeight()).thenReturn(10);
        when(levelMock.getSteps()).thenReturn(0);
        when(levelMock.getLives()).thenReturn(3);
        when(levelMock.getPowerUpTime()).thenReturn(0L);
        when(levelMock.getTargets()).thenReturn(Collections.emptyList());
        when(levelMock.getPortals()).thenReturn(Collections.emptyList());
        when(levelMock.getPowerUps()).thenReturn(Collections.emptyList());
        when(levelMock.getWalls()).thenReturn(Collections.emptyList());
        when(levelMock.getBoxes()).thenReturn(Collections.emptyList());

        Hero heroMock = mock(Hero.class);
        when(heroMock.getPosition()).thenReturn(new Position(5, 5));
        when(levelMock.getHero()).thenReturn(heroMock);
        when(graphicsMock.newTextGraphics(any(), any())).thenReturn(graphicsMock);

        state.draw(screenMock, graphicsMock);

        verify(screenMock).clear();
        verify(levelMock, atLeastOnce()).getSteps();
    }

    @Test
    void restartKeyShouldReloadLevel() {
        state.step(gameMock, new KeyStroke('r', false, false));
        verify(gameMock).setState(any(GameState.class));
    }

    @Test
    void victoryShouldLoadNextLevelAndShowWinMenu() {
        GameState stateLevel0 = new GameState(levelMock, 0, levelManagerMock);

        when(levelMock.isFinished()).thenReturn(true);
        when(levelMock.getSteps()).thenReturn(10); // Para o ScoreManager

        try {
            stateLevel0.step(gameMock, new KeyStroke(KeyType.ArrowRight));
        } catch (Exception e) {
        }

        verify(levelManagerMock).unlockNextLevel(0);
        verify(gameMock).setState(any(WinMenuState.class));
    }

    @Property
    void pauseKeyShouldSaveAndSwitchToMenu(@ForAll("pauseKeys") KeyStroke key) {
        clearInvocations(gameMock, levelMock);

        state.step(gameMock, key);

        verify(gameMock).saveGameState(state);
        verify(gameMock).setState(any(MenuState.class));

        verify(levelMock, never()).processKey(any());
    }

    @Property
    void gamePlayKeysShouldDelegateToLevel(@ForAll("gamePlayKeys") KeyStroke key) {
        clearInvocations(gameMock, levelMock);

        when(levelMock.isFinished()).thenReturn(false);

        state.step(gameMock, key);

        verify(levelMock).processKey(key);

        verify(gameMock, never()).saveGameState(any());
        verify(gameMock, never()).setState(any());
    }

    @Provide
    Arbitrary<KeyStroke> pauseKeys() {
        return Arbitraries.of('q', 'Q').map(c -> new KeyStroke(c, false, false));
    }

    @Provide
    Arbitrary<KeyStroke> gamePlayKeys() {
        return Arbitraries.oneOf(
                Arbitraries.chars().alpha()
                        .filter(c -> c != 'q' && c != 'Q' && c != 'r' && c != 'R')
                        .map(c -> new KeyStroke(c, false, false)),
                Arbitraries.of(
                        new KeyStroke(KeyType.ArrowUp),
                        new KeyStroke(KeyType.ArrowDown),
                        new KeyStroke(KeyType.ArrowLeft),
                        new KeyStroke(KeyType.ArrowRight),
                        new KeyStroke(KeyType.Enter)
                )
        );
    }
}