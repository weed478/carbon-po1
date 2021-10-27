package agh.ics.oop;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class OptionsParserTest {

    @Test
    void testParse() {
        assertEquals(
                Arrays.asList(
                        MoveDirection.FORWARD,
                        MoveDirection.LEFT,
                        MoveDirection.RIGHT,
                        MoveDirection.BACKWARD
                        ),
                OptionsParser.parse(Arrays.stream(new String[]{
                        "f", "L", "123", "R", "B"
                })).collect(Collectors.toList())
        );
    }
}
