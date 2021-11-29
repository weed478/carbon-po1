package agh.ics.oop;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

public class World {
    private static void run(String[] args) {
        Stream<MoveDirection> directions;

        // można włączyć program z argumentami random 13
        // co wygeneruje 13 losowych ruchów
        if (args.length == 2 && args[0].equals("random")) {
            long n = Long.parseLong(args[1]);
            Random r = new Random();
            directions = r.ints()
                    .mapToObj(i -> {
                        switch (i % 4) {
                            case 0:
                                return MoveDirection.FORWARD;
                            case 1:
                                return MoveDirection.BACKWARD;
                            case 2:
                                return MoveDirection.LEFT;
                            case 3:
                            default:
                                return MoveDirection.RIGHT;
                        }
                    })
                    .limit(n);
        }
        else {
            directions = OptionsParser.parse(Arrays.stream(args));
        }

        IWorldMap map = new GrassField(10);
        List<Vector2d> positions = Arrays.asList(new Vector2d(2,2), new Vector2d(3,4));
        IEngine engine = new SimulationEngine(directions, map, positions);
        engine.run();
    }

    public static void main(String[] args) {
        try {
            run(args);
        }
        catch (Exception e) {
            System.err.println("Kółko graniaste, czworokanciaste, " + e.getMessage() + ", a my wszyscy bęc.");
            System.exit(0);
        }
    }
}
