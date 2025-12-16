package com.sokoban.controller;

import com.sokoban.Game;
import com.sokoban.controller.Command;
import com.sokoban.model.LevelManager;
import com.sokoban.model.LevelMenu;
import com.sokoban.states.LevelMenuState;

public class OpenLevelMenuCommand implements Command {
    private final LevelManager levelManager;

    public OpenLevelMenuCommand(LevelManager levelManager) {
        this.levelManager = levelManager;
    }

    @Override
    public void execute(Game game) {
        game.setState(new LevelMenuState(new LevelMenu(levelManager), levelManager));
    }
}
