package com.sokoban.states;

import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.sokoban.Game;
import com.sokoban.model.Menu;
import com.sokoban.view.ScoreBoardViewer;

import java.io.IOException;
import java.util.Map;

public class ScoreBoardState extends State<Map<Integer, Integer>> {
    private final ScoreBoardViewer viewer;

    public ScoreBoardState(Map<Integer, Integer> scores) {
        super(scores);
        this.viewer = new ScoreBoardViewer(scores);
    }

    @Override
    public void draw(Screen screen, TextGraphics graphics) {
        screen.clear();
        viewer.draw(screen, graphics);
    }

    @Override
    public void step(Game game, KeyStroke key) throws IOException {
        if (key == null) return;

        if (key.getKeyType() == KeyType.Enter || key.getKeyType() == KeyType.Escape) {
            game.setState(new MenuState(new Menu(false)));
        }
    }
}