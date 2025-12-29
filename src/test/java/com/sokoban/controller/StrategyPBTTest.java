package com.sokoban.controller;

import com.sokoban.model.Hero;
import com.sokoban.model.Position;
import net.jqwik.api.*;
import org.mockito.Mockito;

import static org.mockito.Mockito.*;

class StrategyPBTTest {

    @Property
    void heroShouldNeverMoveIntoWall(
            @ForAll int heroX, @ForAll int heroY,
            @ForAll int dirX, @ForAll int dirY) {

        Hero hero = Mockito.mock(Hero.class);
        when(hero.getPosition()).thenReturn(new Position(heroX, heroY));

        Collision collision = Mockito.mock(Collision.class);
        Position direction = new Position(dirX, dirY);
        Position target = new Position(heroX + dirX, heroY + dirY);

        when(collision.isWall(target)).thenReturn(true);

        NormalMovementStrategy strategy = new NormalMovementStrategy();

        strategy.move(hero, direction, collision);

        verify(hero, never()).setPosition(any());
    }
}
