public class Level1 extends Level {

    private static final String[] LEVEL_DESIGN = {
            "##################",
            "#                #",
            "#        O       #",
            "#           *    #",
            "#       X        #",
            "#                #",
            "##################"
    };

    public Level1() {
        super(LEVEL_DESIGN[0].length(), LEVEL_DESIGN.length);
        loadLevel(LEVEL_DESIGN);
        initialize();
    }
}
