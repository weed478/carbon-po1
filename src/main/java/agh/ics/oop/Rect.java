package agh.ics.oop;

import java.util.Objects;

public class Rect {
    private final Vector2d bl, tr;

    public Rect(int x0, int y0, int x1, int y1) {
        bl = new Vector2d(x0, y0);
        tr = new Vector2d(x1, y1);
    }

    public Rect(Vector2d bl, Vector2d tr) {
        this.bl = bl;
        this.tr = tr;
    }

    @Override
    public String toString() {
        return "Rect( " + bl + ", " + tr + " )";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rect rect = (Rect) o;
        return bl.equals(rect.bl) && tr.equals(rect.tr);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bl, tr);
    }

    public Vector2d getBL() {
        return bl;
    }

    public Vector2d getTR() {
        return tr;
    }

    public int width() {
        return tr.x - bl.x + 1;
    }

    public int height() {
        return tr.y - bl.y + 1;
    }

    public boolean contains(Vector2d p) {
        return p.precedes(tr) &&
               p.follows(bl);
    }

    public Rect extendedTo(Vector2d p) {
        return new Rect(
                bl.lowerLeft(p),
                tr.upperRight(p)
        );
    }
}
