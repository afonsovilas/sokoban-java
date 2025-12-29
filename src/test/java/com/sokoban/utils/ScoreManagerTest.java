package com.sokoban.utils;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ScoreManagerTest {

    private File tempFile;
    private final String originalFileName = "src/main/resources/levels/scores.txt";

    @BeforeEach
    void setUp() throws Exception {
        tempFile = File.createTempFile("test_scores", ".txt");
        ScoreManager.setFileName(tempFile.getAbsolutePath());
    }

    @AfterEach
    void tearDown() {
        ScoreManager.setFileName(originalFileName);

        if (tempFile.exists()) {
            tempFile.delete();
        }
    }

    @Test
    void loadScoresShouldReturnEmptyMapIfFileIsEmpty() {
        Map<Integer, Integer> scores = ScoreManager.loadScores();
        assertTrue(scores.isEmpty(), "Deve retornar mapa vazio se o ficheiro estiver vazio");
    }

    @Test
    void saveScoreShouldWriteNewScore() {
        ScoreManager.saveScore(1, 100);

        Map<Integer, Integer> scores = ScoreManager.loadScores();

        assertEquals(1, scores.size());
        assertEquals(100, scores.get(1));
    }

    @Test
    void saveScoreShouldUpdateIfBetterScore() {
        ScoreManager.saveScore(1, 100);

        ScoreManager.saveScore(1, 150);
        assertEquals(100, ScoreManager.loadScores().get(1));

        ScoreManager.saveScore(1, 50);
        assertEquals(50, ScoreManager.loadScores().get(1));
    }

    @Test
    void saveScoreShouldHandleMultipleLevels() {
        ScoreManager.saveScore(1, 10);
        ScoreManager.saveScore(2, 20);
        ScoreManager.saveScore(5, 50);

        Map<Integer, Integer> scores = ScoreManager.loadScores();

        assertEquals(3, scores.size());
        assertEquals(10, scores.get(1));
        assertEquals(20, scores.get(2));
        assertEquals(50, scores.get(5));
    }

    @Test
    void loadScoresShouldHandleCorruptedLines() throws IOException {
        Files.writeString(tempFile.toPath(), "Lixo\n1:100\nInválido:200\n2:NãoNúmero\n3:300");

        Map<Integer, Integer> scores = ScoreManager.loadScores();

        assertEquals(2, scores.size());
        assertEquals(100, scores.get(1));
        assertEquals(300, scores.get(3));
    }
}