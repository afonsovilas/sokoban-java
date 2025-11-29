package com.sokoban.model;

public class Box extends Element {
    private boolean onTarget;

    public Box(int x, int y) {
        super(x, y);
        this.onTarget = false;
    }

    public void setOnTarget(boolean onTarget) {
        this.onTarget = onTarget;
    }

    public boolean isOnTarget() {
        return onTarget;
    }

}
