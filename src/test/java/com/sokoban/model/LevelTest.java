package com.sokoban.model;

import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.sokoban.controller.GameRules;
import com.sokoban.controller.HeroController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Stack;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class LevelTest {
    private Level level;
    private HeroController controllerMock;
    private GameRules gameRulesMock;
    private Hero hero;

    @BeforeEach
    void setUp() {

        level = new Level(10, 10);
        hero = new Hero(5, 5);
        level.addElement(hero);

        controllerMock = Mockito.mock(HeroController.class);
        gameRulesMock = Mockito.mock(GameRules.class);

        level.setController(controllerMock);
        level.gameRules = gameRulesMock;

        level.boxes = new ArrayList<>();
        level.powerUps = new ArrayList<>();
        level.targets = new ArrayList<>();

    }


    @Test
    void cannotAddElementOutOfBounds() {
        try {
            level.addElement(new Wall(11, 11));
        } catch (Exception e) {
            assertTrue(e instanceof IllegalArgumentException || e instanceof IndexOutOfBoundsException);
        }
    }

    @Test
    void gameShouldFinishWhenRulesSaySo() {

        when(gameRulesMock.checkWin(level)).thenReturn(true);

        level.updateGameStatus();

        assertTrue(level.isFinished());
    }

    @Test
    void boxShouldBeOnTargetWhenCoordinatesMatch() {
        // Arrange manual (sem loadLevel)
        Box box = new Box(2, 2);
        Target target = new Target(2, 2);
        level.addElement(box);
        level.addElement(target);

        level.updateBoxStates();

        assertTrue(box.isOnTarget());
    }

    @Test
    void undoShouldRevertPositionAndSteps() {
        Position startPos = new Position(5, 5);
        Position movedPos = new Position(5, 4);

        level.saveState();

        hero.setPosition(movedPos);
        level.updateGameStatus();

        assertEquals(1, level.getSteps());
        assertEquals(movedPos, hero.getPosition());

        level.undo();

        assertEquals(0, level.getSteps());
        assertEquals(startPos, hero.getPosition());
        assertEquals(2, level.getLives());
    }

    @Test
    void undoShouldRestoreFullState() {
        Box box = new Box(2, 2);
        Target target = new Target(2, 2);
        level.addElement(box);
        level.addElement(target);
        level.updateBoxStates();
        assertTrue(box.isOnTarget());

        when(controllerMock.getRemainingTime()).thenReturn(5000L);

        Position originalHeroPos = hero.getPosition();
        Position newHeroPos = new Position(5, 4);

        level.saveState();

        hero.setPosition(new Position(5, 4));
        box.setPosition(new Position(3, 2));
        level.updateGameStatus();

        assertFalse(box.isOnTarget());

        reset(controllerMock);
        level.undo();

        assertEquals(new Position(2, 2), box.getPosition());
        verify(controllerMock).setSuperPower(5000L);

        assertTrue(box.isOnTarget());
    }

    @Test
    void testGettersAndPowerUpTime() {

        assertNotNull(level.getWalls());
        assertNotNull(level.getBoxes());
        assertNotNull(level.getTargets());
        assertNotNull(level.getPortals());
        assertNotNull(level.getPowerUps());

        when(controllerMock.getSecondsLeft()).thenReturn(10L);
        assertEquals(10L, level.getPowerUpTime());
    }
}