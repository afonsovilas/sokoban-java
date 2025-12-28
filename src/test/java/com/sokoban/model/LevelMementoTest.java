package com.sokoban.model;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class LevelMementoTest {

    @Test
    void mementoShouldStoreDeepCopyOfHeroPosition() {

        Hero hero = new Hero(10, 10);
        List<Box> boxes = new ArrayList<>();
        List<PowerUp> powerUps = new ArrayList<>();

        LevelMemento memento = new LevelMemento(hero, boxes, powerUps, 100);

        hero.setPosition(new Position(11, 10));


        assertEquals(10, memento.getHeroPosition().getX());
        assertNotEquals(hero.getPosition(), memento.getHeroPosition());
    }

    @Test
    void mementoShouldStoreCopyOfBoxPositions() {

        Box box = new Box(5, 5);
        List<Box> boxes = new ArrayList<>();
        boxes.add(box);

        LevelMemento memento = new LevelMemento(new Hero(0,0), boxes, new ArrayList<>(), 100);

        box.setPosition(new Position(6, 6));

        Position savedPos = memento.getBoxPositions().get(box);
        assertEquals(5, savedPos.getX());
        assertEquals(5, savedPos.getY());
    }
}