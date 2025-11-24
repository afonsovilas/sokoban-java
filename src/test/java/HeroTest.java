import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class HeroTest {
    @Test
    public void moveUp() {
        Hero hero = new Hero(10, 10);
        Position newPos = hero.moveUp();

        Assertions.assertEquals(10, newPos.getX());
        Assertions.assertEquals(9, newPos.getY());
    }
    @Test
    public void moveDown() {
        Hero hero = new Hero(10, 10);
        Position newPos = hero.moveDown();

        Assertions.assertEquals(10, newPos.getX());
        Assertions.assertEquals(11, newPos.getY());
    }
    @Test
    public void moveLeft() {
        Hero hero = new Hero(10, 10);
        Position newPos = hero.moveLeft();

        Assertions.assertEquals(9, newPos.getX());
        Assertions.assertEquals(10, newPos.getY());
    }
    @Test
    public void moveRight() {
        Hero hero = new Hero(10, 10);
        Position newPos = hero.moveRight();

        Assertions.assertEquals(11, newPos.getX());
        Assertions.assertEquals(10, newPos.getY());
    }
}
