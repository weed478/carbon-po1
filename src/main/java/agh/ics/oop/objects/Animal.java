package agh.ics.oop.objects;

import agh.ics.oop.core.MoveDirection;
import agh.ics.oop.core.Vector2d;
import agh.ics.oop.gui.IDrawableElement;
import agh.ics.oop.map.IAnimalMap;
import agh.ics.oop.map.MapDirection;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;

public class Animal extends AbstractObservableMapElement implements IDrawableElement {
    private final IAnimalMap map;
    private int food;

    public Animal(IAnimalMap map, Vector2d position, MapDirection direction, int food) {
        super(position, direction);
        this.map = map;
        this.food = food;
        map.registerAnimal(this);
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

    public boolean canBreed() {
        return getFood() > 1;
    }

    public Animal breed(Animal other) {
        if (!canBreed() || !other.canBreed()) {
            throw new IllegalStateException("Animals cannot breed");
        }

        int childFood = food / 2 + other.food / 2;

        if (childFood < 1) {
            throw new IllegalStateException("Child cannot be given 0 food");
        }

        food -= food / 2;
        other.food -= other.food / 2;

        return new Animal(map, getPosition(), getDirection(), childFood);
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

    @Override
    public Node getDrawableNode(int size) {
        Circle circle = new Circle();
        circle.setRadius(0.4 * size);
        circle.setFill(new Color(1, 0, 0, 1));

        Path arrow = new Path();
        MoveTo moveTo;
        LineTo line1, line2;

        switch (getDirection()) {
            case NORTH:
                moveTo = new MoveTo(-0.2 * size, 0);
                line1 = new LineTo(0, -0.3 * size);
                line2 = new LineTo(0.2 * size,0);
                break;
            case EAST:
                moveTo = new MoveTo(0, 0.2 * size);
                line1 = new LineTo(0.3 * size, 0);
                line2 = new LineTo(0, -0.2 * size);
                break;
            case SOUTH:
                moveTo = new MoveTo(-0.2 * size, 0);
                line1 = new LineTo(0, 0.3 * size);
                line2 = new LineTo(0.2 * size,0);
                break;
            case WEST:
                moveTo = new MoveTo(0, 0.2 * size);
                line1 = new LineTo(-0.3 * size, 0);
                line2 = new LineTo(0, -0.2 * size);
                break;
            default:
                throw new IllegalStateException("Invalid animal direction: " + getDirection());
        }

        arrow.getElements().addAll(moveTo, line1, line2);

        return new Group(circle, arrow);
    }
}
