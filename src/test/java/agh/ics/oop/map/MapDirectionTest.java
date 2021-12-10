package agh.ics.oop.map;

import agh.ics.oop.core.Vector2d;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MapDirectionTest {

    @Test
    void next() {
        assertEquals(MapDirection.NE, MapDirection.N.next());
        assertEquals(MapDirection.SE, MapDirection.E.next());
        assertEquals(MapDirection.SW, MapDirection.S.next());
        assertEquals(MapDirection.NW, MapDirection.W.next());
    }

    @Test
    void previous() {
        assertEquals(MapDirection.NW, MapDirection.N.previous());
        assertEquals(MapDirection.SW, MapDirection.W.previous());
        assertEquals(MapDirection.SE, MapDirection.S.previous());
        assertEquals(MapDirection.NE, MapDirection.E.previous());
    }

    @Test
    void toUnitVector() {
        assertEquals(new Vector2d(0, 1), MapDirection.N.toUnitVector());
        assertEquals(new Vector2d(1, 0), MapDirection.E.toUnitVector());
        assertEquals(new Vector2d(0, -1), MapDirection.S.toUnitVector());
        assertEquals(new Vector2d(-1, 0), MapDirection.W.toUnitVector());
    }
}
