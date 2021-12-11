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
    private static final int GENOME_SIZE = 32;
    private static final int GENOME_RANGE = 8;

    private final int[] genome;
    private final Random rand = new Random();
    private final IAnimalMap map;
    private int food;

    /**
     * Generates random genome.
     * @return genome array
     */
    private static int[] makeRandomGenome() {
        return new Random().ints(GENOME_SIZE, 0, GENOME_RANGE).toArray();
    }

    /**
     * Creates new genome based on parents' genomes.
     * @param p1 first parent
     * @param p2 second parent
     * @return child genome
     */
    private static int[] crossGenome(Animal p1, Animal p2) {
        // TODO implement crossGenome
        return makeRandomGenome();
    }

    /**
     * Adam/Eve constructor.
     * Will register the animal on the map.
     * @param map animal's map
     * @param position initial position
     * @param direction initial direction
     * @param food initial food
     */
    public Animal(IAnimalMap map, Vector2d position, MapDirection direction, int food) {
        super(position, direction);
        this.map = map;
        this.food = food;
        this.genome = makeRandomGenome();
        map.registerAnimal(this);
    }

    /**
     * Breeding constructor.
     * Child inherits parents' map and position.
     * @throws IllegalStateException if breeding was not possible
     * @param p1 first parent
     * @param p2 second parent
     */
    public Animal(Animal p1, Animal p2) {
        super(p1.getPosition(), p1.getDirection());
        this.map = p1.map;

        if (!p1.getPosition().equals(p2.getPosition())) {
            throw new IllegalStateException("Parents are not on the same field");
        }

        if (p1.map != p2.map) {
            throw new IllegalStateException("Parents are not on the same map");
        }

        if (!p1.canBreed() || !p2.canBreed()) {
            throw new IllegalStateException("Parents cannot breed");
        }

        food = p1.food / 4 + p2.food / 4;

        if (food < 1) {
            throw new IllegalStateException("Child cannot be given 0 food");
        }

        p1.food -= p1.food / 4;
        p2.food -= p2.food / 4;

        genome = crossGenome(p1, p2);

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
        return getFood() >= 4;
    }

    public Animal breed(Animal other) {
        return new Animal(this, other);
    }

    public int decideMovement() {
        return genome[rand.nextInt(genome.length)];
    }

    public void move(int turn) {
        if (turn % 4 != 0) {
            setDirection(getDirection().turn(turn));
            return;
        }

        Vector2d newPos = map.moveFrom(getPosition(), getDirection().turn(turn));
        setPosition(newPos);
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
