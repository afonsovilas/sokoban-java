package com.sokoban.view;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.sokoban.model.Hero;

public class HeroViewer implements ElementViewer<Hero> {
    @Override
    public void draw(Hero hero, TextGraphics graphics) {
        graphics.setBackgroundColor(TextColor.Factory.fromString("#3E2723"));
        graphics.setForegroundColor(TextColor.Factory.fromString("#FFD700"));
        graphics.enableModifiers(SGR.BOLD);
        graphics.putString(new TerminalPosition(hero.getPosition().getX(), hero.getPosition().getY()), "@");
    }
}
