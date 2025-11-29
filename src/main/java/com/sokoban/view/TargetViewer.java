package com.sokoban.view;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.sokoban.model.Target;

public class TargetViewer implements ElementViewer<Target> {
    @Override
    public void draw(Target target, TextGraphics graphics) {
        graphics.setBackgroundColor(TextColor.Factory.fromString("#2C1A0B"));
        graphics.setForegroundColor(TextColor.Factory.fromString("#FF7F50"));
        graphics.putString(new TerminalPosition(target.getPosition().getX(), target.getPosition().getY()), "*");
    }
}
