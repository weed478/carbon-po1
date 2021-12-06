package agh.ics.oop;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractWorldMap implements IWorldMap, IPositionChangeObserver {
    private static final Vector2d MARGIN = new Vector2d(3, 3);
    private final MapVisualizer visualizer = new MapVisualizer(this);
    private final Map<Vector2d, Animal> animals = new HashMap<>();
    private final MapBoundary mapBoundary = new MapBoundary();

    public Rect getDrawingBounds() {
        Rect bounds = mapBoundary.getBoundary();

        return new Rect(
                bounds.getBL().subtract(MARGIN),
                bounds.getTR().add(MARGIN)
        );
    }

    @Override
    public String toString() {
        Rect bounds = getDrawingBounds();
        return visualizer.draw(bounds.getBL(), bounds.getTR());
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

    // canMoveTo != isOccupied !!!
    // isOccupied używane jest przez MapVisualizer
    // więc trawa i cokolwiek co się rysuje też isOccupied.
    // canMoveTo jedynie true dla animala

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
