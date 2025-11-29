import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TerminalPosition;
import com.sokoban.model.Level;
import com.sokoban.model.Wall;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class LevelTest {

    private Level level;
    private TextGraphics mainGraphics;
    private TextGraphics subGraphics;

    @BeforeEach
    void setUp() {
        mainGraphics = mock(TextGraphics.class);
        subGraphics = mock(TextGraphics.class);
        when(mainGraphics.newTextGraphics(any(TerminalPosition.class), any(TerminalSize.class)))
                .thenReturn(subGraphics);

        level = new Level(10, 10);
    }

    @Test
    void testDrawCenteringAndBackground() {

        level.draw(mainGraphics);
        verify(mainGraphics).newTextGraphics(
                eq(new TerminalPosition(35, 15)),
                eq(new TerminalSize(10, 10))
        );
        verify(mainGraphics).setBackgroundColor(TextColor.Factory.fromString("#2C1A0B"));
        verify(mainGraphics).fillRectangle(
                eq(new TerminalPosition(33, 14)),
                eq(new TerminalSize(14, 12)),
                eq(' ')
        );
    }
    @Test
    void testDrawElementsDelegation() {
        Wall wall = mock(Wall.class);
        level.getWalls().add(wall);

        level.draw(mainGraphics);
        verify(wall).draw(subGraphics);
        verify(wall, never()).draw(mainGraphics);
    }



}
