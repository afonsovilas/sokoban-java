package com.sokoban.controller;

import com.sokoban.controller.Collision;
import com.sokoban.model.Box;
import com.sokoban.model.Hero;
import com.sokoban.model.Portal;
import com.sokoban.model.Position;

public class NormalMovementStrategy implements MovementStrategy {

    @Override
    public void move(Hero hero, Position direction, Collision collision) {
        Position currentPos = hero.getPosition();
        Position targetPos = new Position(currentPos.getX() + direction.getX(), currentPos.getY() + direction.getY());

        if (collision.isWall(targetPos)) return;

        Box box = collision.getBoxAt(targetPos);
        if (box != null) {
            if (!pushBox(box, direction, collision)) return;
        }

        Portal portal = collision.getPortalAt(targetPos);
        if (portal != null) {
            Portal destination = collision.getOtherPortal(portal);
            if (destination != null && collision.getBoxAt(destination.getPosition()) == null) {
                targetPos = destination.getPosition();
            }
        }

        hero.setPosition(targetPos);
    }

    protected boolean pushBox(Box box, Position direction, Collision collision) {
        Position boxPos = box.getPosition();
        Position boxTargetPos = new Position(boxPos.getX() + direction.getX(), boxPos.getY() + direction.getY());

        Portal portal = collision.getPortalAt(boxTargetPos);
        if (portal != null) {
            Portal destination = collision.getOtherPortal(portal);
            if (destination != null) {
                Position exitPos = new Position(
                        destination.getPosition().getX() + direction.getX(),
                        destination.getPosition().getY() + direction.getY()
                );
                if (collision.isFree(exitPos)) boxTargetPos = exitPos;
                else return false;
            }
        }

        if (!collision.isFree(boxTargetPos)) return false;

        box.setPosition(boxTargetPos);
        return true;
    }
}