import com.sokoban.model.Target;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TargetTest {
    @Test
    void testConstructor() {
        Target target = new Target(5, 5);

        assertEquals(5, target.getPosition().getX());
        assertEquals(5, target.getPosition().getY());
    }
}
