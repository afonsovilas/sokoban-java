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
    private Hero hero;
    private List<Wall> walls;

    public Game() {
        try {

            TerminalSize terminalSize = new TerminalSize(20, 20);
            hero = new Hero(10, 10);
            this.walls = new ArrayList<>();
            walls.add(new Wall(15,15));
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

        TextGraphics graphics = screen.newTextGraphics();
        hero.draw(graphics);
        for (Wall wall : walls) {
            wall.draw(graphics);
        }
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
