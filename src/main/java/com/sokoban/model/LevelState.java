package com.sokoban.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LevelState {
    private final Position heroPosition;
    private final Map<Box, Position> boxPositions;

    public LevelState(Hero hero, List<Box> boxes) {
        this.heroPosition = new Position(hero.getPosition().getX(), hero.getPosition().getY());

        this.boxPositions = new HashMap<>();
        for (Box box : boxes) {
            this.boxPositions.put(box, new Position(box.getPosition().getX(), box.getPosition().getY()));
        }
    }

    public Position getHeroPosition() {
        return heroPosition;
    }

    public Map<Box, Position> getBoxPositions() {
        return boxPositions;
    }
}