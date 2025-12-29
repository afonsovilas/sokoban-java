package com.sokoban.model;

import com.sokoban.controller.Collision;
import com.sokoban.controller.GameRules;
import com.sokoban.controller.HeroController;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Level {
    protected int width;
    protected int height;
    private int steps = 0;
    private boolean isFinished = false;

    protected Hero hero;
    protected List<Wall> walls;
    protected List<Box> boxes;
    protected List<Target> targets;
    protected List<Portal> portals;
    protected GameRules gameRules;
    protected HeroController controller;
    protected Collision collision;
    protected List<PowerUp> powerUps;

    private int lives = 3;
    private Stack<LevelMemento> history = new Stack<>();

    public Level(int width, int height) {
        this.width = width;
        this.height = height;
        this.walls = new ArrayList<>();
        this.boxes = new ArrayList<>();
        this.targets = new ArrayList<>();
        this.portals = new ArrayList<>();
        this.gameRules = new GameRules();
        this.powerUps = new ArrayList<>();
    }

    public void setController(HeroController controller) {
        this.controller = controller;
    }

    public void addElement(Element element) {
        if (element == null) return;
        if (element instanceof Wall) walls.add((Wall) element);
        else if (element instanceof Box) boxes.add((Box) element);
        else if (element instanceof Hero) this.hero = (Hero) element;
        else if (element instanceof Target) targets.add((Target) element);
        else if (element instanceof Portal) portals.add((Portal) element);
        else if (element instanceof PowerUp) powerUps.add((PowerUp) element);
    }

    public void saveState() {
        long time = (controller != null) ? controller.getRemainingTime() : 0;
        LevelMemento snapshot = new LevelMemento(hero, boxes, powerUps, time);
        history.push(snapshot);
    }

    public void updateGameStatus() {
        this.steps++;
        updateBoxStates();
        if (gameRules.checkWin(this)) {
            this.isFinished = true;
        }
    }

    public void undo() {
        if (lives > 0 && !history.isEmpty() && !isFinished) {
            lives--;
            steps--;
            LevelMemento lastState = history.pop();
            hero.setPosition(lastState.getHeroPosition());

            for (Box box : boxes) {
                Position savedPos = lastState.getBoxPositions().get(box);
                if (savedPos != null) box.setPosition(savedPos);
            }
            this.powerUps = new ArrayList<>(lastState.getActivePowerUps());
            if (controller != null) controller.setSuperPower(lastState.getRemainingTime());
            updateBoxStates();
        }
    }

    public void updateBoxStates() {
        for (Box box : boxes) {
            boolean isOnTarget = false;
            for (Target target : targets) {
                if (box.getPosition().equals(target.getPosition())) {
                    isOnTarget = true;
                    break;
                }
            }
            box.setOnTarget(isOnTarget);
        }
    }

    public int getWidth() { return width; }
    public int getHeight() { return height; }
    public Hero getHero() { return hero; }
    public List<Wall> getWalls() { return walls; }
    public List<Box> getBoxes() { return boxes; }
    public List<Target> getTargets() { return targets; }
    public List<Portal> getPortals() { return portals; }
    public boolean isFinished() { return isFinished; }
    public List<PowerUp> getPowerUps() { return powerUps; }
    public int getSteps() { return steps; }
    public void removePowerUp(PowerUp p) { powerUps.remove(p); }
    public void setLives(int lives) { this.lives = lives; }
    public int getLives() { return lives; }
    public long getPowerUpTime() {
        if (controller == null) return 0;
        return controller.getSecondsLeft();
    }
}