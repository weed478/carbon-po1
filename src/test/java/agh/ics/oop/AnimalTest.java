package agh.ics.oop;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AnimalTest {
    @Test
    void testToString() {
        assertEquals("^", new Animal(null).toString());
    }

    @Test
    void testIsAt() {
        assertTrue(new Animal(null).isAt(new Vector2d(2, 2)));
        assertFalse(new Animal(null).isAt(new Vector2d(2, 3)));
    }
}
