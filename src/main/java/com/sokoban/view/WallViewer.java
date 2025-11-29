package com.sokoban.view;


import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.sokoban.model.Wall;

public class WallViewer implements ElementViewer<Wall> {
    @Override
    public void draw(Wall wall, TextGraphics graphics) {
        graphics.setBackgroundColor(TextColor.Factory.fromString("#2C1A0B"));
        graphics.setForegroundColor(TextColor.Factory.fromString("#8B4513"));
        graphics.putString(new TerminalPosition(wall.getPosition().getX(), wall.getPosition().getY()), "#");
    }
}
