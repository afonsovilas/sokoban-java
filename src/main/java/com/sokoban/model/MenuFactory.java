package com.sokoban.model;

import com.sokoban.Game;
import com.sokoban.controller.Command;
import com.sokoban.controller.OpenLevelMenuCommand;
import com.sokoban.controller.OpenScoreBoardCommand;
import com.sokoban.model.LevelLoader;
import com.sokoban.model.LevelManager;
import com.sokoban.model.Menu;
import com.sokoban.states.GameState;
import com.sokoban.states.MenuState;

import java.io.IOException;
import java.util.Arrays;

public class MenuFactory {

    public static Menu createMainMenu(LevelManager levelManager) {
        Command startCommand = game -> startGame(game, 1, levelManager);
        Command levelsCommand = new OpenLevelMenuCommand(levelManager);
        Command scoreBoardCommand = new OpenScoreBoardCommand(levelManager);
        Command exitCommand = game -> game.setState(null);

        return new Menu(Arrays.asList(
                new Menu.MenuEntry("Start Game", startCommand),
                new Menu.MenuEntry("Levels", levelsCommand),
                new Menu.MenuEntry("ScoreBoard", scoreBoardCommand),
                new Menu.MenuEntry("Exit", exitCommand)
        ));
    }

    public static Menu createPauseMenu(LevelManager levelManager, int currentLevelIndex) {
        Command resumeCommand = game -> {
            if (game.getSavedGame() != null) {
                game.setState(game.getSavedGame());
            }
        };

        Command restartCommand = game -> startGame(game, currentLevelIndex, levelManager);

        Command levelsCommand = new OpenLevelMenuCommand(levelManager);
        Command scoreBoardCommand = new OpenScoreBoardCommand(levelManager);

        Command exitToMainCommand = game ->
                game.setState(new MenuState(createMainMenu(levelManager)));

        return new Menu(Arrays.asList(
                new Menu.MenuEntry("Resume", resumeCommand),
                new Menu.MenuEntry("Restart", restartCommand),
                new Menu.MenuEntry("Levels",levelsCommand),
                new Menu.MenuEntry("ScoreBoard", scoreBoardCommand),
                new Menu.MenuEntry("Exit to Menu", exitToMainCommand)
        ));
    }

    private static void startGame(Game game, int level, LevelManager manager) {
        game.setState(new GameState(new LevelLoader().loadLevel(level), level, manager));

    }
}