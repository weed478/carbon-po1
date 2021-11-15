package agh.ics.oop;

import java.util.Objects;
import java.util.stream.Stream;

public class OptionsParser {
    public static Stream<MoveDirection> parse(Stream<String> args) {
        return args.map(arg -> {
                    switch (arg.toLowerCase()) {
                        case "f":
                        case "forward":
                            return MoveDirection.FORWARD;
                        case "b":
                        case "backward":
                            return MoveDirection.BACKWARD;
                        case "l":
                        case "left":
                            return MoveDirection.LEFT;
                        case "r":
                        case "right":
                            return MoveDirection.RIGHT;
                        default:
                            return null;
                    }
                })
                .filter(Objects::nonNull);
    }
}
