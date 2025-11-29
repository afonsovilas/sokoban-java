package com.sokoban.model;

import com.sokoban.model.Menu;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MenuTest {
    private Menu menu;

    @BeforeEach
    void setUp() {
        menu = new Menu();
    }

    @Test
    void testInitialization() {
        assertTrue(menu.isSelectedStart());
        assertTrue(menu.isSelected(0));

        assertEquals(3, menu.getNumberEntries());
        assertEquals("Resume", menu.getEntry(1));
        assertEquals("Start Game", menu.getEntry(0));
        assertEquals("Exit", menu.getEntry(2));
    }

    @Test
    void testNextEntry() {
        menu.nextEntry();
        assertTrue(menu.isSelectedResume());

        menu.nextEntry();
        assertTrue(menu.isSelectedExit());

        menu.nextEntry();
        assertTrue(menu.isSelectedStart());
    }

    @Test
    void testPreviousEntry() {

        menu.previousEntry();
        assertTrue(menu.isSelectedExit());

        menu.previousEntry();
        assertTrue(menu.isSelectedResume());

        menu.previousEntry();
        assertTrue(menu.isSelectedStart());
    }
}
