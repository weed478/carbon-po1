package agh.ics.oop.map;

import agh.ics.oop.core.Vector2d;
import agh.ics.oop.objects.Animal;

public interface IWorldMap {
    boolean canMoveTo(Vector2d position);

    void place(Animal animal);

    boolean isOccupied(Vector2d position);
    
    Object objectAt(Vector2d position);
}
