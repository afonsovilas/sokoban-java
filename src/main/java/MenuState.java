import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;

import java.io.IOException;

public class MenuState extends State<Menu> {
    public MenuState(Menu model) {
        super(model);
    }

    @Override
    public void draw(Screen screen, TextGraphics graphics) {
        screen.clear();
        graphics.setBackgroundColor(TextColor.Factory.fromString("#336699"));
        graphics.fillRectangle(new TerminalPosition(0, 0), screen.getTerminalSize(), ' ');

        int width = screen.getTerminalSize().getColumns();
        int height = screen.getTerminalSize().getRows();

        graphics.setForegroundColor(TextColor.ANSI.WHITE);
        graphics.putString(new TerminalPosition((width - "SOKOBAN GAME".length()) / 2, height / 4), "SOKOBAN GAME");


        int totalEntries = getModel().getNumberEntries();
        int startY = (height - totalEntries) / 2;
        for (int i = 0; i < getModel().getNumberEntries(); i++) {
            String entry = getModel().getEntry(i);
            if (getModel().isSelected(i)) {
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

    @Override
    public void step(Game game, KeyStroke key) throws IOException {
        if (key == null) return;

        if (key.getKeyType() == KeyType.ArrowUp) {
            getModel().previousEntry();
        }
        if (key.getKeyType() == KeyType.ArrowDown) {
            getModel().nextEntry();
        }
        if (key.getKeyType() == KeyType.Enter) {
            if (getModel().isSelectedExit()) {
                game.setState(null);
            }
            if (getModel().isSelectedStart()) {

                game.saveGameState(null);

                LevelLoader loader = new LevelLoader();
                Level firstLevel = loader.loadLevel(1);

                if (firstLevel != null) {

                    firstLevel.initialize();
                    game.setState(new GameState(firstLevel, 1));
                }
            }

            if (getModel().isSelectedResume()) {

                GameState saved = game.getSavedGame();

                if (saved != null) {
                    game.setState(saved);
                }
            }
        }
    }
}
