package agh.ics.oop;

import java.util.Arrays;
import java.util.List;

import static java.lang.System.out;

public class World {
    static public final Vector2d MAP_BOTTOM_LEFT = new Vector2d(0, 0);
    static public final Vector2d MAP_TOP_RIGHT = new Vector2d(4, 4);

    public static void main(String[] args) {
        Animal a = new Animal();
        out.println("Initial: " + a);
        List<MoveDirection> moves = OptionsParser.parse(Arrays.stream(args));
        for (MoveDirection dir : moves) {
            a.move(dir);
            out.println(dir + ": " + a);
        }
    }
}
