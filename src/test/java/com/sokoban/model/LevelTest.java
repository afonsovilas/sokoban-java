package com.sokoban.model;

import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.sokoban.model.Box;
import com.sokoban.model.Hero;
import com.sokoban.model.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LevelTest {
    private Level level;

    @BeforeEach
    void setUp() {
        level = new Level(10, 10);
    }

    @Test
    void testProcessKeyMoveHero() {
        String[] map = {
                "#####",
                "# X #",
                "#####"
        };
        level.loadLevel(map);
        level.initialize();
        assertEquals(new Position(2, 1), level.getHero().getPosition());
        level.processKey(new KeyStroke(KeyType.ArrowRight));
        assertEquals(new Position(3, 1), level.getHero().getPosition());
    }

    @Test
    void testProcessKeyIntoWall() {
        String[] map = {
                "###",
                "#X#",
                "###"
        };
        level.loadLevel(map);
        level.initialize();
        level.processKey(new KeyStroke(KeyType.ArrowUp));
        assertEquals(new Position(1, 1), level.getHero().getPosition());
    }

    @Test
    void testProcessKeyArrowDown() {
        String[] map = {
                "   ",
                " X ",
                "   "
        };
        level.loadLevel(map);
        level.initialize();

        assertEquals(new Position(1, 1), level.getHero().getPosition());
        level.processKey(new KeyStroke(KeyType.ArrowDown));
        assertEquals(new Position(1, 2), level.getHero().getPosition());
    }

    @Test
    void testProcessKeyArrowLeft() {
        String[] map = {
                "   ",
                " X ",
                "   "
        };
        level.loadLevel(map);
        level.initialize();
        level.processKey(new KeyStroke(KeyType.ArrowLeft));
        assertEquals(new Position(0, 1), level.getHero().getPosition());
    }

    @Test
    void testUpdateBoxStates() {
        String[] map = {
                "#####",
                "#XO*#",
                "#####"
        };
        level.loadLevel(map);
        level.initialize();

        Box box = level.getBoxes().get(0);

        assertFalse(box.isOnTarget());
        level.processKey(new KeyStroke(KeyType.ArrowRight));
        assertEquals(new Position(3, 1), box.getPosition());
        assertTrue(box.isOnTarget(), "A caixa devia estar marcada como 'no alvo'");
    }

    @Test
    void testIsFinished() {
        String[] map = {
                "#####",
                "#XO*#",
                "#####"
        };
        level.loadLevel(map);
        level.initialize();

        assertFalse(level.isFinished(), "O nível não deve começar acabado");
        level.processKey(new KeyStroke(KeyType.ArrowRight));
        assertTrue(level.isFinished(), "O nível devia ter terminado");
    }

    @Test
    void testInvalidKeys() {
        String[] map = {"#X#"};
        level.loadLevel(map);
        level.initialize();

        Position initialPos = level.getHero().getPosition();
        level.processKey(new KeyStroke('a', false, false));
        assertEquals(initialPos, level.getHero().getPosition());
        level.processKey(new KeyStroke(KeyType.EOF));
        assertEquals(initialPos, level.getHero().getPosition());
    }
}
