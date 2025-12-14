package com.sokoban.states;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.sokoban.*;
import com.sokoban.model.Level;
import com.sokoban.model.LevelLoader;
import com.sokoban.model.Menu;
import com.sokoban.utils.ScoreManager;
import com.sokoban.view.MenuViewer;

import java.io.IOException;

public class MenuState extends State<Menu> {

    private final MenuViewer viewer;

    public MenuState(Menu model) {
        super(model);
        this.viewer = new MenuViewer(model);
    }

    @Override
    public void draw(Screen screen, TextGraphics graphics) {
        screen.clear();
        viewer.draw(screen, graphics);
    }

    @Override
    public void step(Game game, KeyStroke key) throws IOException {
        if (key == null) return;

        if (key.getKeyType() == KeyType.ArrowUp) {
            getModel().previousEntry();
        }
        if (key.getKeyType() == KeyType.ArrowDown) {
            getModel().nextEntry();
        }
        if (key.getKeyType() == KeyType.Enter) {
            if (getModel().isSelectedExit()) {
                game.setState(null);
            }
            if (getModel().isSelectedStart()) {

                game.saveGameState(null);

                LevelLoader loader = new LevelLoader();
                Level firstLevel = loader.loadLevel(1);

                if (firstLevel != null) {

                    firstLevel.initialize();
                    game.setState(new GameState(firstLevel, 1));
                }
            }

            if (getModel().isSelectedResume()) {

                GameState saved = game.getSavedGame();

                if (saved != null) {
                    game.setState(saved);
                }
            }

            if (getModel().isSelectedScoreBoard()) {
                game.setState(new ScoreBoardState(ScoreManager.loadScores()));
            }
        }
    }
}
