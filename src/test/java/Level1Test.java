import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class Level1Test {

    @Test
    void testLevel1() {
        Level1 level = new Level1();

        assertEquals(18, level.getWidth());
        assertEquals(7, level.getHeight());

        assertNotNull(level.getHero());
        assertEquals(new Position(8, 4), level.getHero().getPosition());

        assertEquals(1, level.getBoxes().size());
        assertEquals(new Position(9, 2), level.getBoxes().get(0).getPosition());

        assertEquals(1, level.getTargets().size());
        assertEquals(new Position(12, 3), level.getTargets().get(0).getPosition());

        assertEquals(46, level.getWalls().size());
    }
}