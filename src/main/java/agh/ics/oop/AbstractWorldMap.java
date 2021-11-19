package agh.ics.oop;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractWorldMap implements IWorldMap {
    private static final Vector2d MARGIN = new Vector2d(3, 3);
    private final MapVisualizer visualizer = new MapVisualizer(this);
    private final List<Animal> animals = new ArrayList<>();

    // miało być abstract, ale tak myślę jest lepiej
    // bo this.animals jest private i GrassField robi tylko trawę
    protected Rect getDrawingBounds() {
        Rect bounds = animals.stream()
                .map(a -> new Rect(a.getPos(), a.getPos()))
                .findFirst()
                .orElse(new Rect(0, 0, 0, 0));

        for (Animal a : animals) {
            bounds = bounds.extendedTo(a.getPos());
        }

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
    public boolean place(Animal animal) {
        if (!canMoveTo(animal.getPos())) {
            return false;
        }
        animals.add(animal);
        return true;
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        return animals.stream()
                .noneMatch(a -> a.getPos().equals(position));
    }

    @Override
    public boolean isOccupied(Vector2d position) {
        return animals.stream().anyMatch(a -> a.getPos().equals(position));
    }

    @Override
    public Object objectAt(Vector2d position) {
        return animals.stream()
                .filter(a -> a.getPos().equals(position))
                .findFirst()
                .orElse(null);
    }
}
