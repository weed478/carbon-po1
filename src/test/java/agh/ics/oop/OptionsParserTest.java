package agh.ics.oop;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class OptionsParserTest {

    @Test
    void testParse() {
        assertEquals(
                Arrays.asList(
                        MoveDirection.FORWARD,
                        MoveDirection.FORWARD,
                        MoveDirection.FORWARD,
                        MoveDirection.LEFT,
                        MoveDirection.LEFT,
                        MoveDirection.BACKWARD,
                        MoveDirection.BACKWARD,
                        MoveDirection.RIGHT,
                        MoveDirection.RIGHT
                        ),
                OptionsParser.parse(Stream.of(
                        "f", "F", "forWard",
                        "L", "lefT",
                        "123",
                        "b", "backWarD",
                        "R", "Right"
                )).collect(Collectors.toList())
        );
    }
}
