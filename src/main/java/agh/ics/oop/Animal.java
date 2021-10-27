package agh.ics.oop;

public class Animal {
    private MapDirection direction = MapDirection.NORTH;
    private Vector2d pos = new Vector2d(2, 2);

    public MapDirection getDirection() {
        return direction;
    }

    public Vector2d getPos() {
        return pos;
    }

    public boolean isAt(Vector2d pos) {
        return getPos().equals(pos);
    }

    @Override
    public String toString() {
        return "Animal(" + direction + ", " + pos + ")";
    }
}
