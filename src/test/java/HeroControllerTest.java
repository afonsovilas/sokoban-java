import com.sokoban.controller.Collision;
import com.sokoban.controller.HeroController;
import com.sokoban.model.Box;
import com.sokoban.model.Hero;
import com.sokoban.model.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.*;

class HeroControllerTest {
    private HeroController controller;
    private Hero hero;
    private Collision collision;

    @BeforeEach
    void setUp() {
        hero = Mockito.mock(Hero.class);
        collision = Mockito.mock(Collision.class);
        controller = new HeroController(hero, collision);
    }

    @Test
    void moveHeroToEmptyPosition() {
        when(hero.getPosition()).thenReturn(new Position(10, 10));
        Position targetPos = new Position(11, 10);

        when(collision.isWall(targetPos)).thenReturn(false);
        when(collision.getBoxAt(targetPos)).thenReturn(null);

        controller.moveHero(new Position(1, 0));
        verify(hero, times(1)).setPosition(targetPos);
    }

    @Test
    void moveHeroToOcupiedPosition() {
        when(hero.getPosition()).thenReturn(new Position(10, 10));
        Position targetPos = new Position(11, 10);

        when(collision.isWall(targetPos)).thenReturn(true);

        controller.moveHero(new Position(1, 0));
        verify(hero, never()).setPosition(any());
    }

    @Test
    void moveHeroPushBoxSuccess() {
        when(hero.getPosition()).thenReturn(new Position(10, 10));
        Position targetPos = new Position(11, 10);
        Position boxDestPos = new Position(12, 10);

        Box box = Mockito.mock(Box.class);
        when(box.getPosition()).thenReturn(targetPos);

        when(collision.isWall(targetPos)).thenReturn(false);
        when(collision.getBoxAt(targetPos)).thenReturn(box);
        when(collision.isFree(boxDestPos)).thenReturn(true);

        controller.moveHero(new Position(1, 0));

        verify(box, times(1)).setPosition(boxDestPos);
        verify(hero, times(1)).setPosition(targetPos);
    }

    @Test
    void moveHeroPushBoxFailed() {
        when(hero.getPosition()).thenReturn(new Position(10, 10));
        Position targetPos = new Position(11, 10);
        Position boxDestPos = new Position(12, 10);

        Box box = Mockito.mock(Box.class);
        when(box.getPosition()).thenReturn(targetPos);

        when(collision.isWall(targetPos)).thenReturn(false);
        when(collision.getBoxAt(targetPos)).thenReturn(box);
        when(collision.isFree(boxDestPos)).thenReturn(false);

        controller.moveHero(new Position(1, 0));

        verify(box, never()).setPosition(any());
        verify(hero, never()).setPosition(any());
    }
}