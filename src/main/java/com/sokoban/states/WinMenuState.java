package com.sokoban.states;

import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.sokoban.Game;
import com.sokoban.model.Menu;
import com.sokoban.view.WinMenuViewer;

import java.io.IOException;

public class WinMenuState extends State<Menu> {
    private final WinMenuViewer viewer;

    public WinMenuState(Menu model) {
        super(model);
        this.viewer = new WinMenuViewer(model);
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