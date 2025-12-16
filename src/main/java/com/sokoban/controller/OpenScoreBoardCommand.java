package com.sokoban.controller;

import com.sokoban.Game;
import com.sokoban.controller.Command;
import com.sokoban.model.LevelManager;
import com.sokoban.states.ScoreBoardState;
import com.sokoban.utils.ScoreManager;
import java.util.Map;

public class OpenScoreBoardCommand implements Command {
    private final LevelManager levelManager;

    public OpenScoreBoardCommand(LevelManager levelManager) {
        this.levelManager = levelManager;
    }

    @Override
    public void execute(Game game) {
        // Usa o loadScores() do teu ficheiro ScoreManager
        Map<Integer, Integer> scores = ScoreManager.loadScores();
        game.setState(new ScoreBoardState(scores, levelManager));
    }
}
