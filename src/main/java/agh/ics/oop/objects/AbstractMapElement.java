package agh.ics.oop.objects;

import agh.ics.oop.core.Vector2d;
import agh.ics.oop.map.MapDirection;

public abstract class AbstractMapElement implements IMapElement {

    private Vector2d position;
    private MapDirection direction;

    public AbstractMapElement(Vector2d position, MapDirection direction) {
        this.position = position;
        this.direction = direction;
    }

    @Override
    public Vector2d getPosition() {
        return position;
    }

    protected void setPosition(Vector2d position) {
        this.position = position;
    }

    @Override
    public MapDirection getDirection() {
        return direction;
    }

    protected void setDirection(MapDirection direction) {
        this.direction = direction;
    }
}
