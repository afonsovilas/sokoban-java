public class Level2 extends Level{
    private static final String[] LEVEL_DESIGN = {
            "##################",
            "#                #",
            "#  *      O      #",
            "#           *    #",
            "#  O     X       #",
            "#                #",
            "##################"
    };

    public Level2() {
        super(LEVEL_DESIGN[0].length(), LEVEL_DESIGN.length);
        loadLevel(LEVEL_DESIGN);
        initialize();
    }

}
