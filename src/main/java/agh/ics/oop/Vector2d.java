package agh.ics.oop;

import java.util.Objects;

public class Vector2d {
    public final int x;
    public final int y;

    public Vector2d(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "(" + x + "," + y + ')';
    }

    boolean precedes(Vector2d o) {
        return x <= o.x && y <= o.y;
    }

    boolean follows(Vector2d o) {
        return x >= o.x && y >= o.y;
    }

    Vector2d upperRight(Vector2d o) {
        return new Vector2d(
                Math.max(x, o.x),
                Math.max(y, o.y)
        );
    }

    Vector2d lowerLeft(Vector2d o) {
        return new Vector2d(
                Math.min(x, o.x),
                Math.min(y, o.y)
        );
    }

    Vector2d add(Vector2d o) {
        return new Vector2d(x + o.x, y + o.y);
    }

    Vector2d subtract(Vector2d o) {
        return add(o.opposite());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector2d vector2d = (Vector2d) o;
        return x == vector2d.x && y == vector2d.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    Vector2d opposite() {
        return new Vector2d(-x, -y);
    }
}
