package agh.ics.oop;

public enum Direction {
    FORWARD,
    BACKWARD,
    LEFT,
    RIGHT;

    public static Direction parse(String m) {
        switch (m) {
            case "f":
                return Direction.FORWARD;
            case "b":
                return Direction.BACKWARD;
            case "r":
                return Direction.RIGHT;
            case "l":
                return Direction.LEFT;
            default:
                return null;
        }
    }
}
