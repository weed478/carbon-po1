package agh.ics.oop;

import java.util.List;
import java.util.stream.Stream;

/**
 * Class representing app mode of operation.
 */
public abstract class AppLaunchIntent {

    /**
     * Create a launch intent from command line args.
     * Supported: AI and manual movement.
     * @param args command line arguments
     * @return launch intent
     */
    public static AppLaunchIntent parse(List<String> args) {
        if (args.size() > 0 && args.get(args.size() - 1).equals("skynet")) {
            return new SkynetLaunchIntent();
        } else {
            Stream<MoveDirection> directions = OptionsParser.parse(args.stream());
            return new ManualLaunchIntent(directions);
        }
    }
}
