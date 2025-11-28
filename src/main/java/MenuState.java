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

        graphics.setForegroundColor(TextColor.ANSI.WHITE);
        graphics.putString(new TerminalPosition(10, 5), "SOKOBAN GAME");

        for (int i = 0; i < getModel().getNumberEntries(); i++) {
            if (getModel().isSelected(i)) {
                graphics.enableModifiers(SGR.BOLD);
                graphics.putString(new TerminalPosition(12, 10 + i), "-> " + getModel().getEntry(i));
            } else {
                graphics.disableModifiers(SGR.BOLD);
                graphics.putString(new TerminalPosition(12, 10 + i), getModel().getEntry(i));
            }
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
                LevelLoader loader = new LevelLoader();
                Level firstLevel = loader.loadLevel(1);

                if (firstLevel != null) {

                    firstLevel.initialize();
                    game.setState(new GameState(firstLevel, 1));
                }
            }
        }
    }
}
