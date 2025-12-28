package com.sokoban.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.File;
import static org.junit.jupiter.api.Assertions.*;

class LevelManagerTest {

    private static final String TEST_FILE = "test_progress.txt";

    @BeforeEach
    @AfterEach
    void cleanUp() {
        File file = new File(TEST_FILE);
        if (file.exists()) {
            file.delete();
        }
    }

    @Test
    void newManagerShouldStartAtLevel1() {
        LevelManager manager = new LevelManager(TEST_FILE);
        assertEquals(1, manager.getMaxUnlockedLevel());
        assertTrue(manager.isLevelUnlocked(1));
        assertFalse(manager.isLevelUnlocked(2));
    }

    @Test
    void unlockNextLevelShouldAdvanceProgress() {
        LevelManager manager = new LevelManager(TEST_FILE);

        manager.unlockNextLevel(1);

        assertEquals(2, manager.getMaxUnlockedLevel());
        assertTrue(manager.isLevelUnlocked(2));
    }

    @Test
    void unlockPreviousLevelShouldNotAdvanceProgress() {
        LevelManager manager = new LevelManager(TEST_FILE);
        manager.unlockNextLevel(1);

        manager.unlockNextLevel(1);

        assertEquals(2, manager.getMaxUnlockedLevel());
    }

    @Test
    void persistenceCheck() {

        LevelManager manager1 = new LevelManager(TEST_FILE);
        manager1.unlockNextLevel(1);

        LevelManager manager2 = new LevelManager(TEST_FILE);

        assertEquals(2, manager2.getMaxUnlockedLevel(), "O progresso deve ser recuperado do ficheiro");
    }

    @Test
    void shouldNotUnlockRandomLevels() {
        LevelManager manager = new LevelManager(TEST_FILE);
        manager.unlockNextLevel(5);
        assertEquals(1, manager.getMaxUnlockedLevel());
    }
}