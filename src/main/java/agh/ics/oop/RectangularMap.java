package agh.ics.oop;

import java.util.HashMap;
import java.util.Map;

public class RectangularMap implements IWorldMap {

    private final Vector2d bottomLeft;
    private final Vector2d topRight;
    private final Map<Vector2d, Animal> fields = new HashMap<>();
    private final MapVisualizer visualizer = new MapVisualizer(this);

    public RectangularMap(int width, int height) {
        if (width < 1 || height < 1) {
            throw new IllegalArgumentException("Width/height must be >0");
        }
        this.bottomLeft = new Vector2d(0, 0);
        this.topRight = new Vector2d(width - 1, height - 1);
    }

    @Override
    public String toString() {
        return visualizer.draw(bottomLeft, topRight);
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        return inBounds(position) &&
               !isOccupied(position);
    }

    @Override
    public boolean place(Animal animal) {
        if (!canMoveTo(animal.getPos())) {
            return false;
        }
        fields.put(animal.getPos(), animal);
        return true;
    }

    @Override
    public boolean isOccupied(Vector2d position) {
        return fields.containsKey(position);
    }

    @Override
    public Object objectAt(Vector2d position) {
        return fields.get(position);
    }

    private boolean inBounds(Vector2d position) {
        return position.precedes(topRight) &&
               position.follows(bottomLeft);
    }
}
