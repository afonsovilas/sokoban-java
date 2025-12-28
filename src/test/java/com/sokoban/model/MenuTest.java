package com.sokoban.model;

import com.sokoban.controller.Command;
import net.jqwik.api.*;
import net.jqwik.api.constraints.IntRange;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MenuTest {


    @Test
    void nextEntryShouldLoopToStart() {
        Menu menu = new Menu(createDummyEntries(3));

        menu.nextEntry();
        assertTrue(menu.isSelected(1));
        menu.nextEntry();
        assertTrue(menu.isSelected(2));
        menu.nextEntry();
        assertTrue(menu.isSelected(0));
    }

    @Test
    void previousEntryShouldLoopToEnd() {
        Menu menu = new Menu(createDummyEntries(3));

        menu.previousEntry();
        assertTrue(menu.isSelected(2));
    }

    // --- PROPERTY BASED TESTING (PBT) ---
    // Estes testes geram centenas de casos aleatórios para garantir a robustez matemática

    @Property
    void navigatingDownArbitraryTimesShouldLandOnCorrectIndex(
            @ForAll @IntRange(min = 1, max = 100) int numEntries,
            @ForAll @IntRange(min = 0, max = 1000) int moves) {

        Menu menu = new Menu(createDummyEntries(numEntries));

        for (int i = 0; i < moves; i++) {
            menu.nextEntry();
        }

        int expectedIndex = moves % numEntries;

        assertTrue(menu.isSelected(expectedIndex),
                String.format("Após %d movimentos num menu de %d, devia estar em %d", moves, numEntries, expectedIndex));
    }

    @Property
    void navigatingUpAndDownSameAmountShouldReturnToOrigin(
            @ForAll @IntRange(min = 1, max = 20) int numEntries,
            @ForAll @IntRange(min = 1, max = 100) int moves) {

        Menu menu = new Menu(createDummyEntries(numEntries));
        int initialSelection = 0; // Assume start at 0

        for (int i = 0; i < moves; i++) menu.nextEntry();
        for (int i = 0; i < moves; i++) menu.previousEntry();

        assertTrue(menu.isSelected(initialSelection), "Devia voltar à origem se subir e descer o mesmo nº de vezes");
    }

    private List<Menu.MenuEntry> createDummyEntries(int count) {
        Menu.MenuEntry[] entries = new Menu.MenuEntry[count];
        for (int i = 0; i < count; i++) {
            entries[i] = new Menu.MenuEntry("Option " + i, Mockito.mock(Command.class));
        }
        return Arrays.asList(entries);
    }
}