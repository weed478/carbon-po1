package agh.ics.oop;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AnimalTest {
    @Test
    void testToString() {
        assertEquals("Animal(Północ, (2,2))", new Animal().toString());
    }

    @Test
    void testIsAt() {
        assertTrue(new Animal().isAt(new Vector2d(2, 2)));
        assertFalse(new Animal().isAt(new Vector2d(2, 3)));
    }
}
