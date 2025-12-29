package com.sokoban.model;

import com.sokoban.controller.Command;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.junit.jupiter.api.Assertions.*;

class LevelMenuTest {

    private LevelMenu menu;
    private LevelManager managerMock;

    @BeforeEach
    void setUp() {
        managerMock = Mockito.mock(LevelManager.class);
        Mockito.when(managerMock.getMaxUnlockedLevel()).thenReturn(100);
        menu = new LevelMenu(managerMock);
    }

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
    void testGridNavigationLogic() {
        if (menu.getNumberLevels() < 4) {
            System.out.println("Aviso: Teste de navegação incompleto por falta de ficheiros de nível.");
            return;
        }

        assertEquals(0, menu.getSelectedIndex());

        menu.moveLeft();
        assertEquals(0, menu.getSelectedIndex());

        menu.moveRight();
        assertEquals(1, menu.getSelectedIndex());

        menu.moveRight();
        assertEquals(1, menu.getSelectedIndex());

        menu.moveDown();
        assertEquals(3, menu.getSelectedIndex());

        menu.moveLeft();
        assertEquals(2, menu.getSelectedIndex());

        menu.moveUp();
        assertEquals(0, menu.getSelectedIndex());
        menu.moveUp();
        assertEquals(0, menu.getSelectedIndex());
    }

    @Test
    void testGettersAndCommandExecution() {

        if (menu.getNumberLevels() > 0) {
            assertNotNull(menu.getEntryTitle(0));
            assertTrue(menu.getEntryTitle(0).contains("Level 1"));

            Command cmd = menu.getSelectedCommand();
            assertNotNull(cmd, "Deve haver um comando associado ao nível");


            assertTrue(menu.isSelected(0));
            assertFalse(menu.isSelected(1));
        }
    }

    @Test
    void verifyLockedLevels() {

        Mockito.when(managerMock.getMaxUnlockedLevel()).thenReturn(1);
        LevelMenu lockedMenu = new LevelMenu(managerMock);

        assertTrue(lockedMenu.isUnlocked(0), "O primeiro nível (index 0) deve estar desbloqueado");
        assertFalse(lockedMenu.isUnlocked(1), "O segundo nível (index 1) deve estar bloqueado");
    }

    @Test
    void testMoveDownBottomBoundary() {
        if (menu.getNumberLevels() > 0) {
            int maxRows = (menu.getNumberLevels() / 2) + 2;

            for (int i = 0; i < maxRows; i++) {
                menu.moveDown();
            }

            int indexBefore = menu.getSelectedIndex();
            menu.moveDown();
            assertEquals(indexBefore, menu.getSelectedIndex(), "Não deve passar do limite inferior");
        }
    }
}
