package agh.ics.oop;

import java.util.stream.Stream;

public class ManualLaunchIntent extends AppLaunchIntent {

    public final Stream<MoveDirection> directions;

    public ManualLaunchIntent(Stream<MoveDirection> directions) {
        this.directions = directions;
    }
}
