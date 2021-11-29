package agh.ics.oop;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GrassFieldTest {
    @Test
    void testMovement() {
        IWorldMap map = new GrassField(10);
        Animal animal = new Animal(map, new Vector2d(2, 2));

        map.place(animal);

        assertTrue(map.isOccupied(new Vector2d(2, 2)));
        assertEquals(animal, map.objectAt(new Vector2d(2, 2)));
        assertFalse(map.canMoveTo(new Vector2d(2, 2)));
        assertTrue(map.canMoveTo(new Vector2d(2, 3)));

        animal.move(MoveDirection.FORWARD);

        assertTrue(map.isOccupied(new Vector2d(2, 3)));
        assertEquals(animal, map.objectAt(new Vector2d(2, 3)));
        assertTrue(map.canMoveTo(new Vector2d(2, 2)));
        assertFalse(map.canMoveTo(new Vector2d(2, 3)));
    }
}
