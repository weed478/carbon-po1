package agh.ics.oop;

import java.util.stream.Stream;

/**
 * User launched the app with provided movement directions.
 */
public class ManualLaunchIntent extends AppLaunchIntent {

    public final Stream<MoveDirection> directions;

    public ManualLaunchIntent(Stream<MoveDirection> directions) {
        this.directions = directions;
    }
}
