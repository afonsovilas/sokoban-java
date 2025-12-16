package com.sokoban.states;

import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.screen.Screen;
import com.sokoban.Game;
import com.sokoban.model.LevelManager;
import com.sokoban.model.LevelMenu;
import com.sokoban.model.MenuFactory;
import com.sokoban.view.LevelMenuViewer;

import java.io.IOException;

public class LevelMenuState extends State<LevelMenu> {
    private final LevelMenuViewer viewer;
    private final LevelManager levelManager;

    public LevelMenuState(LevelMenu model, LevelManager levelManager) {
        super(model);
        this.levelManager = levelManager;
        this.viewer = new LevelMenuViewer(model);
    }

    @Override
    public void draw(Screen screen, TextGraphics graphics) {
        screen.clear();
        viewer.draw(screen, graphics);
    }

    @Override
    public void step(Game game, KeyStroke key) throws IOException {
        if (key == null) return;
        LevelMenu model = getModel();

        switch (key.getKeyType()) {
            case ArrowUp    -> model.moveUp();
            case ArrowDown  -> model.moveDown();
            case ArrowLeft  -> model.moveLeft();
            case ArrowRight -> model.moveRight();

            case Enter -> {
                if (model.isUnlocked(model.getSelectedIndex())) {

                    model.getSelectedCommand().execute(game);
                }
            }

            case Character -> {
                if (key.getCharacter() == 'q' || key.getCharacter() == 'Q') {

                    game.setState(new MenuState(MenuFactory.createMainMenu(levelManager)));
                }
            }
        }
    }
}