import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Game {
    private Screen screen;
    private Level level;
    private LevelLoader levelLoader;
    private int currLevelIndex = 1;

    public Game() {
        try {
            this.levelLoader = new LevelLoader();
            this.level = levelLoader.loadLevel(currLevelIndex);

            if (this.level == null) {
                System.out.println("Erro: O ficheiro do nível não foi encontrado!");
                System.exit(1);
            }

            TerminalSize terminalSize = new TerminalSize(80, 40);
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
        level.draw(screen.newTextGraphics());
        screen.refresh();
    }

    private void processKey(KeyStroke key) {

        if (key.getKeyType() == KeyType.Character && key.getCharacter() == 'q') {
            try { screen.close(); } catch (IOException e) {}
            System.exit(0);
        }
        if (key.getKeyType() == KeyType.EOF) {
            try { screen.close(); } catch (IOException e) {}
            System.exit(0);
        }

        level.processKey(key);
    }

    public void run() {
        try {
            while (true) {
                draw();
                KeyStroke key = screen.readInput();
                processKey(key);

                if (level.isFinished()) {
                    currLevelIndex++;
                    Level nextLevel = levelLoader.loadLevel(currLevelIndex);
                    if (nextLevel != null) {
                        this.level = nextLevel;
                        this.level.initialize();

                        screen.clear();
                    } else {

                        System.out.println("You Won!");
                        screen.close();
                        System.exit(0);
                    }
                }

                if (key.getKeyType() == KeyType.EOF) break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
