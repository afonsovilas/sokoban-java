package com.sokoban.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LevelMemento {
    private final Position heroPosition;
    private final Map<Box, Position> boxPositions;
    private final List<PowerUp> activePowerUps;
    private final long remainingTime;

    public LevelMemento(Hero hero, List<Box> boxes, List<PowerUp> powerUps,long remainingTime) {
        this.heroPosition = new Position(hero.getPosition().getX(), hero.getPosition().getY());

        this.boxPositions = new HashMap<>();
        for (Box box : boxes) {
            this.boxPositions.put(box, new Position(box.getPosition().getX(), box.getPosition().getY()));
        }
        this.activePowerUps = new ArrayList<>(powerUps);
        this.remainingTime = remainingTime;
    }

    public Position getHeroPosition() {
        return heroPosition;
    }

    public Map<Box, Position> getBoxPositions() {
        return boxPositions;
    }

    public List<PowerUp> getActivePowerUps() {
        return activePowerUps;
    }

    public long getRemainingTime() { return remainingTime; }
}