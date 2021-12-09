package agh.ics.oop.map;

import agh.ics.oop.core.Rect;
import agh.ics.oop.core.Vector2d;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MapBoundaryTest {

    @Test
    void testAdding() {
        MapBoundary map = new MapBoundary();
        assertEquals(new Rect(0, 0, 0, 0), map.getBoundary());

        map.add(new Vector2d(3, 2));
        assertEquals(new Rect(3, 2, 3, 2), map.getBoundary());

        map.add(new Vector2d(-2, 5));
        assertEquals(new Rect(-2, 2, 3, 5), map.getBoundary());

        map.add(new Vector2d(0, 3));
        assertEquals(new Rect(-2, 2, 3, 5), map.getBoundary());

        map.add(new Vector2d(0, -3));
        assertEquals(new Rect(-2, -3, 3, 5), map.getBoundary());
    }

    @Test
    void testRemove() {
        MapBoundary map = new MapBoundary();
        map.add(new Vector2d(3, 2));
        map.add(new Vector2d(-2, 5));
        map.add(new Vector2d(0, 3));
        map.add(new Vector2d(0, -3));
        assertEquals(new Rect(-2, -3, 3, 5), map.getBoundary());

        map.remove(new Vector2d(-2, 5));
        assertEquals(new Rect(0, -3, 3, 3), map.getBoundary());
    }
}
