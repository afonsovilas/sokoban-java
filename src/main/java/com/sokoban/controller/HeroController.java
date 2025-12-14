package com.sokoban.controller;

import com.sokoban.model.*;

public class HeroController {
    private final Hero hero;
    private final Collision collision;
    private MovementStrategy strategy;
    private long powerEndTime = 0;

    public HeroController(Hero hero, Collision collision) {
        this.hero = hero;
        this.collision = collision;
        this.strategy = new NormalMovementStrategy();
    }

    public void moveHero(Position direction) {
        if (System.currentTimeMillis() > powerEndTime && strategy instanceof SuperMovementStrategy) {
            this.strategy = new NormalMovementStrategy();
            System.out.println("Super Power Acabou... De volta ao normal.");
        }

        Position nextPos = new Position(
                hero.getPosition().getX() + direction.getX(),
                hero.getPosition().getY() + direction.getY()
        );

        collectPowerUp(nextPos);
        strategy.move(hero, direction, collision);
    }

    private void collectPowerUp(Position position) {
        PowerUp powerUp = collision.getPowerUpAt(position);
        if (powerUp != null) {
            this.strategy = new SuperMovementStrategy();
            this.powerEndTime = System.currentTimeMillis() + 10000;

            collision.getLevel().removePowerUp(powerUp);
            System.out.println("SUPER POWER ATIVADO! (Strategy Pattern)");
        }
    }
}