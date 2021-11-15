package agh.ics.oop;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RectTest {

    @Test
    void testCreation() {
        assertEquals(
                new Rect(-10, -10, 10, 10),
                new Rect(new Vector2d(-10, -10), new Vector2d(10, 10))
        );
    }

    @Test
    void testEquals() {
        assertEquals(
                new Rect(-10, -10, 10, 10),
                new Rect(-10, -10, 10, 10)
        );
        assertNotEquals(
                new Rect(-10, -10, 10, 10),
                new Rect(-10, -10, 11, 10)
        );
    }

    @Test
    void testContains() {
        assertTrue(new Rect(-10, -10, 10, 10).contains(new Vector2d(2, -10)));
        assertFalse(new Rect(-10, -10, 10, 10).contains(new Vector2d(2, -11)));
    }

    @Test
    void testExtended() {
        assertEquals(new Rect(-10, -10, 10, 10),
                new Rect(-5, -5, 5, 5)
                        .extendedTo(new Vector2d(10, 10))
                        .extendedTo(new Vector2d(-10, -10)));
    }

}
