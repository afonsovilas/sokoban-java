public class Level1 extends Level {

    private static final String[] LEVEL_DESIGN = {
            "##################",
            "#                #",
            "#        O       #",
            "#           .    #",
            "#       X        #",
            "#                #",
            "##################"
    };

    public Level1() {
        super(LEVEL_DESIGN[0].length(), LEVEL_DESIGN.length);
        createLevel();
    }

    @Override
    protected void createLevel() {
        for (int y = 0; y < height; y++) {
            String line = LEVEL_DESIGN[y];
            for (int x = 0; x < width; x++) {
                if (x >= line.length()) break;

                char c = line.charAt(x);
                Element element = ElementCreation.createElement(c, x, y);

                if (element != null) {
                    if (element instanceof Wall) {
                        walls.add((Wall) element);
                    }
                    else if (element instanceof Box) {
                        boxes.add((Box) element);
                    }
                    else if (element instanceof Hero) {
                        hero = (Hero) element;
                    }
                    else if (element instanceof Target) {
                        targets.add((Target) element);
                    }
                }
            }
        }
    }
}
