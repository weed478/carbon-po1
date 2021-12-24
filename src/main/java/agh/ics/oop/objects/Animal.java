package agh.ics.oop.objects;

import agh.ics.oop.core.SimulationConfig;
import agh.ics.oop.core.Vector2d;
import agh.ics.oop.gui.IDrawable;
import agh.ics.oop.map.IAnimalMap;
import agh.ics.oop.map.MapDirection;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.Arrays;
import java.util.Random;

public class Animal extends AbstractObservableMapElement implements IDrawable {
    private static final int GENOME_SIZE = 32;
    private static final int GENOME_RANGE = 8;

    private final SimulationConfig config;
    private final int[] genome;
    private final Random rand = new Random();
    private final IAnimalMap map;
    private int food;
    private int age = 0;
    private int numChildren = 0;
    private boolean isSelected = false;

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
        int splitIndex = p1.getFood() / (p1.getFood() + p2.getFood());

        int[] g = new int[GENOME_SIZE];

        // left/right
        if (new Random().nextBoolean()) {
            System.arraycopy(p1.genome, 0, g, 0, splitIndex);
            System.arraycopy(p2.genome, splitIndex, g, splitIndex, GENOME_SIZE - splitIndex);
        }
        else {
            System.arraycopy(p1.genome, 0, g, GENOME_SIZE - splitIndex, splitIndex);
            System.arraycopy(p2.genome, splitIndex, g, 0, GENOME_SIZE - splitIndex);
        }

        return g;
    }

    /**
     * Adam/Eve constructor.
     * Will register the animal on the map.
     * @param config simulation configuration
     * @param map animal's map
     * @param position initial position
     * @param direction initial direction
     */
    public Animal(SimulationConfig config, IAnimalMap map, Vector2d position, MapDirection direction) {
        super(position, direction);
        this.config = config;
        this.map = map;
        this.food = config.startEnergy;
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
        this.config = p1.config;
        this.map = p1.map;

        if (p1.config != p2.config) {
            throw new IllegalStateException("Parents have different configs");
        }

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

        p1.numChildren++;
        p2.numChildren++;

        isSelected = p1.isSelected | p2.isSelected;
    }

    public void select() {
        isSelected = true;
    }

    public void deselect() {
        isSelected = false;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public int[] getGenome() {
        Arrays.sort(genome);
        return genome;
    }

    public int getNumChildren() {
        return numChildren;
    }

    public boolean isAlive() {
        return getFood() > 0;
    }

    public int getFood() {
        return food;
    }

    public void passDay() {
        if (food <= 0) {
            throw new IllegalStateException("Animal does not have enough food");
        }
        food -= config.moveEnergy;
        age += 1;
    }

    public int getAge() {
        return age;
    }

    public void eatGrass(Grass grass) {
        if (!grass.getPosition().equals(getPosition())) {
            throw new IllegalArgumentException("Cannot eat, grass is not at the animal position");
        }
        grass.elementRemoved();
        food += config.plantEnergy;
    }

    public boolean canBreed() {
        return getFood() >= config.minBreedingEnergy;
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
    public void draw(GraphicsContext gc) {
        gc.save();
        gc.translate(0.5, 0.5);
        gc.scale(0.8, 0.8);

        gc.beginPath();
        gc.arc(0, 0, 0.5, 0.5, 0, 360);
        gc.closePath();
        gc.clip();

        gc.setFill(isSelected ? Color.DARKGOLDENROD : Color.DARKRED);
        gc.fill();

        gc.save();
        gc.rotate(180);
        gc.setFill(isSelected ? Color.GOLDENROD : Color.RED);
        double healthBar = Math.min((double) getFood() / config.startEnergy, 1);
        gc.fillRect(-0.5, -0.5, 1, healthBar);
        gc.restore();

        gc.setStroke(Color.BLACK);
        gc.setLineWidth(0.1);
        gc.stroke();

        gc.rotate(getDirection().angle());
        gc.setLineWidth(0.1);
        gc.setStroke(Color.BLACK);
        gc.beginPath();
        gc.moveTo(
                -0.2,
                0
        );
        gc.lineTo(
                0,
                -0.3
        );
        gc.lineTo(
                0.2,
                0
        );
        gc.stroke();

        gc.restore();
    }
}
