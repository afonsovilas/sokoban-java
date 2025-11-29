import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

class GameStateTest {

    @Test
    void testQuitToMenu() {
        Level level = Mockito.mock(Level.class);
        Game game = Mockito.mock(Game.class);
        GameState state = new GameState(level, 1);

        state.step(game, new KeyStroke('q', false, false));

        verify(game).saveGameState(state);

        verify(game).setState(any(MenuState.class));
    }

}
