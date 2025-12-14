package com.sokoban.view;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.sokoban.model.Portal;

public class PortalViewer implements ElementViewer<Portal> {
    @Override
    public void draw(Portal portal, TextGraphics graphics) {
        graphics.setBackgroundColor(TextColor.Factory.fromString("#3E2723"));
        graphics.setForegroundColor(TextColor.Factory.fromString("#B388FF"));
        graphics.enableModifiers(SGR.BOLD);
        graphics.putString(new TerminalPosition(portal.getPosition().getX(), portal.getPosition().getY()), "\u25CE");
    }
}