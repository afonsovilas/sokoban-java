package com.sokoban.view;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.sokoban.model.PowerUp;

public class PowerUpViewer implements ElementViewer<PowerUp> {

    @Override
    public void draw(PowerUp powerUp, TextGraphics graphics) {
        graphics.setBackgroundColor(TextColor.Factory.fromString("#3E2723"));
        graphics.setForegroundColor(TextColor.Factory.fromString("#FFD700"));
        graphics.putString(powerUp.getPosition().getX(), powerUp.getPosition().getY(), "\u26A1");
    }

}
