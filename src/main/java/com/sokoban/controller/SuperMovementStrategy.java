package com.sokoban.controller;

import com.sokoban.controller.Collision;
import com.sokoban.model.Box;
import com.sokoban.model.Position;

public class SuperMovementStrategy extends NormalMovementStrategy {

    @Override
    protected boolean pushBox(Box box1, Position direction, Collision collision) {
        Position box1Pos = box1.getPosition();
        Position target1 = new Position(box1Pos.getX() + direction.getX(), box1Pos.getY() + direction.getY());

        Box box2 = collision.getBoxAt(target1);

        if (box2 != null) {
            if (super.pushBox(box2, direction, collision)) {
                return super.pushBox(box1, direction, collision);
            }
            return false;
        }

        return super.pushBox(box1, direction, collision);
    }
}