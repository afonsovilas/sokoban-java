package com.sokoban.controller;

import com.sokoban.Game;
import com.sokoban.model.LevelLoader;
import com.sokoban.model.LevelManager;
import com.sokoban.states.GameState;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class LoadLevelCommandTest {

    @Test
    void executeShouldSetGameStateSafe() {
        Game game = mock(Game.class);
        LevelManager manager = mock(LevelManager.class);
        LevelLoader loaderMock = mock(LevelLoader.class);

        LoadLevelCommand command = new LoadLevelCommand(1, manager);
        command.setLevelLoader(loaderMock);

        command.execute(game);

        verify(game).setState(any(GameState.class));
    }
}
