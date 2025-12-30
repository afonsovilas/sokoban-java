package com.sokoban.model;

import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LevelLoaderTest {

    @Test
    void testLoadLevel1() {
        LevelLoader loader = new LevelLoader();
        Level level = loader.loadLevel(1);

        if (level != null) {
            assertNotNull(level.getHero(), "O nível deve ter um herói carregado");
        }
    }

    @Test
    void testLoadValidLevelWithMetadata() {
        LevelLoader loader = new LevelLoader();

        List<String> fakeFile = Arrays.asList(
                "Lives: 5",
                "#####",
                "# X #",
                "#####"
        );

        Level level = loader.createLevelFromLines(fakeFile);

        assertNotNull(level);
        assertEquals(5, level.getLives(), "Deve ler o número de vidas corretamente");
        assertEquals(5, level.getWidth());
        assertEquals(3, level.getHeight());
        assertNotNull(level.getHero(), "O herói deve ser criado");
        assertEquals(new Position(2, 1), level.getHero().getPosition());
    }

    @Test
    void testLivesParsingStrippedFromMap() {
        LevelLoader loader = new LevelLoader();

        List<String> fakeLines = Arrays.asList(
                "Lives: 5",
                "#####",
                "# X #",
                "#####"
        );

        Level level = loader.createLevelFromLines(fakeLines);

        assertNotNull(level);
        assertEquals(5, level.getLives());

        assertEquals(3, level.getHeight(), "A linha 'Lives' não deve fazer parte do mapa");
        assertEquals(5, level.getWidth());
    }

    @Test
    void testLoadLevelUsingDefaults() {
        LevelLoader loader = new LevelLoader();

        List<String> fakeFile = Arrays.asList(
                "###",
                "#X#",
                "###"
        );

        Level level = loader.createLevelFromLines(fakeFile);

        assertNotNull(level);
        assertEquals(3, level.getLives(), "Deve assumir 3 vidas se não especificado");
    }

    @Test
    void testLoadEmptyLevel() {
        LevelLoader loader = new LevelLoader();

        Level level = loader.createLevelFromLines(Collections.emptyList());

        assertNull(level, "Deve retornar null se a lista estiver vazia");
    }

    @Test
    void testLoadInvalidLivesFormat() {
        LevelLoader loader = new LevelLoader();

        List<String> fakeFile = Arrays.asList(
                "Lives: INVALIDO",
                "###",
                "#X#",
                "###"
        );

        Level level = loader.createLevelFromLines(fakeFile);

        assertNotNull(level);
        assertEquals(3, level.getLives(), "Deve ignorar o erro de parsing e usar default (3)");
    }

    @Test
    void testLoadNonExistentFileReturnsNull() {
        LevelLoader loader = new LevelLoader();
        Level level = loader.loadLevel(9999);
        assertNull(level);
    }

    @Test
    void testInitializationLogic() {
        LevelLoader loader = new LevelLoader();
        List<String> fakeLines = Arrays.asList("###", "#X#", "###");

        Level level = loader.createLevelFromLines(fakeLines);

        assertNotNull(level);
        assertNotNull(level.getHero());
    }
}