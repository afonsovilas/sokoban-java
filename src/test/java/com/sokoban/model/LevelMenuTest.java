package com.sokoban.model;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.junit.jupiter.api.Assertions.*;

class LevelMenuTest {

    @Test
    void testMenuNavigation() {
        LevelManager managerMock = Mockito.mock(LevelManager.class);
        Mockito.when(managerMock.getMaxUnlockedLevel()).thenReturn(5);
        LevelMenu menu = new LevelMenu(managerMock);

        assertTrue(menu.getNumberLevels() >= 2, "Precisamos de pelo menos 2 níveis nos resources para testar navegação");

        int initialSelect = menu.getSelectedIndex();
        menu.moveRight();
        assertNotEquals(initialSelect, menu.getSelectedIndex(), "Mover para a direita deve alterar a seleção");

    }

    @Test
    void verifyLockedLevels() {
        LevelManager managerMock = Mockito.mock(LevelManager.class);

        Mockito.when(managerMock.getMaxUnlockedLevel()).thenReturn(1);

        LevelMenu menu = new LevelMenu(managerMock);

        assertTrue(menu.isUnlocked(0), "O primeiro nível deve estar sempre desbloqueado");

        assertFalse(menu.isUnlocked(1), "O segundo nível deve estar bloqueado se só passámos o 1");
    }
}
