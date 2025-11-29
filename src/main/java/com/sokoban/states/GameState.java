package com.sokoban.states;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.sokoban.*;
import com.sokoban.controller.MenuState;
import com.sokoban.model.Level;
import com.sokoban.model.LevelLoader;
import com.sokoban.model.Menu;

public class GameState extends State<Level> {
    private int currIndexLevel;

    public GameState(Level level, int currIndexLevel) {
        super(level);
        this.currIndexLevel= currIndexLevel;
    }

    @Override
    public void draw(Screen screen, TextGraphics graphics) {
        screen.clear();
        graphics.setBackgroundColor(TextColor.Factory.fromString("#336699"));
        graphics.fillRectangle(
                new TerminalPosition(0, 0),
                screen.getTerminalSize(),
                ' '
        );
        getModel().draw(graphics);
    }

    @Override
    public void step(Game game, KeyStroke key) {
        if (key == null) return;

        if (key.getKeyType() == KeyType.Character && key.getCharacter() == 'q') {
            game.saveGameState(this);
            game.setState(new MenuState(new Menu()));
            return;
        }

        getModel().processKey(key);

        if (getModel().isFinished()) {
            int nextLevelNumber = currIndexLevel + 1;
            LevelLoader loader = new LevelLoader();
            Level nextLevel = loader.loadLevel(nextLevelNumber);

            if (nextLevel != null) {
                nextLevel.initialize();
                game.setState(new GameState(nextLevel, nextLevelNumber));
            } else {
                System.out.println("YOU WON!");
                game.setState(new MenuState(new Menu()));
            }
        }
    }
}