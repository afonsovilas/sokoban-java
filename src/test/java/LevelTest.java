import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TerminalPosition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LevelTest {

    private Level level;
    private TextGraphics graphics;

    @BeforeEach
    void setUp() {
        graphics = mock(TextGraphics.class);
        level = new Level(10, 10) {
            protected void createLevel() {
            }
        };
    }

    @Test
    void testDrawLevel() {
        Wall wall = mock(Wall.class);
        level.getWalls().add(wall);

        level.draw(graphics);

        verify(graphics).fillRectangle(eq(new TerminalPosition(0, 0)), any(TerminalSize.class), eq(' '));
        verify(wall).draw(graphics);
    }

    @Test
    void testBoxOnTarget() {
        Box box = mock(Box.class);
        when(box.getPosition()).thenReturn(new Position(5, 5));

        Target target = new Target(5, 5);

        level.getBoxes().add(box);
        level.getTargets().add(target);

        level.draw(graphics);

        verify(box).setOnTarget(true);
        verify(box).draw(graphics);
    }

    @Test
    void testBoxNotOnTarget() {
        Box box = mock(Box.class);
        when(box.getPosition()).thenReturn(new Position(5, 5));

        Target target = new Target(2, 2);

        level.getBoxes().add(box);
        level.getTargets().add(target);

        level.draw(graphics);

        verify(box).setOnTarget(false);
        verify(box).draw(graphics);
    }
}
