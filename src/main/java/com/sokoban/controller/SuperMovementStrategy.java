package com.sokoban.controller;

import com.sokoban.controller.Collision;
import com.sokoban.model.Box;
import com.sokoban.model.Portal;
import com.sokoban.model.Position;

public class SuperMovementStrategy extends NormalMovementStrategy {

    @Override
    protected boolean pushBox(Box box1, Position direction, Collision collision) {
        Position box1Pos = box1.getPosition();
        Position target1 = new Position(box1Pos.getX() + direction.getX(), box1Pos.getY() + direction.getY());

        Box box2 = collision.getBoxAt(target1);

        if (box2 == null) {
            Portal portal = collision.getPortalAt(target1);
            if (portal != null) {
                Portal destination = collision.getOtherPortal(portal);
                if (destination != null) {
                    Position exitPos = new Position(
                            destination.getPosition().getX() + direction.getX(),
                            destination.getPosition().getY() + direction.getY()
                    );
                    box2 = collision.getBoxAt(exitPos);
                }
            }
        }

        if (box2 != null) {
            return pushTwoBoxes(box1, box2, direction, collision);
        }

        return super.pushBox(box1, direction, collision);
    }

    private boolean pushTwoBoxes(Box box1, Box box2, Position direction, Collision collision) {
        Position originalBox2Pos = new Position(box2.getPosition().getX(), box2.getPosition().getY());
        if (super.pushBox(box2, direction, collision)) {

            if (!super.pushBox(box1, direction, collision)) {

                if (super.pushBox(box2, direction, collision)) {
                    if (super.pushBox(box1, direction, collision)) {
                        return true;
                    }
                }

                box2.setPosition(originalBox2Pos);
                return false;
            }
            return true;
        }
        return false;
    }
}