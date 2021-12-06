package agh.ics.oop;

import java.util.List;
import java.util.stream.Stream;

public abstract class AppLaunchIntent {

    public static AppLaunchIntent parse(List<String> args) {
        if (args.size() == 1 && args.get(0).equals("skynet")) {
            return new SkynetLaunchIntent();
        } else {
            Stream<MoveDirection> directions = OptionsParser.parse(args.stream());
            return new ManualLaunchIntent(directions);
        }
    }
}
