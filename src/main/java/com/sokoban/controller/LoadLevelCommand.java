package com.sokoban.controller;

import com.sokoban.Game;
import com.sokoban.model.LevelLoader;
import com.sokoban.model.LevelManager;
import com.sokoban.states.GameState;

import java.io.IOException;

public class LoadLevelCommand implements Command {
    private final int levelNumber;
    private final LevelManager levelManager;


    public LoadLevelCommand(int levelNumber, LevelManager levelManager) {
        this.levelNumber = levelNumber;
        this.levelManager = levelManager;
    }

    @Override
    public void execute(Game game) {

        game.setState(new GameState(
                new LevelLoader().loadLevel(levelNumber),
                levelNumber,
                levelManager
        ));

    }
}