package agh.ics.oop;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RectangularMapTest {

    @Test
    void testOneByOne() {
        RectangularMap map = new RectangularMap(1, 1);

        assertFalse(map.isOccupied(new Vector2d(0, 0)));
        assertTrue(map.canMoveTo(new Vector2d(0, 0)));
        assertNull(map.objectAt(new Vector2d(0, 0)));

        assertFalse(map.canMoveTo(new Vector2d(1, 0)));
        assertFalse(map.canMoveTo(new Vector2d(-1, 0)));

        assertTrue(map.place(new Animal(map, new Vector2d(0, 0))));

        assertTrue(map.isOccupied(new Vector2d(0, 0)));
        assertFalse(map.canMoveTo(new Vector2d(0, 0)));
        assertNotNull(map.objectAt(new Vector2d(0, 0)));
    }

    @Test
    void testMovement() {
        RectangularMap map = new RectangularMap(5, 5);
        Animal animal = new Animal(map, new Vector2d(2, 2));

        assertTrue(map.place(animal));

        assertTrue(map.isOccupied(new Vector2d(2, 2)));
        assertFalse(map.isOccupied(new Vector2d(2, 3)));
        assertTrue(map.canMoveTo(new Vector2d(2, 3)));

        animal.move(MoveDirection.FORWARD);

        assertFalse(map.isOccupied(new Vector2d(2, 2)));
        assertTrue(map.isOccupied(new Vector2d(2, 3)));
        assertFalse(map.canMoveTo(new Vector2d(2, 3)));
    }
}
