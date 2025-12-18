package com.sokoban.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ElementCreationTest {

    @Test
    void testCreateWall() {
        Element element = ElementFactory.createElement('#', 10, 10);
        assertNotNull(element);
        assertTrue(element instanceof Wall);
        assertEquals(new Position(10, 10), element.getPosition());
    }

    @Test
    void testCreateBox() {
        Element element = ElementFactory.createElement('O', 5, 5);
        assertNotNull(element);
        assertTrue(element instanceof Box);
        assertEquals(new Position(5, 5), element.getPosition());
    }

    @Test
    void testCreateHero() {
        Element element = ElementFactory.createElement('X', 1, 1);
        assertNotNull(element);
        assertTrue(element instanceof Hero);
        assertEquals(new Position(1, 1), element.getPosition());
    }

    @Test
    void testCreateTarget() {
        Element element = ElementFactory.createElement('*', 2, 2);
        assertNotNull(element);
        assertTrue(element instanceof Target);
        assertEquals(new Position(2, 2), element.getPosition());
    }

    @Test
    void testInvalid() {
        Element elementSpace = ElementFactory.createElement(' ', 0, 0);
        assertNull(elementSpace);

        Element elementInvalid = ElementFactory.createElement('Z', 0, 0);
        assertNull(elementInvalid);
    }
}
