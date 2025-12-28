package com.sokoban.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BoxTest {

    @Test
    void boxShouldSharePositionWithTargetWhenCoordinatesAreSame() {

        Box box = new Box(5, 5);
        Target target = new Target(5, 5);

        assertEquals(box.getPosition(), target.getPosition(),
                "A posição da caixa e do target devem ser iguais se as coordenadas forem as mesmas");
    }

    @Test
    void boxShouldNotSharePositionWithTargetWhenCoordinatesDiffer() {
        Box box = new Box(5, 5);
        Target target = new Target(5, 6);

        assertNotEquals(box.getPosition(), target.getPosition(),
                "As posições devem ser diferentes se as coordenadas forem diferentes");
    }

    @Test
    void boxShouldUpdateOnTargetState() {

        Box box = new Box(1, 1);
        Target target = new Target(1, 1);

        assertFalse(box.isOnTarget());

        if (box.getPosition().equals(target.getPosition())) {
            box.setOnTarget(true);
        }

        assertTrue(box.isOnTarget(), "Box deve ficar 'onTarget' quando o estado é atualizado");
    }

    @Test
    void boxOnTargetShouldDependOnEqualsNotReference() {
        Box box = new Box(5, 5);
        Target target = new Target(5, 5);

        box.setPosition(new Position(5, 5));
        target.setPosition(new Position(5, 5));

        assertEquals(box.getPosition(), target.getPosition());
    }
}
