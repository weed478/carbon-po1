package agh.ics.oop;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DirectionTest {

    @Test
    void testParse() {
        assertEquals(Direction.FORWARD, Direction.parse("f"));
        assertEquals(Direction.RIGHT, Direction.parse("r"));
        assertEquals(Direction.BACKWARD, Direction.parse("b"));
        assertEquals(Direction.LEFT, Direction.parse("l"));
        assertNull(Direction.parse("xd"));
    }
}
