package agh.ics.oop.objects;

import agh.ics.oop.core.MoveDirection;
import agh.ics.oop.core.Vector2d;
import agh.ics.oop.map.IWorldMap;
import agh.ics.oop.map.MapDirection;

public class Animal extends AbstractObservableMapElement {
    private final IWorldMap map;
    private int food;

    public Animal(IWorldMap map, Vector2d position, MapDirection direction, int food) {
        super(position, direction);
        this.map = map;
        this.food = food;
    }

    public boolean isAlive() {
        return getFood() > 0;
    }

    public int getFood() {
        return food;
    }

    public void decrementFood() {
        if (food <= 0) {
            throw new IllegalStateException("Animal does not have enough food");
        }
        food--;
    }

    public void eatGrass(Grass grass) {
        if (!grass.getPosition().equals(getPosition())) {
            throw new IllegalArgumentException("Cannot eat, grass is not at the animal position");
        }
        grass.elementRemoved();
        food++;
    }

    public MoveDirection decideMovement() {
        // TODO genome based movement
        return MoveDirection.FORWARD;
    }

    public void move(MoveDirection move) {
        switch (move) {
            case LEFT:
                setDirection(getDirection().previous());
                return;
            case RIGHT:
                setDirection(getDirection().next());
                return;
        }

        Vector2d newPos;

        switch (move) {
            case BACKWARD:
                newPos = getPosition().subtract(getDirection().toUnitVector());
                break;
            case FORWARD:
                newPos = getPosition().add(getDirection().toUnitVector());
                break;
            default:
                throw new IllegalArgumentException("Invalid move direction: " + move);
        }

        if (map.canMoveTo(newPos)) {
            setPosition(newPos);
        }
    }

    @Override
    public String toString() {
        switch (getDirection()) {
            case NORTH:
                return "^";
            case SOUTH:
                return "v";
            case WEST:
                return "<";
            case EAST:
                return ">";
            default:
                throw new IllegalStateException("Invalid animal direction");
        }
    }
}
