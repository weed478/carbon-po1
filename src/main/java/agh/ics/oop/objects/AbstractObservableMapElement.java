package agh.ics.oop.objects;

import agh.ics.oop.core.IMapElementObserver;
import agh.ics.oop.core.Vector2d;
import agh.ics.oop.map.MapDirection;

import java.util.HashSet;
import java.util.Set;

public abstract class AbstractObservableMapElement extends AbstractMapElement {

    private final Set<IMapElementObserver> observers = new HashSet<>();

    public AbstractObservableMapElement(Vector2d position, MapDirection direction) {
        super(position, direction);
    }

    public void addMapElementObserver(IMapElementObserver observer) {
        observers.add(observer);
    }

    public void removeMapElementObserver(IMapElementObserver observer) {
        observers.remove(observer);
    }

    @Override
    protected void setPosition(Vector2d position) {
        Vector2d oldPosition = getPosition();
        super.setPosition(position);
        positionChanged(oldPosition);
    }

    @Override
    protected void setDirection(MapDirection direction) {
        MapDirection oldDirection = getDirection();
        super.setDirection(direction);
        directionChanged(oldDirection);
    }

    private void positionChanged(Vector2d oldPos) {
        for (IMapElementObserver observer : observers) {
            observer.mapElementMoved(this, oldPos);
        }
    }

    private void directionChanged(MapDirection oldDirection) {
        for (IMapElementObserver observer : observers) {
            observer.mapElementRotated(this, oldDirection);
        }
    }

    public void elementRemoved() {
        for (IMapElementObserver observer : observers) {
            observer.mapElementRemoved(this);
        }
    }
}
