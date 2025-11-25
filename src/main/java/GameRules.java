import java.util.List;

public class GameRules {

    public boolean checkWin(Level level) {
        List<Box> boxes = level.getBoxes();
        List<Target> targets = level.getTargets();

        if (boxes.isEmpty() || targets.isEmpty()) {
            return false;
        }

        int boxesOnTargets = 0;

        for (Box box : boxes) {
            for (Target target : targets) {
                if (box.getPosition().equals(target.getPosition())) {
                    boxesOnTargets++;
                    break;
                }
            }
        }

        return boxesOnTargets == boxes.size();
    }
}
