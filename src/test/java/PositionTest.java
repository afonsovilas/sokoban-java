import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class PositionTest {
    private Position position;

    @BeforeEach
    void setUp() {
        position = new Position(10, 20);
    }

    @Test
    void testConstructorAndGetters() {
        assertEquals(10, position.getX());
        assertEquals(20, position.getY());
    }

    @Test
    void testSetters() {
        position.setX(5);
        position.setY(15);
        assertEquals(5, position.getX());
        assertEquals(15, position.getY());
    }

    @Test
    void testEquals() {
        Position p1 = new Position(10, 20);
        Position p2 = new Position(10, 20);
        Position p3 = new Position(5, 5);

        assertEquals(p1, p1);
        assertEquals(p1, p2);
        assertNotEquals(p1, p3);
        assertNotEquals(p1, null);
        assertNotEquals(p1, "Uma String");
    }
}
