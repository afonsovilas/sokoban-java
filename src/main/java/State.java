import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.screen.Screen;

import java.io.IOException;

public abstract class State<T> {
    private final T model;

    public State(T model) {
        this.model = model;
    }

    public T getModel() {
        return model;
    }

    public abstract void draw(Screen screen, TextGraphics graphics) throws IOException;

    public abstract void step(Game game, KeyStroke key) throws IOException;
}