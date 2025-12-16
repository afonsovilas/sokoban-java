package com.sokoban.states;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.sokoban.*;
import com.sokoban.model.Level;
import com.sokoban.model.LevelLoader;
import com.sokoban.model.LevelManager;
import com.sokoban.model.Menu;
import com.sokoban.utils.ScoreManager;
import com.sokoban.view.LevelViewer;
import com.sokoban.model.MenuFactory;

import java.io.IOException;

public class GameState extends State<Level> {
    private int currIndexLevel;
    private final LevelViewer levelviewer;
    private final LevelManager levelManager;

    public GameState(Level level, int currIndexLevel, LevelManager levelManager) {
        super(level);
        this.currIndexLevel= currIndexLevel;
        this.levelviewer = new LevelViewer(level);
        this.levelManager = levelManager;
    }

    @Override
    public void draw(Screen screen, TextGraphics graphics) {
        screen.clear();
        graphics.setBackgroundColor(TextColor.Factory.fromString("#3E2723"));
        graphics.fillRectangle(
                new TerminalPosition(0, 0),
                screen.getTerminalSize(),
                ' '
        );
        levelviewer.draw(
                graphics,
                screen.getTerminalSize().getColumns(),
                screen.getTerminalSize().getRows()
        );
    }

    @Override
    public void step(Game game, KeyStroke key) {
        if (key == null) return;

        if (key.getKeyType() == KeyType.Character && key.getCharacter() == 'q') {
            game.saveGameState(this);
            game.setState(new MenuState(MenuFactory.createPauseMenu(levelManager, currIndexLevel)));
            return;
        }

        if (key.getKeyType() == KeyType.Character && (key.getCharacter() == 'r' || key.getCharacter() == 'R')) {
            restartLevel(game);
            return;
        }

        getModel().processKey(key);
        if (getModel().isFinished()) {
            handleVictory(game);
        }


    }
    private void restartLevel(Game game) {

        Level currentLevel = new LevelLoader().loadLevel(currIndexLevel);
        currentLevel.initialize();
        game.setState(new GameState(currentLevel, currIndexLevel, levelManager));

    }

    private void handleVictory(Game game) {
        ScoreManager.saveScore(currIndexLevel, getModel().getSteps());
        levelManager.unlockNextLevel(currIndexLevel);

        int nextLevelNumber = currIndexLevel + 1;

        Level nextLevel = new LevelLoader().loadLevel(nextLevelNumber);
        if (nextLevel != null) {
            nextLevel.initialize();
            game.setState(new GameState(nextLevel, nextLevelNumber, levelManager));
        } else {
            System.out.println("YOU WON THE GAME!");
            game.setState(new MenuState(MenuFactory.createMainMenu(levelManager)));
        }

    }
}