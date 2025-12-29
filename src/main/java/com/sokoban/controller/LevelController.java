package com.sokoban.controller;

import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.sokoban.model.Level;
import com.sokoban.model.Position;

public class LevelController {
    private final Level level;
    private final HeroController heroController;

    public LevelController(Level level) {
        this.level = level;
        this.heroController = new HeroController(level.getHero(), new Collision(level));
        this.level.setController(this.heroController);
    }

    public void handleInput(KeyStroke key) {
        if (level.isFinished()) return;

        if (key.getKeyType() == KeyType.Character && (key.getCharacter() == 'u' || key.getCharacter() == 'U')) {
            level.undo();
            return;
        }

        Position direction = null;
        switch (key.getKeyType()) {
            case ArrowUp -> direction = new Position(0, -1);
            case ArrowDown -> direction = new Position(0, 1);
            case ArrowLeft -> direction = new Position(-1, 0);
            case ArrowRight -> direction = new Position(1, 0);
        }

        if (direction != null) {

            level.saveState();
            heroController.moveHero(direction);
            level.updateGameStatus();
        }
    }

    public HeroController getHeroController() {
        return heroController;
    }
}
