import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class LevelLoaderTest {

    @Test
    void testLoadLevel1() {
        LevelLoader loader = new LevelLoader();
        Level level = loader.loadLevel(1);
        assertNotNull(level, "O nível não foi carregado (ficheiro não encontrado?)");

        assertEquals(21, level.getWidth());
        assertEquals(11, level.getHeight());

        assertNotNull(level.getHero());
        assertEquals(new Position(10, 2), level.getHero().getPosition());

        assertEquals(4, level.getBoxes().size());
        assertEquals(new Position(7, 3), level.getBoxes().get(0).getPosition());
        assertEquals(new Position(11, 8), level.getBoxes().get(3).getPosition());

        assertEquals(4, level.getTargets().size());
        assertEquals(new Position(7, 5), level.getTargets().get(0).getPosition());
        assertEquals(88, level.getWalls().size());
    }

    @Test
    void testLoadLevel2() {
        LevelLoader loader = new LevelLoader();
        Level level = loader.loadLevel(2);

        assertNotNull(level, "O nível 2 não foi carregado");

        assertEquals(15, level.getWidth());
        assertEquals(11, level.getHeight());

        assertNotNull(level.getHero());
        assertEquals(new Position(7, 6), level.getHero().getPosition());

        assertEquals(5, level.getBoxes().size());

        assertEquals(new Position(4, 4), level.getBoxes().get(0).getPosition());
        assertEquals(new Position(7, 4), level.getBoxes().get(1).getPosition());
        assertEquals(new Position(10, 4), level.getBoxes().get(2).getPosition());

        assertEquals(5, level.getTargets().size());
        assertEquals(new Position(4, 2), level.getTargets().get(0).getPosition());
        assertEquals(55, level.getWalls().size());
    }

    @Test
    void testLoadInvalidLevel() {
        LevelLoader loader = new LevelLoader();
        Level level = loader.loadLevel(50);
        assertNull(level);
    }
}