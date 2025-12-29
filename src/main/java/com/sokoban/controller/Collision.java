package com.sokoban.controller;

import com.sokoban.model.*;
import java.util.List;

public class Collision {
    private final Level level;

    public Collision(Level level) {
        this.level = level;
    }

    private <T extends Element> T getElementAt(List<T> elements, Position position) {
        for (T element : elements) {
            if (element.getPosition().equals(position)) {
                return element;
            }
        }
        return null;
    }

    public boolean isWall(Position position) {
        return getElementAt(level.getWalls(), position) != null;
    }

    public Box getBoxAt(Position position) {
        return getElementAt(level.getBoxes(), position);
    }

    public Portal getPortalAt(Position position) {
        return getElementAt(level.getPortals(), position);
    }

    public PowerUp getPowerUpAt(Position position) {
        return getElementAt(level.getPowerUps(), position);
    }

    public Portal getOtherPortal(Portal currentPortal) {
        for (Portal p : level.getPortals()) {
            if (!p.getPosition().equals(currentPortal.getPosition())) {
                return p;
            }
        }
        return null;
    }

    public Level getLevel() { return level; }

    public boolean isFree(Position position) {
        return !isWall(position) && getBoxAt(position) == null;
    }
}