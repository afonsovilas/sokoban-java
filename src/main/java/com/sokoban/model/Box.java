package com.sokoban.model;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;

public class Box extends Element {
    private boolean onTarget;

    public Box(int x, int y) {
        super(x, y);
        this.onTarget = false;
    }

    public void setOnTarget(boolean onTarget) {
        this.onTarget = onTarget;
    }

    public boolean isOnTarget() {
        return onTarget;
    }

    @Override
    public void draw(TextGraphics graphics) {
        if (onTarget) {
            graphics.setForegroundColor(TextColor.Factory.fromString("#00FF00"));
        } else {
            graphics.setForegroundColor(TextColor.Factory.fromString("#3399CC"));
        }
        graphics.putString(new TerminalPosition(position.getX(), position.getY()), "O");
    }
}
