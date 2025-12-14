package com.sokoban.controller;

import com.sokoban.model.*;

public class Collision {
    private Level level;

    public Collision(Level level) {
        this.level = level;
    }

    public boolean isWall(Position position) {
        for (Wall wall : level.getWalls()) {
            if (wall.getPosition().equals(position)) {
                return true;
            }
        }
        return false;
    }

    public Box getBoxAt(Position position) {
        for (Box box : level.getBoxes()) {
            if (box.getPosition().equals(position)) {
                return box;
            }
        }
        return null;
    }

    public Portal getPortalAt(Position position) {
        for (Portal portal : level.getPortals()) {
            if (portal.getPosition().equals(position)) {
                return portal;
            }
        }
        return null;
    }

    public Portal getOtherPortal(Portal currentPortal) {
        for (Portal p : level.getPortals()) {
            if (!p.getPosition().equals(currentPortal.getPosition())) {
                return p;
            }
        }
        return null;
    }

    public PowerUp getPowerUpAt(Position position) {
        for (PowerUp p : level.getPowerUps()) {
            if (p.getPosition().equals(position)) {
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