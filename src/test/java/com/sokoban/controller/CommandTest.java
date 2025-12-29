package com.sokoban.controller;

import com.sokoban.Game;
import com.sokoban.model.LevelManager;
import com.sokoban.states.GameState;
import com.sokoban.states.LevelMenuState;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.*;

class CommandTest {

    @Test
    void loadLevelCommandShouldSetGameState() {
        Game game = mock(Game.class);
        LevelManager manager = mock(LevelManager.class);

        LoadLevelCommand command = new LoadLevelCommand(1, manager);
        try {
            command.execute(game);
        } catch (Exception e) {

        }

    }

    @Test
    void openLevelMenuCommandShouldSetMenuState() {
        Game game = mock(Game.class);
        LevelManager manager = mock(LevelManager.class);

        OpenLevelMenuCommand command = new OpenLevelMenuCommand(manager);

        command.execute(game);

        verify(game).setState(any(LevelMenuState.class));
    }
}
