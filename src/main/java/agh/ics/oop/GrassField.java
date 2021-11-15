package agh.ics.oop;

import java.util.ArrayList;
import java.util.List;

public class GrassField implements IWorldMap {

    static final Vector2d MARGIN = new Vector2d(2, 2);
    private final MapVisualizer visualizer = new MapVisualizer(this);
    private final List<Animal> animals = new ArrayList<>();

    public GrassField(int numGrass) {

    }

    @Override
    public String toString() {
        Vector2d bl, tr;

        bl = tr = animals.stream()
                .map(Animal::getPos)
                .findFirst()
                .orElse(new Vector2d(0, 0));

        for (Animal a : animals) {
            bl = bl.lowerLeft(a.getPos());
            tr = tr.upperRight(a.getPos());
        }

        bl = bl.subtract(MARGIN);
        tr = tr.add(MARGIN);

        return visualizer.draw(bl, tr);
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        return !isOccupied(position);
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
    public boolean isOccupied(Vector2d position) {
        return animals.stream()
                .anyMatch(a -> a.getPos().equals(position));
    }

    @Override
    public Object objectAt(Vector2d position) {
        return animals.stream()
                .filter(a -> a.getPos().equals(position))
                .findFirst()
                .orElse(null);
    }
}
