package com.sokoban;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import java.awt.Font;
import com.googlecode.lanterna.terminal.swing.SwingTerminalFontConfiguration;
import com.sokoban.model.Menu;
import com.sokoban.states.GameState;
import com.sokoban.states.MenuState;
import com.sokoban.states.State;

import java.io.IOException;

public class Game {
    private Screen screen;
    private State state;
    private GameState savedGame;

    public Game(Screen screen) {
        this.screen = screen;
        this.state = new MenuState(new Menu(false));
    }


    public void setState(State state) {
        this.state = state;
    }
    public void saveGameState(GameState gameState) {this.savedGame = gameState;
    }

    public void run() {
        try {
            while (state!= null) {
                state.draw(screen, screen.newTextGraphics());
                screen.refresh();
                KeyStroke key = screen.readInput();

                state.step(this,key);

            }
            screen.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public GameState getSavedGame() {return savedGame;}

}
