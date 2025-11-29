package com.sokoban.controller;

import com.sokoban.controller.Collision;
import com.sokoban.model.Box;
import com.sokoban.model.Level;
import com.sokoban.model.Position;
import com.sokoban.model.Wall;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class CollisionTest {
    private Collision collision;
    private Level level;

    @BeforeEach
    void setUp() {
        level = Mockito.mock(Level.class);
        collision = new Collision(level);
    }

    @Test
    void testIsWall() {
        when(level.getWalls()).thenReturn(List.of(new Wall(2, 2)));
        assertTrue(collision.isWall(new Position(2, 2)));
        assertFalse(collision.isWall(new Position(3, 3)));
    }

    @Test
    void testGetBoxAt() {
        Box box = new Box(5, 5);
        when(level.getBoxes()).thenReturn(List.of(box));
        assertEquals(box, collision.getBoxAt(new Position(5, 5)));
        assertNull(collision.getBoxAt(new Position(6, 6)));
    }

    @Test
    void testIsFree() {
        when(level.getWalls()).thenReturn(List.of(new Wall(1, 1)));
        when(level.getBoxes()).thenReturn(List.of(new Box(2, 2)));

        assertFalse(collision.isFree(new Position(1, 1)));
        assertFalse(collision.isFree(new Position(2, 2)));
        assertTrue(collision.isFree(new Position(3, 3)));
    }
}