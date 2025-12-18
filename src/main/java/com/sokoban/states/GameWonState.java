package com.sokoban.states;

import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.screen.Screen;
import com.sokoban.Game;
import com.sokoban.model.Menu;
import com.sokoban.view.GameWonViewer;

import java.io.IOException;

public class GameWonState extends State<Menu> {
    private final GameWonViewer viewer;

    public GameWonState(Menu model) {
        super(model);
        this.viewer = new GameWonViewer(model);
    }

    @Override
    public void draw(Screen screen, TextGraphics graphics) {
        screen.clear();
        viewer.draw(screen, graphics);
    }

    @Override
    public void step(Game game, KeyStroke key) throws IOException {
        if (key == null) return;
        Menu model = getModel();

        switch (key.getKeyType()) {
            case ArrowUp -> model.previousEntry();
            case ArrowDown -> model.nextEntry();
            case Enter -> model.getCurrentCommand().execute(game);
        }
    }
}
