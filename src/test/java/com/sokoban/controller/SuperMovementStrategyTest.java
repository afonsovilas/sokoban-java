package com.sokoban.controller;

import com.sokoban.model.Box;
import com.sokoban.model.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class SuperMovementStrategyTest {
    private SuperMovementStrategy strategy;
    private Collision collision;

    @BeforeEach
    void setUp() {
        strategy = new SuperMovementStrategy();
        collision = Mockito.mock(Collision.class);
    }

    @Test
    void pushTwoBoxesSuccess() {
        Position direction = new Position(1, 0);

        Box box1 = mock(Box.class);
        Box box2 = mock(Box.class);

        when(box1.getPosition()).thenReturn(new Position(2, 1));
        when(box2.getPosition()).thenReturn(new Position(3, 1));

        when(collision.getBoxAt(new Position(3, 1))).thenReturn(box2);

        Position box2Target = new Position(4, 1);
        when(collision.isFree(box2Target)).thenReturn(true);
        when(collision.getBoxAt(box2Target)).thenReturn(null);

        when(collision.isFree(new Position(3, 1))).thenReturn(true);

        boolean result = strategy.pushBox(box1, direction, collision);

        assertTrue(result, "Deve conseguir empurrar as duas caixas");

        verify(box2).setPosition(new Position(4, 1));
        verify(box1).setPosition(new Position(3, 1));
    }

    @Test
    void pushTwoBoxesBlocked() {
        Position direction = new Position(1, 0);

        Box box1 = mock(Box.class);
        Box box2 = mock(Box.class);

        when(box1.getPosition()).thenReturn(new Position(2, 1));
        when(box2.getPosition()).thenReturn(new Position(3, 1));

        when(collision.getBoxAt(new Position(3, 1))).thenReturn(box2);

        when(collision.isFree(new Position(4, 1))).thenReturn(false);

        boolean result = strategy.pushBox(box1, direction, collision);

        assertFalse(result);
        verify(box2, never()).setPosition(any());
        verify(box1, never()).setPosition(any());
    }

    @Test
    void pushTwoBoxes_RollbackLogic() {
        Position direction = new Position(1, 0);
        Box box1 = mock(Box.class);
        Box box2 = mock(Box.class);

        Position pos1 = new Position(2, 1);
        Position pos2 = new Position(3, 1);

        when(box1.getPosition()).thenReturn(pos1);
        when(box2.getPosition()).thenReturn(pos2);

        doAnswer(invocation -> {
            Position newPos = invocation.getArgument(0);
            when(box2.getPosition()).thenReturn(newPos);
            return null;
        }).when(box2).setPosition(any(Position.class));
        // ---------------------------------------------------------------------------------

        when(collision.getBoxAt(new Position(3, 1))).thenReturn(box2);

        when(collision.isFree(new Position(4, 1))).thenReturn(true);

        when(collision.isFree(new Position(3, 1))).thenReturn(false);

        when(collision.isFree(new Position(5, 1))).thenReturn(false);

        boolean result = strategy.pushBox(box1, direction, collision);

        assertFalse(result, "O movimento deve falhar");

        verify(box2, times(1)).setPosition(new Position(4, 1));

        verify(box2, times(1)).setPosition(pos2);
    }
}