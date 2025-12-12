package com.sokoban.controller;

import com.sokoban.model.Portal;
import com.sokoban.model.Position;
import com.sokoban.model.Box;
import com.sokoban.model.Hero;

public class HeroController {
    private final Hero hero;
    private final Collision collision;

    public HeroController(Hero hero, Collision collision) {
        this.hero = hero;
        this.collision = collision;
    }

    public void moveHero(Position direction) {
        Position currentPos = hero.getPosition();
        Position targetPos = new Position(currentPos.getX() + direction.getX(), currentPos.getY() + direction.getY());
        if (collision.isWall(targetPos)) {
            return;
        }

        Box box = collision.getBoxAt(targetPos);
        if (box != null) {
            if (!pushBox(box, direction)) {
                return;
            }
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

    private boolean pushBox(Box box, Position direction) {
        Position boxPos = box.getPosition();
        Position boxTargetPos = new Position(boxPos.getX() + direction.getX(), boxPos.getY() + direction.getY());

        if (!collision.isFree(boxTargetPos)) {
            return false;
        }
        box.setPosition(boxTargetPos);
        return true;
    }
}