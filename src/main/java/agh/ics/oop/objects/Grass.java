package agh.ics.oop.objects;

import agh.ics.oop.core.Vector2d;
import agh.ics.oop.map.MapDirection;

public class Grass extends AbstractObservableMapElement {
    public Grass(Vector2d position) {
        super(position, MapDirection.NORTH);
    }

    @Override
    public String toString() {
        return "*";
    }
}
