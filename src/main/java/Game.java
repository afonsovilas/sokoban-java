import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import java.awt.Font;
import com.googlecode.lanterna.terminal.swing.SwingTerminalFontConfiguration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Game {
    private Screen screen;
    private State state;
    private GameState savedGame;

    public Game() {
        try {

            TerminalSize terminalSize = new TerminalSize(80, 40);
            DefaultTerminalFactory terminalFactory = new DefaultTerminalFactory()
                    .setInitialTerminalSize(terminalSize);
            Font font = new Font("Monospaced", Font.BOLD, 20);
            terminalFactory.setTerminalEmulatorFontConfiguration(
                    SwingTerminalFontConfiguration.newInstance(font)
            );

            Terminal terminal = terminalFactory.createTerminal();

            screen = new TerminalScreen(terminal);
            screen.setCursorPosition(null);
            screen.startScreen();
            screen.doResizeIfNecessary();

            this.state = new MenuState(new Menu());

        } catch (IOException e) {
            e.printStackTrace();
        }
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
