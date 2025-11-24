import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;

public class Game {
    private Screen screen;

    public Game() {
        try {

            TerminalSize terminalSize = new TerminalSize(20, 20);
            DefaultTerminalFactory terminalFactory = new DefaultTerminalFactory()
                    .setInitialTerminalSize(terminalSize);
            Terminal terminal = terminalFactory.createTerminal();

            screen = new TerminalScreen(terminal);
            screen.setCursorPosition(null);
            screen.startScreen();
            screen.doResizeIfNecessary();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void draw() throws IOException {
        screen.clear();


        screen.refresh();
    }

    private void processKey(KeyStroke key) {

        if (key.getKeyType() == KeyType.Character && key.getCharacter() == 'q') {
            try {
                screen.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.exit(0);
        }

        if (key.getKeyType() == KeyType.EOF) {
            try {
                screen.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.exit(0);
        }

    }

    public void run() {
        try {
            while (true) {
                draw();
                KeyStroke key = screen.readInput();
                processKey(key);
                if (key.getKeyType() == KeyType.EOF) break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
