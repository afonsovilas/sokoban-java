package com.sokoban.controller;

import com.sokoban.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class StrategyTest {

    private Collision collision;
    private Hero hero;

    @BeforeEach
    void setUp() {
        collision = mock(Collision.class);
        hero = mock(Hero.class);
        when(hero.getPosition()).thenReturn(new Position(5, 5));
    }

    @Test
    void normalStrategyCannotPushTwoBoxes() {
        NormalMovementStrategy strategy = new NormalMovementStrategy();
        Position direction = new Position(1, 0);

        Box box1 = mock(Box.class);
        when(box1.getPosition()).thenReturn(new Position(6, 5));

        Box box2 = mock(Box.class);
        when(collision.getBoxAt(new Position(6, 5))).thenReturn(box1);
        when(collision.getBoxAt(new Position(7, 5))).thenReturn(box2);
        when(collision.isFree(new Position(7, 5))).thenReturn(false);

        strategy.move(hero, direction, collision);

        verify(box1, never()).setPosition(any());
        verify(hero, never()).setPosition(any());
    }

    @Test
    void superStrategyCanPushTwoBoxes() {
        SuperMovementStrategy strategy = new SuperMovementStrategy();
        Position direction = new Position(1, 0);

        Box box1 = new Box(6, 5);
        Box box2 = new Box(7, 5);

        when(collision.getBoxAt(new Position(6, 5))).thenReturn(box1);
        when(collision.getBoxAt(new Position(7, 5))).thenReturn(box2);

        when(collision.isFree(new Position(8, 5))).thenReturn(true);
        when(collision.getBoxAt(new Position(8, 5))).thenReturn(null);
        when(collision.isWall(new Position(8, 5))).thenReturn(false);

        when(collision.isFree(new Position(7, 5))).thenReturn(true);

        strategy.move(hero, direction, collision);

        assertEquals(new Position(7, 5), box1.getPosition(), "Box1 deve mover para o lugar da Box2");
        assertEquals(new Position(8, 5), box2.getPosition(), "Box2 deve ser empurrada para o espaço livre");
        verify(hero).setPosition(new Position(6, 5));
    }

    @Test
    void testPushBoxThroughPortal() {
        NormalMovementStrategy strategy = new NormalMovementStrategy();
        Position direction = new Position(1, 0);

        Box box = new Box(6, 5);
        Portal pIn = new Portal(7, 5);
        Portal pOut = new Portal(20, 20);

        when(collision.getBoxAt(new Position(6, 5))).thenReturn(box);

        when(collision.getPortalAt(new Position(7, 5))).thenReturn(pIn);
        when(collision.getOtherPortal(pIn)).thenReturn(pOut);

        Position exitDest = new Position(21, 20); // (20+1, 20+0)
        when(collision.isFree(exitDest)).thenReturn(true);

        strategy.move(hero, direction, collision);

        assertEquals(exitDest, box.getPosition());
        verify(hero).setPosition(new Position(6, 5));
    }
}