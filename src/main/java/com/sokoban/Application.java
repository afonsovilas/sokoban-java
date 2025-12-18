package com.sokoban;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.terminal.swing.SwingTerminalFontConfiguration;
import com.sokoban.controller.Command;
import com.sokoban.controller.OpenLevelMenuCommand;
import com.sokoban.model.LevelLoader;
import com.sokoban.model.LevelManager;
import com.sokoban.model.MenuFactory;
import com.sokoban.states.GameState;
import com.sokoban.model.Menu;
import com.sokoban.states.MenuState;

import java.awt.*;
import java.io.IOException;
import java.util.Arrays;

public class Application {

    public static void main(String[] args) {
        try {
            TerminalSize terminalSize = new TerminalSize(80, 40);
            DefaultTerminalFactory terminalFactory = new DefaultTerminalFactory()
                    .setInitialTerminalSize(terminalSize).setTerminalEmulatorTitle("Sokoban");
            Font font = new Font("Monospaced", Font.BOLD, 22);
            terminalFactory.setTerminalEmulatorFontConfiguration(
                    SwingTerminalFontConfiguration.newInstance(font)
            );

            Terminal terminal = terminalFactory.createTerminal();
            Screen screen = new TerminalScreen(terminal);

            screen.setCursorPosition(null);
            screen.startScreen();
            screen.doResizeIfNecessary();

            LevelManager levelManager = new LevelManager();

            MenuState initialState = new MenuState(MenuFactory.createMainMenu(levelManager));


            Game game = new Game(screen, initialState);
            game.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
