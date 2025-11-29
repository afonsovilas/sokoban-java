package com.sokoban.view;
import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.Screen;
import com.sokoban.model.Menu;


public class MenuViewer {
    private final Menu menu;

    public MenuViewer(Menu menu) {
        this.menu = menu;
    }

    public void draw(Screen screen, TextGraphics graphics) {

        graphics.setBackgroundColor(TextColor.Factory.fromString("#336699"));
        graphics.fillRectangle(new TerminalPosition(0, 0), screen.getTerminalSize(), ' ');

        int width = screen.getTerminalSize().getColumns();
        int height = screen.getTerminalSize().getRows();

        String title = "SOKOBAN GAME";
        graphics.setForegroundColor(TextColor.ANSI.WHITE);
        graphics.enableModifiers(SGR.BOLD);
        graphics.putString(new TerminalPosition((width - title.length()) / 2, height / 4), title);

        int totalEntries = menu.getNumberEntries();
        int startY = (height - totalEntries) / 2;

        for (int i = 0; i < totalEntries; i++) {
            String entry = menu.getEntry(i);

            if (menu.isSelected(i)) {
                graphics.enableModifiers(SGR.BOLD);
                entry = "-> " + entry + " <-";
            } else {
                graphics.setForegroundColor(TextColor.ANSI.WHITE);
                entry = "   " + entry + "   ";
            }

            int startX = (width - entry.length()) / 2;
            graphics.putString(new TerminalPosition(startX, startY + i), entry);
        }
    }
}