package com.sokoban.model;

import com.sokoban.model.Box;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BoxTest {
    @Test
    void testConstructor() {
        Box box = new Box(10, 20);
        assertEquals(10, box.getPosition().getX());
        assertEquals(20, box.getPosition().getY());
        assertFalse(box.isOnTarget());
    }

}
