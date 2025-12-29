package com.sokoban.controller;

import com.sokoban.model.Box;
import com.sokoban.model.Hero;
import com.sokoban.model.Portal;
import com.sokoban.model.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.*;

class NormalMovementStrategyTest {
    private NormalMovementStrategy strategy;
    private Collision collision;
    private Hero hero;

    @BeforeEach
    void setUp() {
        strategy = new NormalMovementStrategy();
        collision = Mockito.mock(Collision.class);
        hero = Mockito.mock(Hero.class);
    }

    @Test
    void moveHeroToEmptySpace() {
        Position startPos = new Position(5, 5);
        Position direction = new Position(1, 0);
        Position targetPos = new Position(6, 5);

        when(hero.getPosition()).thenReturn(startPos);
        when(collision.isWall(targetPos)).thenReturn(false);
        when(collision.getBoxAt(targetPos)).thenReturn(null);

        strategy.move(hero, direction, collision);

        verify(hero).setPosition(targetPos);
    }

    @Test
    void moveHeroBlockedByWall() {
        Position startPos = new Position(5, 5);
        Position direction = new Position(0, 1);
        Position targetPos = new Position(5, 6);

        when(hero.getPosition()).thenReturn(startPos);
        when(collision.isWall(targetPos)).thenReturn(true);

        strategy.move(hero, direction, collision);

        verify(hero, never()).setPosition(any());
    }

    @Test
    void moveHeroThroughPortal() {
        Position startPos = new Position(4, 5);
        Position direction = new Position(1, 0);
        Position portalPos = new Position(5, 5);

        Portal portalEnter = new Portal(5, 5);
        Portal portalExit = new Portal(10, 10);

        when(hero.getPosition()).thenReturn(startPos);
        when(collision.isWall(portalPos)).thenReturn(false);
        when(collision.getBoxAt(portalPos)).thenReturn(null);

        when(collision.getPortalAt(portalPos)).thenReturn(portalEnter);
        when(collision.getOtherPortal(portalEnter)).thenReturn(portalExit);
        when(collision.getBoxAt(portalExit.getPosition())).thenReturn(null);

        strategy.move(hero, direction, collision);

        verify(hero).setPosition(portalExit.getPosition());
    }

    @Test
    void pushSingleBoxSuccess() {
        Position startPos = new Position(5, 5);
        Position direction = new Position(1, 0);
        Position boxPos = new Position(6, 5);
        Position boxDestPos = new Position(7, 5);

        Box box = mock(Box.class);
        when(box.getPosition()).thenReturn(boxPos);

        when(hero.getPosition()).thenReturn(startPos);
        when(collision.getBoxAt(boxPos)).thenReturn(box);
        when(collision.isFree(boxDestPos)).thenReturn(true);

        strategy.move(hero, direction, collision);

        verify(box).setPosition(boxDestPos);
        verify(hero).setPosition(boxPos);
    }
}