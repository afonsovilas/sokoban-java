package com.sokoban.model;

import com.sokoban.model.Wall;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class WallTest {

    @Test
    public void testWallPosition() {

        Wall wall = new Wall(10, 20);
        Assertions.assertEquals(10, wall.getPosition().getX());
        Assertions.assertEquals(20, wall.getPosition().getY());
    }

}
