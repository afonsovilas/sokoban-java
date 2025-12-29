package com.sokoban.controller;

import com.sokoban.model.Box;
import com.sokoban.model.Level;
import com.sokoban.model.Position;
import com.sokoban.model.Wall;
import net.jqwik.api.*;
import org.mockito.Mockito;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class CollisionPBTTest {

    @Property
    void isFreeShouldBeConsistentWithWallAndBoxChecks(@ForAll int x, @ForAll int y) {

        Level level = Mockito.mock(Level.class);
        Collision collision = new Collision(level);
        Position p = new Position(x, y);

        when(level.getWalls()).thenReturn(List.of(new Wall(x, y)));
        when(level.getBoxes()).thenReturn(Collections.emptyList());

        assertFalse(collision.isFree(p), "Se é parede, isFree deve ser false");

        when(level.getWalls()).thenReturn(Collections.emptyList());
        when(level.getBoxes()).thenReturn(List.of(new Box(x, y)));

        assertFalse(collision.isFree(p), "Se tem caixa, isFree deve ser false");

        when(level.getWalls()).thenReturn(Collections.emptyList());
        when(level.getBoxes()).thenReturn(Collections.emptyList());

        assertTrue(collision.isFree(p), "Se não há nada, isFree deve ser true");
    }
}
