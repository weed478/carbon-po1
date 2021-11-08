package agh.ics.oop;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class SimulationEngineTest {
    @Test
    void testRun() {
        Stream<String> args = Arrays.stream("f b r l f f r r f f f f f f f f".split(" "));
        Stream<MoveDirection> directions = OptionsParser.parse(args);
        IWorldMap map = new RectangularMap(10, 5);
        List<Vector2d> positions = Arrays.asList(new Vector2d(2, 2), new Vector2d(3, 4));
        IEngine engine = new SimulationEngine(directions, map, positions);

        assertTrue(map.isOccupied(new Vector2d(2, 2)));
        assertTrue(map.isOccupied(new Vector2d(3, 4)));
        assertFalse(map.isOccupied(new Vector2d(2, 0)));

        engine.run();

        assertFalse(map.isOccupied(new Vector2d(2, 2)));
        assertTrue(map.isOccupied(new Vector2d(3, 4)));
        assertTrue(map.isOccupied(new Vector2d(2, 0)));
    }
}
