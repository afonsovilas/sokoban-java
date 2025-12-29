package com.sokoban.controller;

import com.sokoban.controller.Collision;
import com.sokoban.controller.HeroController;
import com.sokoban.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class HeroControllerTest {
    private HeroController controller;
    private Hero hero;
    private Collision collision;
    private Level level;
    @BeforeEach
    void setUp() {
        hero = Mockito.mock(Hero.class);
        collision = Mockito.mock(Collision.class);
        level = Mockito.mock(Level.class);

        when(collision.getLevel()).thenReturn(level);

        controller = new HeroController(hero, collision);
    }

    @Test
    void moveHeroToEmptyPosition() {
        when(hero.getPosition()).thenReturn(new Position(10, 10));
        Position targetPos = new Position(11, 10);

        when(collision.isWall(targetPos)).thenReturn(false);
        when(collision.getBoxAt(targetPos)).thenReturn(null);

        controller.moveHero(new Position(1, 0));
        verify(hero, times(1)).setPosition(targetPos);
    }

    @Test
    void moveHeroToOcupiedPosition() {
        when(hero.getPosition()).thenReturn(new Position(10, 10));
        Position targetPos = new Position(11, 10);

        when(collision.isWall(targetPos)).thenReturn(true);

        controller.moveHero(new Position(1, 0));
        verify(hero, never()).setPosition(any());
    }

    @Test
    void shouldCollectPowerUpWhenNoBoxIsPresent() {
        when(hero.getPosition()).thenReturn(new Position(10, 10));
        Position targetPos = new Position(11, 10);
        PowerUp powerUp = new PowerUp(11, 10);

        when(collision.isWall(targetPos)).thenReturn(false);
        when(collision.getBoxAt(targetPos)).thenReturn(null);
        when(collision.getPowerUpAt(targetPos)).thenReturn(powerUp);

        controller.moveHero(new Position(1, 0));

        verify(level, times(1)).removePowerUp(powerUp);

        assertTrue(controller.hasSuperPower());

        verify(hero, times(1)).setPosition(targetPos);
    }

    @Test
    void shouldNOTCollectPowerUpIfBoxIsOnTop() {
        when(hero.getPosition()).thenReturn(new Position(10, 10));
        Position targetPos = new Position(11, 10);
        Position boxDestPos = new Position(12, 10);

        PowerUp powerUp = new PowerUp(11, 10);
        Box box = Mockito.mock(Box.class);
        when(box.getPosition()).thenReturn(targetPos);

        // Mocks
        when(collision.isWall(targetPos)).thenReturn(false);
        when(collision.getBoxAt(targetPos)).thenReturn(box);
        when(collision.getPowerUpAt(targetPos)).thenReturn(powerUp);
        when(collision.isFree(boxDestPos)).thenReturn(true);

        controller.moveHero(new Position(1, 0));

        verify(level, never()).removePowerUp(any());

        assertFalse(controller.hasSuperPower());

        verify(box, times(1)).setPosition(boxDestPos);
    }

    @Test
    void shouldResetSuperPowerCorrectly() {
        controller.setSuperPower(true);
        assertTrue(controller.hasSuperPower());
        assertTrue(controller.getSecondsLeft() > 0);

        controller.setSuperPower(false);

        assertFalse(controller.hasSuperPower());
        assertEquals(0, controller.getSecondsLeft());
    }

    @Test
    void shouldSetSpecificDurationForSuperPower() {
        controller.setSuperPower(5000);

        assertTrue(controller.hasSuperPower());
        long seconds = controller.getSecondsLeft();
        assertTrue(seconds >= 4 && seconds <= 5);
    }
}