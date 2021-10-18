package agh.ics.oop;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Vector2dTest {

    @Test
    void testToString() {
        assertEquals(new Vector2d(123, 321).toString(), "(123,321)");
    }

    @Test
    void testPrecedes() {
        Vector2d v1 = new Vector2d(1, 1);
        Vector2d v2 = new Vector2d(2, 2);
        Vector2d v3 = new Vector2d(2, 3);

        assertTrue(v1.precedes(v2), "v1 precedes v2");
        assertFalse(v2.precedes(v1), "v2 !precedes v1");

        assertTrue(v1.precedes(v3), "v1 precedes v3");
        assertFalse(v3.precedes(v1), "v3 !precedes v1");

        assertTrue(v2.precedes(v3), "v2 precedes v3");
        assertFalse(v3.precedes(v2), "v3 !precedes v2");

        assertTrue(v1.precedes(v1), "v1 precedes v1");
        assertTrue(v2.precedes(v2), "v2 precedes v2");
        assertTrue(v3.precedes(v3), "v3 precedes v3");
    }

    @Test
    void testFollows() {
        Vector2d v1 = new Vector2d(1, 1);
        Vector2d v2 = new Vector2d(2, 2);
        Vector2d v3 = new Vector2d(2, 3);

        assertTrue(v1.follows(v1), "v1 follows v1");
        assertTrue(v2.follows(v2), "v2 follows v2");
        assertTrue(v3.follows(v3), "v3 follows v3");

        assertTrue(v3.follows(v2), "v3 follows v2");
        assertFalse(v2.follows(v3), "v2 !follows v3");

        assertTrue(v3.follows(v1), "v3 follows v1");
        assertFalse(v1.follows(v3), "v1 !follows v3");

        assertTrue(v2.follows(v1), "v2 follows v1");
        assertFalse(v1.follows(v2), "v1 !follows v2");
    }

    @Test
    void testUpperRight() {
        Vector2d v1 = new Vector2d(1, -1);
        Vector2d v2 = new Vector2d(-1, 1);

        assertEquals(new Vector2d(1, 1), v1.upperRight(v2));
        assertEquals(new Vector2d(1, 1), v2.upperRight(v1));
    }

    @Test
    void testLowerLeft() {
        Vector2d v1 = new Vector2d(1, -1);
        Vector2d v2 = new Vector2d(-1, 1);

        assertEquals(new Vector2d(-1, -1), v1.lowerLeft(v2));
        assertEquals(new Vector2d(-1, -1), v2.lowerLeft(v1));
    }

    @Test
    void testAdd() {
        assertEquals(new Vector2d(4, 6), new Vector2d(1, 2).add(new Vector2d(3, 4)));
    }

    @Test
    void testSubtract() {
        assertEquals(new Vector2d(-2, -2), new Vector2d(1, 2).subtract(new Vector2d(3, 4)));
    }

    @Test
    void testEquals() {
        assertEquals(new Vector2d(12, 34), new Vector2d(12, 34));
        assertNotEquals(new Vector2d(34, 34), new Vector2d(12, 34));
        Vector2d v = new Vector2d(12, 34);
        assertEquals(v, v);
        //noinspection AssertBetweenInconvertibleTypes
        assertNotEquals(v, 123);
    }

    @Test
    void testHashCode() {
        assertEquals(new Vector2d(12, 34).hashCode(), new Vector2d(12, 34).hashCode());
    }

    @Test
    void testOpposite() {
        assertEquals(new Vector2d(-1, -2), new Vector2d(1, 2).opposite());
    }
}
