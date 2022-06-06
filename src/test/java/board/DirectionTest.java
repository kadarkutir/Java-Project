package board;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DirectionTest {

    @Test
    void testOf() {
        assertEquals(Direction.UP, Direction.of(-1, 0));
        assertEquals(Direction.RIGHT, Direction.of(0, 1));
        assertEquals(Direction.DOWN, Direction.of(1, 0));
        assertEquals(Direction.LEFT, Direction.of(0, -1));
        assertEquals(Direction.UP_LEFT, Direction.of(-1, -1));
        assertEquals(Direction.UP_RIGHT, Direction.of(-1, 1));
        assertEquals(Direction.DOWN_LEFT, Direction.of(1, -1));
        assertEquals(Direction.DOWN_RIGHT, Direction.of(1, 1));
    }

    @Test
    void testOf_shouldThrowException(){
        assertThrows(IllegalArgumentException.class,() -> Direction.of(2,1));
    }

}
