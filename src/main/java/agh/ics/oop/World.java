package agh.ics.oop;

import java.util.Arrays;
import java.util.stream.Stream;

public class World {
    public static void main(String[] args) {
        Stream<MoveDirection> directions = OptionsParser.parse(Arrays.stream(args));
        IWorldMap map = new RectangularMap(10, 5);
        Vector2d[] positions = { new Vector2d(2,2), new Vector2d(3,4) };
        IEngine engine = new SimulationEngine(directions, map, Arrays.asList(positions));
        engine.run();
    }
}
