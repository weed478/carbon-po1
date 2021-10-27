package agh.ics.oop;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class OptionsParser {
    public static List<MoveDirection> parse(Stream<String> args) {
        return args.map(arg -> {
                    switch (arg.toLowerCase()) {
                        case "f":
                            return MoveDirection.FORWARD;
                        case "b":
                            return MoveDirection.BACKWARD;
                        case "l":
                            return MoveDirection.LEFT;
                        case "r":
                            return MoveDirection.RIGHT;
                        default:
                            return null;
                    }
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }
}
