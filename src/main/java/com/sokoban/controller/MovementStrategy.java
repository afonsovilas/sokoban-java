package com.sokoban.controller;

import com.sokoban.controller.Collision;
import com.sokoban.model.Hero;
import com.sokoban.model.Position;

public interface MovementStrategy {
    void move(Hero hero, Position direction, Collision collision);
}