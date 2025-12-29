package com.sokoban.controller;

import com.sokoban.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class SuperStrategyTest {

    private Collision collision;
    private Hero hero;
    private SuperMovementStrategy strategy;

    @BeforeEach
    void setUp() {
        collision = mock(Collision.class);
        hero = mock(Hero.class);
        strategy = new SuperMovementStrategy();
        when(hero.getPosition()).thenReturn(new Position(5, 5));
    }

    @Test
    void shouldBehaveLikeNormalStrategyWhenPushingSingleBox() {
        Position direction = new Position(1, 0); // Direita
        Box box1 = new Box(6, 5);

        when(collision.getBoxAt(new Position(6, 5))).thenReturn(box1);
        when(collision.getBoxAt(new Position(7, 5))).thenReturn(null);
        when(collision.getPortalAt(new Position(7, 5))).thenReturn(null);
        when(collision.isFree(new Position(7, 5))).thenReturn(true);
        when(collision.isWall(new Position(7, 5))).thenReturn(false);

        strategy.move(hero, direction, collision);
        assertEquals(new Position(7, 5), box1.getPosition());
        verify(hero).setPosition(new Position(6, 5));
    }

    @Test
    void shouldDetectAndPushSecondBoxThroughPortal() {
        Position direction = new Position(1, 0);

        Box box1 = new Box(6, 5);
        Portal p1 = new Portal(7, 5);
        Portal p2 = new Portal(20, 20);
        Box box2 = new Box(21, 20);


        when(collision.getBoxAt(new Position(6, 5))).thenReturn(box1);
        when(collision.getBoxAt(new Position(7, 5))).thenReturn(null);

        when(collision.getPortalAt(new Position(7, 5))).thenReturn(p1);
        when(collision.getOtherPortal(p1)).thenReturn(p2);

        when(collision.getBoxAt(new Position(21, 20))).thenReturn(box2);

        when(collision.isFree(new Position(22, 20))).thenReturn(true);
        when(collision.getBoxAt(new Position(22, 20))).thenReturn(null);

        when(collision.isFree(new Position(21, 20))).thenReturn(true);

        when(collision.isFree(new Position(7, 5))).thenReturn(true);
        strategy.move(hero, direction, collision);

        assertEquals(new Position(22, 20), box2.getPosition(), "Box2 deve ser empurrada no destino do portal");
        assertEquals(new Position(21, 20), box1.getPosition(), "Box1 deve atravessar o portal e empurrar");
    }

    @Test
    void shouldRollbackIfSecondBoxMovesButFirstStuck() {
        Position direction = new Position(1, 0);
        Box box1 = new Box(6, 5);
        Box box2 = new Box(7, 5);
        Position box2OriginalPos = new Position(7, 5);

        when(collision.getBoxAt(new Position(6, 5))).thenReturn(box1);
        when(collision.getBoxAt(new Position(7, 5))).thenReturn(box2);

        when(collision.isFree(new Position(8, 5))).thenReturn(true);
        when(collision.getBoxAt(new Position(8, 5))).thenReturn(null);
        when(collision.isFree(new Position(7, 5))).thenReturn(false);

        strategy.move(hero, direction, collision);

        assertEquals(new Position(6, 5), box1.getPosition(), "Box1 não deve mover");
        assertEquals(box2OriginalPos, box2.getPosition(), "Box2 deve voltar à posição original");

        verify(hero, never()).setPosition(any());
    }
}
