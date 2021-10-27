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

    @Test
    void testMove2() {
        Animal a = new Animal();
        a.move(MoveDirection.RIGHT);
        a.move(MoveDirection.FORWARD);
        a.move(MoveDirection.FORWARD);
        a.move(MoveDirection.FORWARD);
        assertEquals(new Vector2d(4, 2), a.getPos());
        assertEquals(MapDirection.EAST, a.getDirection());
    }

    @Test
    void testMove() {
        Animal a = new Animal();
        assertEquals(new Vector2d(2, 2), a.getPos());
        assertEquals(MapDirection.NORTH, a.getDirection());

        a.move(MoveDirection.FORWARD);
        assertEquals(new Vector2d(2, 3), a.getPos());
        assertEquals(MapDirection.NORTH, a.getDirection());

        a.move(MoveDirection.FORWARD);
        assertEquals(new Vector2d(2, 4), a.getPos());
        assertEquals(MapDirection.NORTH, a.getDirection());

        a.move(MoveDirection.FORWARD);
        assertEquals(new Vector2d(2, 4), a.getPos());
        assertEquals(MapDirection.NORTH, a.getDirection());

        a.move(MoveDirection.BACKWARD);
        assertEquals(new Vector2d(2, 3), a.getPos());
        assertEquals(MapDirection.NORTH, a.getDirection());

        a.move(MoveDirection.RIGHT);
        assertEquals(new Vector2d(2, 3), a.getPos());
        assertEquals(MapDirection.EAST, a.getDirection());

        a.move(MoveDirection.FORWARD);
        assertEquals(new Vector2d(3, 3), a.getPos());
        assertEquals(MapDirection.EAST, a.getDirection());

        a.move(MoveDirection.LEFT);
        assertEquals(new Vector2d(3, 3), a.getPos());
        assertEquals(MapDirection.NORTH, a.getDirection());

        a.move(MoveDirection.LEFT);
        assertEquals(new Vector2d(3, 3), a.getPos());
        assertEquals(MapDirection.WEST, a.getDirection());

        a.move(MoveDirection.BACKWARD);
        assertEquals(new Vector2d(4, 3), a.getPos());
        assertEquals(MapDirection.WEST, a.getDirection());

        a.move(MoveDirection.LEFT);
        assertEquals(new Vector2d(4, 3), a.getPos());
        assertEquals(MapDirection.SOUTH, a.getDirection());

        a.move(MoveDirection.FORWARD);
        assertEquals(new Vector2d(4, 2), a.getPos());
        assertEquals(MapDirection.SOUTH, a.getDirection());

        a.move(MoveDirection.FORWARD);
        assertEquals(new Vector2d(4, 1), a.getPos());
        assertEquals(MapDirection.SOUTH, a.getDirection());

        a.move(MoveDirection.FORWARD);
        assertEquals(new Vector2d(4, 0), a.getPos());
        assertEquals(MapDirection.SOUTH, a.getDirection());

        a.move(MoveDirection.FORWARD);
        assertEquals(new Vector2d(4, 0), a.getPos());
        assertEquals(MapDirection.SOUTH, a.getDirection());

        a.move(MoveDirection.RIGHT);
        assertEquals(new Vector2d(4, 0), a.getPos());
        assertEquals(MapDirection.WEST, a.getDirection());

        a.move(MoveDirection.FORWARD);
        assertEquals(new Vector2d(3, 0), a.getPos());
        assertEquals(MapDirection.WEST, a.getDirection());

        a.move(MoveDirection.FORWARD);
        assertEquals(new Vector2d(2, 0), a.getPos());
        assertEquals(MapDirection.WEST, a.getDirection());

        a.move(MoveDirection.FORWARD);
        assertEquals(new Vector2d(1, 0), a.getPos());
        assertEquals(MapDirection.WEST, a.getDirection());

        a.move(MoveDirection.FORWARD);
        assertEquals(new Vector2d(0, 0), a.getPos());
        assertEquals(MapDirection.WEST, a.getDirection());

        a.move(MoveDirection.FORWARD);
        assertEquals(new Vector2d(0, 0), a.getPos());
        assertEquals(MapDirection.WEST, a.getDirection());

        a.move(MoveDirection.BACKWARD);
        assertEquals(new Vector2d(1, 0), a.getPos());
        assertEquals(MapDirection.WEST, a.getDirection());

        a.move(MoveDirection.BACKWARD);
        assertEquals(new Vector2d(2, 0), a.getPos());
        assertEquals(MapDirection.WEST, a.getDirection());

        a.move(MoveDirection.BACKWARD);
        assertEquals(new Vector2d(3, 0), a.getPos());
        assertEquals(MapDirection.WEST, a.getDirection());

        a.move(MoveDirection.BACKWARD);
        assertEquals(new Vector2d(4, 0), a.getPos());
        assertEquals(MapDirection.WEST, a.getDirection());

        a.move(MoveDirection.BACKWARD);
        assertEquals(new Vector2d(4, 0), a.getPos());
        assertEquals(MapDirection.WEST, a.getDirection());
    }
}
