package agh.ics.oop;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class World {
    public static void main(String[] args) {
        Stream<MoveDirection> directions = OptionsParser.parse(Arrays.stream(args));
        IWorldMap map = new GrassField(0);
        List<Vector2d> positions = Arrays.asList(new Vector2d(2,2), new Vector2d(3,4));
        IEngine engine = new SimulationEngine(directions, map, positions);
        engine.run();
    }
}
