import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;

import java.util.ArrayList;
import java.util.List;

public class Level {
    protected int width;
    protected int height;

    private boolean isFinished = false;

    protected Hero hero;
    protected List<Wall> walls;
    protected List<Box> boxes;
    protected List<Target> targets;
    protected GameRules gameRules;
    protected HeroController controller;
    protected Collision collision;


    public Level(int width, int height) {
        this.width = width;
        this.height = height;
        this.walls = new ArrayList<>();
        this.boxes = new ArrayList<>();
        this.targets = new ArrayList<>();
        this.gameRules = new GameRules();
    }

    public void loadLevel(String[] levelDesign) {
        this.width = levelDesign[0].length();
        this.height = levelDesign.length;

        for (int y = 0; y < height; y++) {
            String line = levelDesign[y];
            for (int x = 0; x < width; x++) {
                if (x >= line.length()) break;

                char c = line.charAt(x);
                Element element = ElementCreation.createElement(c, x, y);

                if (element != null) {
                    if (element instanceof Wall) walls.add((Wall) element);
                    else if (element instanceof Box) boxes.add((Box) element);
                    else if (element instanceof Hero) hero = (Hero) element;
                    else if (element instanceof Target) targets.add((Target) element);
                }
            }
        }
    }

    public void initialize() {
        this.collision = new Collision(this);
        this.controller = new HeroController(this.hero, this.collision);
    }


    public void draw(TextGraphics graphics) {

        graphics.setBackgroundColor(TextColor.Factory.fromString("#2C1A0B"));
        graphics.fillRectangle(new TerminalPosition(0, 0), new TerminalSize(width, height), ' ');


        for (Wall wall : walls) {
            wall.draw(graphics);
        }

        for (Target target : targets) {
            target.draw(graphics);
        }

        for (Box box : boxes) {
            boolean isOnTarget = false;

            for (Target target : targets) {
                if (box.getPosition().equals(target.getPosition())) {
                    isOnTarget = true;
                    break;
                }
            }
            box.setOnTarget(isOnTarget);
            box.draw(graphics);
        }

        if (hero != null) {
            hero.draw(graphics);
        }
    }

    public void processKey(KeyStroke key) {
        if (controller == null) return;

        Position direction = null;

        switch (key.getKeyType()) {
            case ArrowUp -> direction = new Position(0, -1);
            case ArrowDown -> direction = new Position(0, 1);
            case ArrowLeft -> direction = new Position(-1, 0);
            case ArrowRight -> direction = new Position(1, 0);
        }

        if (direction != null) {
            controller.moveHero(direction);
            if (gameRules.checkWin(this)) {
                this.isFinished = true;
                System.out.println("Level Passesd!");
            }
        }
    }

    public int getWidth() { return width; }
    public int getHeight() { return height; }
    public Hero getHero() { return hero; }
    public List<Wall> getWalls() { return walls; }
    public List<Box> getBoxes() { return boxes; }
    public List<Target> getTargets() { return targets; }
    public boolean isFinished() {return isFinished; }

}

