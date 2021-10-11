package agh.ics.oop;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Objects;
import java.util.stream.Stream;

import static java.lang.System.out;

public class World {
    public static void main(String[] args) {
        out.println("Start");
        Stream<Direction> moves = Arrays.stream(args)
                .map(World::parse)
                .filter(Objects::nonNull);
        run(moves);
        out.println("Stop");
    }

    private static Direction parse(String m) {
        switch (m) {
            case "f":
                return Direction.FORWARD;
            case "b":
                return Direction.BACKWARD;
            case "r":
                return Direction.RIGHT;
            case "l":
                return Direction.LEFT;
            default:
                return null;
        }
    }

    private static void run(Stream<Direction> moves) {
        Iterator<Direction> iter = moves.iterator();
        while (iter.hasNext()) {
            Direction m = iter.next();
            switch (m) {
                case FORWARD:
                    out.println("Zwierzak idzie do przodu");
                    break;
                case BACKWARD:
                    out.println("Zwierzak idzie do tyłu");
                    break;
                case LEFT:
                    out.println("Zwierzak idzie w lewo");
                    break;
                case RIGHT:
                    out.println("Zwierzak idzie w prawo");
                    break;
            }
        }
    }
}
