package agh.ics.oop;

import java.util.HashSet;
import java.util.Set;

public class Animal {
    private MapDirection direction = MapDirection.NORTH;
    private Vector2d pos = new Vector2d(2, 2);
    private final IWorldMap map;
    private final Set<IPositionChangeObserver> observers = new HashSet<>();

    public Animal(IWorldMap map) {
        this.map = map;
    }

    public Animal(IWorldMap map, Vector2d initialPosition) {
        this.map = map;
        this.pos = initialPosition;
    }

    public void addObserver(IPositionChangeObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(IPositionChangeObserver observer) {
        observers.remove(observer);
    }

    public void positionChanged(Vector2d oldPos, Vector2d newPos) {
        for (IPositionChangeObserver observer : observers) {
            observer.positionChanged(oldPos, newPos);
        }
    }

    public MapDirection getDirection() {
        return direction;
    }

    public Vector2d getPos() {
        return pos;
    }

    public boolean isAt(Vector2d pos) {
        return getPos().equals(pos);
    }

    public void move(MoveDirection dir) {
        switch (dir) {
            case RIGHT:
                this.direction = getDirection().next();
                return;
            case LEFT:
                this.direction = getDirection().previous();
                return;
        }

        Vector2d newPos = getPos();

        switch (dir) {
            case FORWARD:
                newPos = newPos.add(getDirection().toUnitVector());
                break;
            case BACKWARD:
                newPos = newPos.subtract(getDirection().toUnitVector());
                break;
        }

        if (map.canMoveTo(newPos)) {
            tryEat(newPos);
            Vector2d oldPos = this.pos;
            this.pos = newPos;
            positionChanged(oldPos, newPos);
        }
    }

    @Override
    public String toString() {
        switch (direction) {
            case NORTH:
                return "^";
            case SOUTH:
                return "v";
            case WEST:
                return "<";
            case EAST:
                return ">";
            default:
                throw new IllegalArgumentException("Invalid animal direction");
        }
    }

    private void tryEat(Vector2d pos) {
        Object inFront = map.objectAt(pos);
        if (inFront != null && inFront.getClass() == Grass.class) {
            // if map contains grass it has to be a GrassField
            GrassField grassField = (GrassField) map;
            grassField.eatGrass(pos);
        }
    }
}
