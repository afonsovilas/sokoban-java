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
        level = Mockito.mock(Level.class);
        collision = Mockito.mock(Collision.class);

        when(collision.getLevel()).thenReturn(level);

        when(hero.getPosition()).thenReturn(new Position(10, 10));

        controller = new HeroController(hero, collision);
    }

    @Test
    void moveHeroToEmptyPosition() {
        Position targetPos = new Position(11, 10);

        when(collision.isWall(targetPos)).thenReturn(false);
        when(collision.getBoxAt(targetPos)).thenReturn(null);

        controller.moveHero(new Position(1, 0));
        verify(hero, times(1)).setPosition(targetPos);
    }

    @Test
    void moveHeroIntoWallShouldFail() {
        Position targetPos = new Position(11, 10);
        when(collision.isWall(targetPos)).thenReturn(true);

        controller.moveHero(new Position(1, 0));
        verify(hero, never()).setPosition(any());
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
    void moveHeroPushBoxSuccess() {
        when(hero.getPosition()).thenReturn(new Position(10, 10));
        Position targetPos = new Position(11, 10);
        Position boxDestPos = new Position(12, 10);

        Box box = Mockito.mock(Box.class);
        when(box.getPosition()).thenReturn(targetPos);

        when(collision.isWall(targetPos)).thenReturn(false);
        when(collision.getBoxAt(targetPos)).thenReturn(box);
        when(collision.isFree(boxDestPos)).thenReturn(true);

        controller.moveHero(new Position(1, 0));

        verify(box, times(1)).setPosition(boxDestPos);
        verify(hero, times(1)).setPosition(targetPos);
    }

    @Test
    void moveHeroPushBoxFailed() {
        when(hero.getPosition()).thenReturn(new Position(10, 10));
        Position targetPos = new Position(11, 10);
        Position boxDestPos = new Position(12, 10);

        Box box = Mockito.mock(Box.class);
        when(box.getPosition()).thenReturn(targetPos);

        when(collision.isWall(targetPos)).thenReturn(false);
        when(collision.getBoxAt(targetPos)).thenReturn(box);
        when(collision.isFree(boxDestPos)).thenReturn(false);

        controller.moveHero(new Position(1, 0));

        verify(box, never()).setPosition(any());
        verify(hero, never()).setPosition(any());
    }

    @Test
    void collectPowerUpShouldActivateSuperStrategy() {
        Position targetPos = new Position(11, 10);
        PowerUp powerUp = new PowerUp(11, 10);

        when(collision.isWall(targetPos)).thenReturn(false);
        when(collision.getBoxAt(targetPos)).thenReturn(null);
        when(collision.getPowerUpAt(targetPos)).thenReturn(powerUp);

        assertFalse(controller.hasSuperPower());

        controller.moveHero(new Position(1, 0));
        verify(level).removePowerUp(powerUp);
        assertTrue(controller.hasSuperPower(), "O controlador deve mudar para SuperMovementStrategy");
        verify(hero).setPosition(targetPos);
    }

    @Test
    void superPowerShouldExpireAfterDuration() throws InterruptedException {

        controller.setSuperPower(100);
        assertTrue(controller.hasSuperPower());

        Thread.sleep(150);
        Position targetPos = new Position(11, 10);
        when(collision.isWall(targetPos)).thenReturn(false);
        controller.moveHero(new Position(1, 0));

        assertFalse(controller.hasSuperPower(), "O poder deve expirar após o tempo definido");
    }

    @Test
    void testSuperPowerToggleAndTiming() {
        controller.setSuperPower(10000);
        assertTrue(controller.hasSuperPower());

        long remaining = controller.getRemainingTime();
        assertTrue(remaining > 0, "Deve ser positivo");
        assertTrue(remaining <= 10000, "Não pode ser maior que a duração inicial (mata o mutante de soma)");

        controller.setSuperPower(0);
        assertFalse(controller.hasSuperPower());
    }

    @Test
    void testSetSuperPowerWithDuration() {

        controller.setSuperPower(5000);
        assertTrue(controller.hasSuperPower());

        long seconds = controller.getSecondsLeft();
        assertTrue(seconds >= 4 && seconds <= 5, "Devia reportar ~5 segundos");
    }

    @Test
    void testSuperPowerExpirationDuringMove() throws InterruptedException {

        controller.setSuperPower(10);
        assertTrue(controller.hasSuperPower());

        Thread.sleep(20);

        Position targetPos = new Position(11, 10);
        when(collision.isWall(targetPos)).thenReturn(false);

        controller.moveHero(new Position(1, 0));

        assertFalse(controller.hasSuperPower(), "O poder devia ter expirado ao tentar mover");
    }

    @Test
    void testCannotCollectPowerUpIfBoxIsThere() {
        Position targetPos = new Position(11, 10);

        when(collision.getBoxAt(targetPos)).thenReturn(new Box(11, 10));


        controller.moveHero(new Position(1, 0));
        assertFalse(controller.hasSuperPower());
        verify(level, never()).removePowerUp(any());
    }

    @Test
    void testMoveHeroVertically() {

        when(hero.getPosition()).thenReturn(new Position(10, 10));
        Position targetPos = new Position(10, 11);

        when(collision.isWall(targetPos)).thenReturn(false);
        when(collision.getBoxAt(targetPos)).thenReturn(null);
        controller.moveHero(new Position(0, 1));

        verify(hero).setPosition(targetPos);
    }
}