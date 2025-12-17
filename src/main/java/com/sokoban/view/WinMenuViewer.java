package com.sokoban.view;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.Screen;
import com.sokoban.model.Menu;

public class WinMenuViewer {
    private final Menu menu;

    private static final int BOX_WIDTH = 40;
    private static final int BOX_HEIGHT = 15;

    public WinMenuViewer(Menu menu) {
        this.menu = menu;
    }

    public void draw(Screen screen, TextGraphics graphics) {

        graphics.setBackgroundColor(TextColor.Factory.fromString("#3E2723"));
        graphics.fillRectangle(new TerminalPosition(0, 0), screen.getTerminalSize(), ' ');

        int width = screen.getTerminalSize().getColumns();
        int height = screen.getTerminalSize().getRows();

        int startX = (width - BOX_WIDTH) / 2;
        int startY = (height - BOX_HEIGHT) / 2;


        String boxColor = "#4E342E";
        graphics.setBackgroundColor(TextColor.Factory.fromString(boxColor));
        graphics.fillRectangle(new TerminalPosition(startX, startY), new TerminalSize(BOX_WIDTH, BOX_HEIGHT), ' ');

        String title = "CONGRATULATIONS!";
        graphics.setForegroundColor(TextColor.Factory.fromString("#FFD54F"));
        graphics.enableModifiers(SGR.BOLD);
        graphics.putString(new TerminalPosition(startX + (BOX_WIDTH - title.length()) / 2, startY + 3), title);

        String subTitle = "You Passed this Level";
        graphics.setForegroundColor(TextColor.Factory.fromString("#D7CCC8"));
        graphics.disableModifiers(SGR.BOLD);
        graphics.putString(new TerminalPosition(startX + (BOX_WIDTH - subTitle.length()) / 2, startY + 5), subTitle);


        int buttonsStartY = startY + 9;

        for (int i = 0; i < menu.getNumberEntries(); i++) {
            String entry = menu.getEntryText(i);

            if (menu.isSelected(i)) {

                graphics.setBackgroundColor(TextColor.Factory.fromString("#FF9800"));
                graphics.setForegroundColor(TextColor.Factory.fromString("#3E2723"));
                graphics.enableModifiers(SGR.BOLD);


                entry = "[ " + entry + " ]";
            } else {

                graphics.setBackgroundColor(TextColor.Factory.fromString(boxColor));
                graphics.setForegroundColor(TextColor.Factory.fromString("#D7CCC8"));
                graphics.disableModifiers(SGR.BOLD);


                entry = "  " + entry + "  ";
            }

            int buttonX = startX + (BOX_WIDTH - entry.length()) / 2;
            graphics.putString(new TerminalPosition(buttonX, buttonsStartY + (i * 2)), entry);
        }
    }
}