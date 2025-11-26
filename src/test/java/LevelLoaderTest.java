import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class LevelLoaderTest {

    @Test
    void testLoadLevel1() {
        LevelLoader loader = new LevelLoader();
        Level level = loader.loadLevel(1);

        assertNotNull(level);

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

    @Test
    void testLoadLevel2() {
        LevelLoader loader = new LevelLoader();
        Level level = loader.loadLevel(2);

        assertEquals(18, level.getWidth());
        assertEquals(7, level.getHeight());

        assertNotNull(level.getHero());
        assertEquals(new Position(9, 4), level.getHero().getPosition());

        assertEquals(2, level.getBoxes().size());

        assertEquals(new Position(10, 2), level.getBoxes().get(0).getPosition());
        assertEquals(new Position(3, 4), level.getBoxes().get(1).getPosition());
        assertEquals(2, level.getTargets().size());

        assertEquals(new Position(3, 2), level.getTargets().get(0).getPosition());
        assertEquals(new Position(12, 3), level.getTargets().get(1).getPosition());

        assertEquals(46, level.getWalls().size());
    }

    @Test
    void testLoadInvalidLevel() {
        LevelLoader loader = new LevelLoader();
        Level level = loader.loadLevel(50);
        assertNull(level);
    }
}