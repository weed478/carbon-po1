package agh.ics.oop;

public interface IWorldMap {
    boolean canMoveTo(Vector2d position);

    void place(Animal animal);

    boolean isOccupied(Vector2d position);
    
    Object objectAt(Vector2d position);
}
