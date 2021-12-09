package agh.ics.oop.map;

import agh.ics.oop.core.IPositionChangeObserver;
import agh.ics.oop.core.Rect;
import agh.ics.oop.core.Vector2d;
import agh.ics.oop.objects.Animal;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractWorldMap implements IDrawableMap, IPositionChangeObserver {
    private static final Vector2d MARGIN = new Vector2d(3, 3);
    private final Map<Vector2d, Animal> animals = new HashMap<>();
    private final MapBoundary mapBoundary = new MapBoundary();

    @Override
    public Rect getDrawingBounds() {
        Rect bounds = mapBoundary.getBoundary();

        return new Rect(
                bounds.getBL().subtract(MARGIN),
                bounds.getTR().add(MARGIN)
        );
    }

    @Override
    public void place(Animal animal) {
        if (!canMoveTo(animal.getPos())) {
            throw new IllegalArgumentException("Cannot place animal at " + animal.getPos());
        }

        animals.put(animal.getPos(), animal);
        animal.addObserver(this);

        mapBoundary.add(animal.getPos());
        animal.addObserver(mapBoundary);
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        return !animals.containsKey(position);
    }

    @Override
    public boolean isOccupied(Vector2d position) {
        return animals.containsKey(position);
    }

    @Override
    public Object objectAt(Vector2d position) {
        return animals.get(position);
    }

    @Override
    public void positionChanged(Vector2d oldPosition, Vector2d newPosition) {
        Animal a = animals.remove(oldPosition);
        animals.put(newPosition, a);
    }
}
