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
                .map(Direction::parse)
                .filter(Objects::nonNull);
        run(moves);
        out.println("Stop");
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
