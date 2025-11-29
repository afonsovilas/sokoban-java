import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.sokoban.Game;
import com.sokoban.states.GameState;
import com.sokoban.model.Menu;
import com.sokoban.states.MenuState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class MenuStateTest {
    private MenuState state;
    private Menu menu;
    private Game game;

    @BeforeEach
    void setUp() {
        menu = new Menu();
        state = new MenuState(menu);
        game = Mockito.mock(Game.class);
    }

    @Test
    void testMenuNavigation() throws IOException {
        state.step(game, new KeyStroke(KeyType.ArrowDown));

        assert(menu.isSelectedResume());
    }

    @Test
    void testSelectStartGame() throws IOException {
        state.step(game, new KeyStroke(KeyType.Enter));

        verify(game).saveGameState(null);
        verify(game).setState(any(GameState.class));
    }


    @Test
    void testSelectResume() throws IOException {

        GameState savedGame = mock(GameState.class);
        when(game.getSavedGame()).thenReturn(savedGame);
        state.step(game, new KeyStroke(KeyType.ArrowDown));
        state.step(game, new KeyStroke(KeyType.Enter));

        verify(game).setState(savedGame);
    }

    @Test
    void testSelectExit() throws IOException {
        state.step(game, new KeyStroke(KeyType.ArrowDown));
        state.step(game, new KeyStroke(KeyType.ArrowDown));

        assert(menu.isSelectedExit());
        state.step(game, new KeyStroke(KeyType.Enter));
        verify(game).setState(null);
    }
}