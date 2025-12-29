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

        level.controller = controllerMock;
        level.gameRules = gameRulesMock;

        level.boxes = new ArrayList<>();
        level.powerUps = new ArrayList<>();
        level.targets = new ArrayList<>();

    }


    @Test
    void cannotAddElementOutOfBounds() {
        assertThrows(IllegalArgumentException.class, () -> {
            level.addElement(new Wall(11, 11));
        });

        assertThrows(IllegalArgumentException.class, () -> {
            level.addElement(new Hero(-1, 5));
        });
    }


    @Test
    void processKeyShouldDelegateToController() {
        level.processKey(new KeyStroke(KeyType.ArrowUp));

        verify(controllerMock, times(1)).moveHero(eq(new Position(0, -1)));

        assertEquals(0, level.getSteps(), "Se o herói não muda de sítio (mock default), passos não contam");
    }

    @Test
    void gameShouldFinishWhenRulesSaySo() {

        when(gameRulesMock.checkWin(level)).thenReturn(true);

        doAnswer(inv -> { hero.setPosition(new Position(5, 4)); return null; })
                .when(controllerMock).moveHero(any());

        level.processKey(new KeyStroke(KeyType.ArrowUp));

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

        doAnswer(inv -> {
            hero.setPosition(movedPos);
            return null;
        }).when(controllerMock).moveHero(any());

        level.processKey(new KeyStroke(KeyType.ArrowUp));

        assertEquals(1, level.getSteps());
        assertEquals(movedPos, hero.getPosition());

        level.processKey(new KeyStroke('u', false, false));

        assertEquals(0, level.getSteps());
        assertEquals(startPos, hero.getPosition());
        assertEquals(2, level.getLives());
    }

    @Test
    void loadLevelShouldClearExistingElements() {
        level.addElement(new Wall(0, 0));
        level.addElement(new Box(0, 0));
        assertFalse(level.getWalls().isEmpty());

        String[] emptyMap = {"   "};
        level.loadLevel(emptyMap);

        assertTrue(level.getWalls().isEmpty(), "Deve limpar paredes antigas");
        assertTrue(level.getBoxes().isEmpty(), "Deve limpar caixas antigas");
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

        doAnswer(inv -> {
            hero.setPosition(newHeroPos);
            box.setPosition(new Position(3, 2));
            return null;
        }).when(controllerMock).moveHero(any());

        level.processKey(new KeyStroke(KeyType.ArrowUp));

        assertEquals(newHeroPos, hero.getPosition());

        reset(controllerMock);
        level.processKey(new KeyStroke('u', false, false));

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