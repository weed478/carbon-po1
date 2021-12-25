package agh.ics.oop.objects;

import agh.ics.oop.core.IMapElementObserver;
import agh.ics.oop.core.Vector2d;
import agh.ics.oop.map.MapDirection;

import java.util.HashSet;
import java.util.Set;

public abstract class AbstractObservableMapElement extends AbstractMapElement {

    private final Set<IMapElementObserver> observers = new HashSet<>();

    public AbstractObservableMapElement(Object world, Vector2d position, MapDirection direction) {
        super(world, position, direction);
    }

    public void addMapElementObserver(IMapElementObserver observer) {
        synchronized (world) {
            observers.add(observer);
        }
    }

    public void removeMapElementObserver(IMapElementObserver observer) {
        synchronized (world) {
            observers.remove(observer);
        }
    }

    @Override
    protected void setPosition(Vector2d position) {
        synchronized (world) {
            Vector2d oldPosition = getPosition();
            super.setPosition(position);
            positionChanged(oldPosition);
        }
    }

    @Override
    protected void setDirection(MapDirection direction) {
        synchronized (world) {
            MapDirection oldDirection = getDirection();
            super.setDirection(direction);
            directionChanged(oldDirection);
        }
    }

    private void positionChanged(Vector2d oldPos) {
        synchronized (world) {
            for (IMapElementObserver observer : observers) {
                observer.mapElementMoved(this, oldPos);
            }
        }
    }

    private void directionChanged(MapDirection oldDirection) {
        synchronized (world) {
            for (IMapElementObserver observer : observers) {
                observer.mapElementRotated(this, oldDirection);
            }
        }
    }

    public void elementRemoved() {
        synchronized (world) {
            for (IMapElementObserver observer : observers) {
                observer.mapElementRemoved(this);
            }
            observers.clear();
        }
    }
}
