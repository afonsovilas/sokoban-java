package com.sokoban.controller;

import com.sokoban.model.Hero;
import com.sokoban.model.Position;
import com.sokoban.model.PowerUp;

public class HeroController {
    private final Hero hero;
    private final Collision collision;
    private final PowerUpManager powerUpManager;

    public HeroController(Hero hero, Collision collision) {
        this.hero = hero;
        this.collision = collision;
        this.powerUpManager = new PowerUpManager();
    }

    public void moveHero(Position direction) {
        powerUpManager.update();

        Position nextPos = new Position(
                hero.getPosition().getX() + direction.getX(),
                hero.getPosition().getY() + direction.getY()
        );

        collectPowerUp(nextPos);
        powerUpManager.getStrategy().move(hero, direction, collision);
    }

    private void collectPowerUp(Position position) {
        PowerUp powerUp = collision.getPowerUpAt(position);
        if (powerUp != null) {
            powerUpManager.activateSuperPower();
            collision.getLevel().removePowerUp(powerUp);
        }
    }

    public boolean hasSuperPower() {
        return powerUpManager.isActive();
    }

    public long getSecondsLeft() {
        return powerUpManager.getSecondsLeft();
    }

    public long getRemainingTime() {
        return powerUpManager.getRemainingMillis();
    }

    public void setSuperPower(long duration) {
        powerUpManager.setPowerDuration(duration);
    }
}