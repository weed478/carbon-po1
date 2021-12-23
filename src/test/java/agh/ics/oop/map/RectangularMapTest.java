package agh.ics.oop.map;

import agh.ics.oop.core.Rect;
import agh.ics.oop.core.Vector2d;
import agh.ics.oop.objects.Animal;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RectangularMapTest {

    @Test
    void walk1() {
        RectangularMap map = new RectangularMap(new Rect(-10, -10, 11, 11), new Rect(-2, -2, 3, 3));
        assertEquals(0, map.getAnimalsAt(new Vector2d(0, 0)).size());

        Animal animal = new Animal(null, map, new Vector2d(0, 0), MapDirection.N);
        assertEquals(1, map.getAnimalsAt(new Vector2d(0, 0)).size());
        assertEquals(0, map.getAnimalsAt(new Vector2d(0, 1)).size());

        animal.move(0);
        assertEquals(0, map.getAnimalsAt(new Vector2d(0, 0)).size());
        assertEquals(1, map.getAnimalsAt(new Vector2d(0, 1)).size());

        animal.move(2);
        assertEquals(0, map.getAnimalsAt(new Vector2d(1, 1)).size());
        assertEquals(1, map.getAnimalsAt(new Vector2d(0, 1)).size());

        animal.move(0);
        assertEquals(1, map.getAnimalsAt(new Vector2d(1, 1)).size());
        assertEquals(0, map.getAnimalsAt(new Vector2d(0, 1)).size());
    }
}