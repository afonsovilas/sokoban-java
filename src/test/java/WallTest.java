import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.sokoban.model.Wall;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class WallTest {

    @Test
    public void testWallPosition() {

        Wall wall = new Wall(10, 20);
        Assertions.assertEquals(10, wall.getPosition().getX());
        Assertions.assertEquals(20, wall.getPosition().getY());
    }

    @Test
    public void testDraw() {

        Wall wall = new Wall(10, 20);


        TextGraphics graphics = Mockito.mock(TextGraphics.class);
        wall.draw(graphics);

        Mockito.verify(graphics).setForegroundColor(TextColor.Factory.fromString("#8B4513"));

        Mockito.verify(graphics).putString(new TerminalPosition(10, 20), "#");
    }
}
