package com.sokoban.controller;

import com.sokoban.controller.Collision;
import com.sokoban.model.*;
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

    @Test
    void testGetOtherPortal() {
        Portal p1 = new Portal(1, 1);
        Portal p2 = new Portal(5, 5);

        when(level.getPortals()).thenReturn(List.of(p1, p2));

        assertEquals(p2, collision.getOtherPortal(p1));
        assertEquals(p1, collision.getOtherPortal(p2));
    }

    @Test
    void testGetPowerUpAt() {
        PowerUp p = new PowerUp(3, 3);
        when(level.getPowerUps()).thenReturn(List.of(p));

        assertNotNull(collision.getPowerUpAt(new Position(3, 3)));
        assertNull(collision.getPowerUpAt(new Position(4, 4)));
    }

    @Test
    void testGetPortalAt() {
        Portal portal = new Portal(3, 3);
        when(level.getPortals()).thenReturn(List.of(portal));
        assertEquals(portal, collision.getPortalAt(new Position(3, 3)), "Deve encontrar o portal se estiver na lista");

        assertNull(collision.getPortalAt(new Position(4, 4)), "Deve retornar null se a posição estiver vazia de portais");
    }

    @Test
    void testGetLevel() {
        assertEquals(level, collision.getLevel());
    }
}