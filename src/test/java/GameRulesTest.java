import com.sokoban.model.Box;
import com.sokoban.controller.GameRules;
import com.sokoban.model.Level;
import com.sokoban.model.Target;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GameRulesTest {

    @Test
    void hasWon() {
        Level level = mock(Level.class);
        GameRules rules = new GameRules();

        List<Box> boxes = new ArrayList<>();
        Box box1 = new Box(5, 5);
        boxes.add(box1);

        List<Target> targets = new ArrayList<>();
        Target target1 = new Target(5, 5);
        targets.add(target1);

        when(level.getBoxes()).thenReturn(boxes);
        when(level.getTargets()).thenReturn(targets);

        assertTrue(rules.checkWin(level));
    }

    @Test
    void hasNotWon() {
        Level level = mock(Level.class);
        GameRules rules = new GameRules();

        List<Box> boxes = new ArrayList<>();
        boxes.add(new Box(5, 5));

        List<Target> targets = new ArrayList<>();
        targets.add(new Target(6, 6));

        when(level.getBoxes()).thenReturn(boxes);
        when(level.getTargets()).thenReturn(targets);

        assertFalse(rules.checkWin(level));
    }
}
