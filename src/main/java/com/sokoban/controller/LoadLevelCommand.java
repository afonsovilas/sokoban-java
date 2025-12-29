package com.sokoban.controller;

import com.sokoban.Game;
import com.sokoban.model.LevelLoader;
import com.sokoban.model.LevelManager;
import com.sokoban.states.GameState;

import java.io.IOException;

public class LoadLevelCommand implements Command {
    private final int levelNumber;
    private final LevelManager levelManager;
    private LevelLoader levelLoader;


    public LoadLevelCommand(int levelNumber, LevelManager levelManager) {
        this.levelNumber = levelNumber;
        this.levelManager = levelManager;
        this.levelLoader = new LevelLoader();
    }

    public void setLevelLoader(LevelLoader levelLoader) {
        this.levelLoader = levelLoader;
    }

    @Override
    public void execute(Game game) {

        game.setState(new GameState(
                levelLoader.loadLevel(levelNumber),
                levelNumber,
                levelManager
        ));

    }
}