package agh.ics.oop;

public class Grass {
    private final Vector2d pos;

    public Grass(Vector2d where) {
        pos = where;
    }

    public Vector2d getPosition() {
        return pos;
    }

    @Override
    public String toString() {
        return "*";
    }
}
