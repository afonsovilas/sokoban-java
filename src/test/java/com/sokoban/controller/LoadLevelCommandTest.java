package com.sokoban.controller;

import com.sokoban.Game;
import com.sokoban.model.*;
import com.sokoban.states.GameState;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class LoadLevelCommandTest {

    @Test
    void executeShouldSetGameStateSafe() {
        Game game = mock(Game.class);
        LevelManager manager = mock(LevelManager.class);
        LevelLoader loaderMock = mock(LevelLoader.class);

        Level levelMock = mock(Level.class);
        Hero heroMock = mock(Hero.class);

        when(levelMock.getHero()).thenReturn(heroMock);
        when(heroMock.getPosition()).thenReturn(new Position(1, 1));

        when(loaderMock.loadLevel(1)).thenReturn(levelMock);

        LoadLevelCommand command = new LoadLevelCommand(1, manager);
        command.setLevelLoader(loaderMock);

        command.execute(game);

        verify(game).setState(any(GameState.class));
    }
}
