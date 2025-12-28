package com.sokoban.model;

import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
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

    public void addElement(Element element) {
        if (element == null) return;

        Position p = element.getPosition();

        if (p.getX() < 0 || p.getX() >= width || p.getY() < 0 || p.getY() >= height) {
            throw new IllegalArgumentException("Elemento na posição (" + p.getX() + "," + p.getY() +
                    ") está fora dos limites do nível (" + width + "x" + height + ")");
        }

        if (element instanceof Wall) {
            walls.add((Wall) element);
        } else if (element instanceof Box) {
            boxes.add((Box) element);
        } else if (element instanceof Hero) {
            this.hero = (Hero) element;
        } else if (element instanceof Target) {
            targets.add((Target) element);
        } else if (element instanceof Portal) {
            portals.add((Portal) element);
        } else if (element instanceof PowerUp) {
            powerUps.add((PowerUp) element);
        }
    }

    public void loadLevel(String[] levelDesign) {
        int maxWidth = 0;
        for (String line : levelDesign) {
            if (line.length() > maxWidth) {
                maxWidth = line.length();
            }
        }

        this.width = maxWidth;
        this.height = levelDesign.length;

        walls.clear(); boxes.clear(); targets.clear(); portals.clear(); powerUps.clear();

        for (int y = 0; y < height; y++) {
            String line = levelDesign[y];
            for (int x = 0; x < width; x++) {
                if (x >= line.length()) break;

                char c = line.charAt(x);
                Element element = ElementFactory.createElement(c, x, y);

                if (element != null) {
                    addElement(element);
                }
            }
        }
    }

    public void initialize() {
        this.collision = new Collision(this);
        this.controller = new HeroController(this.hero, this.collision);
    }


    public void processKey(KeyStroke key) {
        if (controller == null) return;

        if (key.getKeyType() == KeyType.Character && (key.getCharacter() == 'u' || key.getCharacter() == 'U')) {
            undo();
            return;
        }

        Position direction = null;

        switch (key.getKeyType()) {
            case ArrowUp -> direction = new Position(0, -1);
            case ArrowDown -> direction = new Position(0, 1);
            case ArrowLeft -> direction = new Position(-1, 0);
            case ArrowRight -> direction = new Position(1, 0);
        }

        if (direction != null) {
            long time = controller.getRemainingTime();
            LevelMemento snapshot = new LevelMemento(hero, boxes, powerUps,time);
            Position before = hero.getPosition();
            controller.moveHero(direction);
            if (!hero.getPosition().equals(before)) {
                history.push(snapshot);
                this.steps++;
                updateBoxStates();
                if (gameRules.checkWin(this)) {
                    this.isFinished = true;
                }
            }
        }
    }

    private void undo() {
        if (lives > 0 && !history.isEmpty() && !isFinished) {
            lives--;
            steps--;

            LevelMemento lastState = history.pop();

            hero.setPosition(lastState.getHeroPosition());

            for (Box box : boxes) {
                Position savedPos = lastState.getBoxPositions().get(box);
                if (savedPos != null) {
                    box.setPosition(savedPos);
                }
            }
            this.powerUps = new ArrayList<>(lastState.getActivePowerUps());
            controller.setSuperPower(lastState.getRemainingTime());
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
    public boolean isFinished() {return isFinished; }
    public List<PowerUp> getPowerUps() { return powerUps; }
    public int getSteps() { return steps; }
    public void removePowerUp(PowerUp p) {
        powerUps.remove(p);
    }
    public void setLives(int lives) {this.lives = lives;}
    public int getLives() {return lives;}
    public long getPowerUpTime() {
        if (controller == null) return 0;
        return controller.getSecondsLeft();
    }
}

