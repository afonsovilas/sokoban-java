import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ElementCreationTest {

    @Test
    void testCreateWall() {
        Element element = ElementCreation.createElement('#', 10, 10);
        assertNotNull(element);
        assertTrue(element instanceof Wall);
        assertEquals(new Position(10, 10), element.getPosition());
    }

    @Test
    void testCreateBox() {
        Element element = ElementCreation.createElement('O', 5, 5);
        assertNotNull(element);
        assertTrue(element instanceof Box);
        assertEquals(new Position(5, 5), element.getPosition());
    }

    @Test
    void testCreateHero() {
        Element element = ElementCreation.createElement('X', 1, 1);
        assertNotNull(element);
        assertTrue(element instanceof Hero);
        assertEquals(new Position(1, 1), element.getPosition());
    }

    @Test
    void testCreateTarget() {
        Element element = ElementCreation.createElement('*', 2, 2);
        assertNotNull(element);
        assertTrue(element instanceof Target);
        assertEquals(new Position(2, 2), element.getPosition());
    }

    @Test
    void testInvalid() {
        Element elementSpace = ElementCreation.createElement(' ', 0, 0);
        assertNull(elementSpace);

        Element elementInvalid = ElementCreation.createElement('Z', 0, 0);
        assertNull(elementInvalid);
    }
}
