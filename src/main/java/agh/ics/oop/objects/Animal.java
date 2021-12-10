package agh.ics.oop.objects;

import agh.ics.oop.core.Vector2d;
import agh.ics.oop.gui.IDrawableElement;
import agh.ics.oop.map.IAnimalMap;
import agh.ics.oop.map.MapDirection;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.transform.Rotate;

import java.util.Random;

public class Animal extends AbstractObservableMapElement implements IDrawableElement {
    private static final int MAX_HEALTH_BAR = 20;
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

    public int decideMovement() {
        // TODO genome based movement
        return new Random().nextInt(8);
    }

    public void move(int turn) {
        Vector2d newPos;

        switch (turn % 8) {
            case 0:
                newPos = getPosition().add(getDirection().toUnitVector());
                break;
            case 4:
                newPos = getPosition().subtract(getDirection().toUnitVector());
                break;
            default:
                setDirection(getDirection().turn(turn));
                return;
        }

        if (map.canMoveTo(newPos)) {
            setPosition(newPos);
        }
    }

    @Override
    public Node getDrawableNode(int size) {
        Circle circle = new Circle();
        circle.setCenterX(0);
        circle.setCenterY(0);
        circle.setRadius(0.4 * size);
        circle.setFill(Color.DARKRED);

        Shape healthBar;

        if (getFood() < MAX_HEALTH_BAR) {
            double healthBarHeight = Math.min(1, (double) getFood() / MAX_HEALTH_BAR) * 0.8 * size;
            double healthBarY = 0.4 * size - healthBarHeight;
            double healthBarX = Math.sqrt(0.4 * size * 0.4 * size - healthBarY * healthBarY);
            Path healthBarArc = new Path();
            healthBarArc.getElements().addAll(
                    new MoveTo(-healthBarX, healthBarY),
                    new ArcTo(
                            0.4 * size,
                            0.4 * size,
                            0,
                            healthBarX,
                            healthBarY,
                            healthBarY < 0,
                            false)
            );
            healthBarArc.setFill(new Color(1, 0, 0, 1));
            healthBar = healthBarArc;
        }
        else {
            healthBar = new Circle(0, 0, 0.4 * size, Color.RED);
        }

        Path arrow = new Path();
        MoveTo moveTo = new MoveTo(-0.2 * size, 0);
        LineTo line1 = new LineTo(0, -0.3 * size);
        LineTo line2 = new LineTo(0.2 * size,0);
        arrow.getElements().addAll(moveTo, line1, line2);
        arrow.getTransforms().add(new Rotate(getDirection().angle(), 0, 0));

        return new Group(circle, healthBar, arrow);
    }
}
