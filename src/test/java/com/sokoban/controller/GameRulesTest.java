package com.sokoban.controller;

import com.sokoban.model.Box;
import com.sokoban.controller.GameRules;
import com.sokoban.model.Level;
import com.sokoban.model.Target;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GameRulesTest {

    private GameRules gameRules;
    private Level level;

    @BeforeEach
    void setUp() {
        gameRules = new GameRules();
        level = Mockito.mock(Level.class);
    }

    @Test
    void testWinCondition_SingleBox() {
        Box box = new Box(5, 5);
        Target target = new Target(5, 5);

        when(level.getBoxes()).thenReturn(List.of(box));
        when(level.getTargets()).thenReturn(List.of(target));

        assertTrue(gameRules.checkWin(level), "Deve ganhar quando a caixa está no alvo");
    }

    @Test
    void testLoseCondition_SingleBox() {
        Box box = new Box(5, 5);
        Target target = new Target(6, 6);

        when(level.getBoxes()).thenReturn(List.of(box));
        when(level.getTargets()).thenReturn(List.of(target));

        assertFalse(gameRules.checkWin(level), "Não deve ganhar quando a caixa não está no alvo");
    }

    @Test
    void testWinCondition_MultipleBoxes() {
        Box box1 = new Box(1, 1);
        Box box2 = new Box(2, 2);

        Target target1 = new Target(1, 1);
        Target target2 = new Target(2, 2);

        when(level.getBoxes()).thenReturn(List.of(box1, box2));
        when(level.getTargets()).thenReturn(List.of(target1, target2));

        assertTrue(gameRules.checkWin(level), "Deve ganhar quando TODAS as caixas estão nos alvos");
    }

    @Test
    void testPartialWin_NotFinished() {
        Box box1 = new Box(1, 1);
        Box box2 = new Box(3, 3);

        Target target1 = new Target(1, 1);
        Target target2 = new Target(2, 2);

        when(level.getBoxes()).thenReturn(List.of(box1, box2));
        when(level.getTargets()).thenReturn(List.of(target1, target2));

        assertFalse(gameRules.checkWin(level), "Não deve ganhar se faltar colocar alguma caixa");
    }

    @Test
    void testEmptyLevel() {
        when(level.getBoxes()).thenReturn(new ArrayList<>());
        when(level.getTargets()).thenReturn(new ArrayList<>());

        assertFalse(gameRules.checkWin(level), "Nível vazio não deve ser considerado vitória automática");
    }
}
