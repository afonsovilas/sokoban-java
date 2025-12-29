package com.sokoban.controller;

import com.sokoban.model.PowerUp;

public class PowerUpManager {
    private MovementStrategy strategy;
    private long powerEndTime;

    public PowerUpManager() {
        this.strategy = new NormalMovementStrategy();
        this.powerEndTime = 0;
    }

    public void update() {
        if (isActive() && System.currentTimeMillis() > powerEndTime) {
            deactivate();
        }
    }

    public void activateSuperPower() {
        setPowerDuration(10000);
    }


    public void setPowerDuration(long durationMillis) {
        if (durationMillis > 0) {
            this.strategy = new SuperMovementStrategy();
            this.powerEndTime = System.currentTimeMillis() + durationMillis;
        } else {
            deactivate();
        }
    }

    public void deactivate() {
        this.strategy = new NormalMovementStrategy();
        this.powerEndTime = 0;
    }

    public boolean isActive() {
        return strategy instanceof SuperMovementStrategy;
    }

    public MovementStrategy getStrategy() {
        return strategy;
    }

    public long getSecondsLeft() {
        if (!isActive()) return 0;
        long millisLeft = powerEndTime - System.currentTimeMillis();
        return Math.max(0, millisLeft / 1000);
    }

    public long getRemainingMillis() {
        if (!isActive()) return 0;
        return Math.max(0, powerEndTime - System.currentTimeMillis());
    }
}