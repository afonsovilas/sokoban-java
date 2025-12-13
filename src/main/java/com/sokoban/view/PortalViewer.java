package com.sokoban.view;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.sokoban.model.Portal;

public class PortalViewer implements ElementViewer<Portal> {
    @Override
    public void draw(Portal portal, TextGraphics graphics) {
        graphics.setBackgroundColor(TextColor.Factory.fromString("#2C1A0B"));
        graphics.setForegroundColor(TextColor.Factory.fromString("#9932CC"));
        graphics.enableModifiers(SGR.BOLD);
        graphics.putString(new TerminalPosition(portal.getPosition().getX(), portal.getPosition().getY()), "P");
    }
}