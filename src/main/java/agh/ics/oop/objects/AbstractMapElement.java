package agh.ics.oop.objects;

import agh.ics.oop.core.Vector2d;
import agh.ics.oop.map.MapDirection;

public abstract class AbstractMapElement implements IMapElement {

    private Vector2d position;
    private MapDirection direction;
    protected final Object world;

    public AbstractMapElement(Object world, Vector2d position, MapDirection direction) {
        this.world = world;
        this.position = position;
        this.direction = direction;
    }

    @Override
    public Vector2d getPosition() {
        synchronized (world) {
            return position;
        }
    }

    protected void setPosition(Vector2d position) {
        synchronized (world) {
            this.position = position;
        }
    }

    @Override
    public MapDirection getDirection() {
        synchronized (world) {
            return direction;
        }
    }

    protected void setDirection(MapDirection direction) {
        synchronized (world) {
            this.direction = direction;
        }
    }
}
