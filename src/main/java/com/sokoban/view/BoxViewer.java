package com.sokoban.view;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.sokoban.model.Box;

public class BoxViewer implements ElementViewer<Box> {
    @Override
    public void draw(Box box, TextGraphics graphics) {
        graphics.setBackgroundColor(TextColor.Factory.fromString("#2C1A0B"));
        if (box.isOnTarget()) {
            graphics.setForegroundColor(TextColor.Factory.fromString("#00FF00"));
        } else {
            graphics.setForegroundColor(TextColor.Factory.fromString("#3399CC"));
        }
        graphics.putString(new TerminalPosition(box.getPosition().getX(), box.getPosition().getY()), "O");
    }
}