package com.sokoban.view;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.sokoban.model.Box;

public class BoxViewer implements ElementViewer<Box> {
    @Override
    public void draw(Box box, TextGraphics graphics) {
        graphics.setBackgroundColor(TextColor.Factory.fromString("#3E2723"));
        if (box.isOnTarget()) {
            graphics.setForegroundColor(TextColor.Factory.fromString("#76FF03"));
        } else {
            graphics.setForegroundColor(TextColor.Factory.fromString("#FF6D00"));
        }
        graphics.putString(new TerminalPosition(box.getPosition().getX(), box.getPosition().getY()), "\u25A3");
    }
}