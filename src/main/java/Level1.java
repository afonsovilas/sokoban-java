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

                if (c == '#') walls.add(new Wall(x, y));
                else if (c == 'O') boxes.add(new Box(x, y));
                else if (c == 'X') hero = new Hero(x, y);
                else if(c== '.') targets.add(new Target(x,y));
            }
        }
    }
}
