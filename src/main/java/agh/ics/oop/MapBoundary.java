package agh.ics.oop;

import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Keeps track of the area occupied by map elements.
 */
public class MapBoundary implements IPositionChangeObserver {

    private final SortedSet<Vector2d> xElements = new TreeSet<>(MapBoundary::compareVectorsX);
    private final SortedSet<Vector2d> yElements = new TreeSet<>(MapBoundary::compareVectorsY);

    public void add(Vector2d pos) {
        xElements.add(pos);
        yElements.add(pos);
    }

    public void remove(Vector2d pos) {
        xElements.remove(pos);
        yElements.remove(pos);
    }

    public Rect getBoundary() {
        if (xElements.isEmpty()) {
            return new Rect(0, 0, 0, 0);
        }

        Vector2d left = xElements.first();
        Vector2d right = xElements.last();
        Vector2d bottom = yElements.first();
        Vector2d top = yElements.last();

        return new Rect(
            left.lowerLeft(bottom),
            right.upperRight(top)
        );
    }

    @Override
    public void positionChanged(Vector2d oldPosition, Vector2d newPosition) {
        remove(oldPosition);
        add(newPosition);
    }

    private static int compareVectorsX(Vector2d v1, Vector2d v2) {
        if (v1.x < v2.x) {
            return -1;
        }
        else if (v1.x > v2.x) {
            return 1;
        }
        else if (v1.y == v2.y){
            return 0;
        }
        else {
            return compareVectorsY(v1, v2);
        }
    }

    private static int compareVectorsY(Vector2d v1, Vector2d v2) {
        if (v1.y < v2.y) {
            return -1;
        }
        else if (v1.y > v2.y) {
            return 1;
        }
        else if (v1.x == v2.x){
            return 0;
        }
        else {
            return compareVectorsX(v1, v2);
        }
    }
}
