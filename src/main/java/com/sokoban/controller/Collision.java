package com.sokoban.controller;

import com.sokoban.model.Level;
import com.sokoban.model.Position;
import com.sokoban.model.Box;
import com.sokoban.model.Wall;

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

    public boolean isFree(Position position) {
        return !isWall(position) && getBoxAt(position) == null;
    }
}