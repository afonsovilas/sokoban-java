package com.sokoban.states;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.sokoban.*;
import com.sokoban.model.*;
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
        Menu model = getModel();
        switch (key.getKeyType()) {
            case ArrowUp -> model.previousEntry();
            case ArrowDown -> model.nextEntry();
            case Enter -> model.getCurrentCommand().execute(game);
        }


    }
}
