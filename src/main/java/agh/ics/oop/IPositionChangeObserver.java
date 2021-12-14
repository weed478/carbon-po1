package agh.ics.oop;

public interface IPositionChangeObserver {

    /**
     * Object moved from oldPosition to newPosition.
     * Object position should be set to newPosition
     * when calling.
     */
    void positionChanged(Vector2d oldPosition, Vector2d newPosition);
}
