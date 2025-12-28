package com.sokoban.model;

import com.sokoban.model.Hero;
import com.sokoban.model.Position;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import net.jqwik.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HeroTest {
    @Test
    public void moveUp() {
        Hero hero = new Hero(10, 10);
        Position newPos = hero.moveUp();

        assertEquals(10, newPos.getX());
        assertEquals(9, newPos.getY());
    }
    @Test
    public void moveDown() {
        Hero hero = new Hero(10, 10);
        Position newPos = hero.moveDown();

        assertEquals(10, newPos.getX());
        assertEquals(11, newPos.getY());
    }
    @Test
    public void moveLeft() {
        Hero hero = new Hero(10, 10);
        Position newPos = hero.moveLeft();

        assertEquals(9, newPos.getX());
        assertEquals(10, newPos.getY());
    }
    @Test
    public void moveRight() {
        Hero hero = new Hero(10, 10);
        Position newPos = hero.moveRight();

        assertEquals(11, newPos.getX());
        assertEquals(10, newPos.getY());
    }

    @Test
    void moveShouldReturnNewPositionNotModifyOriginal() {
        Hero hero = new Hero(5, 5);
        Position nextPos = hero.moveRight();

        assertEquals(6, nextPos.getX());

        assertEquals(5, hero.getPosition().getX());
    }

    @Property
    void movingOppositeDirectionsShouldReturnToOriginalAxis(@ForAll int x, @ForAll int y) {
        Hero hero = new Hero(x, y);

        Position up = hero.moveUp();
        Position backDown = new Hero(up.getX(), up.getY()).moveDown();

        assertEquals(y, backDown.getY());
        assertEquals(x, backDown.getX());
    }

    @Property
    void moveRightShouldAlwaysIncrementX(@ForAll int x, @ForAll int y) {
        Hero hero = new Hero(x, y);
        Position newPos = hero.moveRight();

        assertEquals(x + 1, newPos.getX());
        assertEquals(y, newPos.getY());
    }

}
