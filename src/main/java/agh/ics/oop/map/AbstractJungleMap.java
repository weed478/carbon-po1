package agh.ics.oop.map;

import agh.ics.oop.core.IMapElementObserver;
import agh.ics.oop.core.Rect;
import agh.ics.oop.core.Vector2d;
import agh.ics.oop.gui.DesertField;
import agh.ics.oop.gui.IDrawable;
import agh.ics.oop.gui.JungleField;
import agh.ics.oop.objects.Animal;
import agh.ics.oop.objects.Grass;
import agh.ics.oop.objects.IMapElement;

import java.util.*;

public abstract class AbstractJungleMap implements IWorldMap, IMapElementObserver {
    private final Map<Vector2d, List<Animal>> animals = new HashMap<>();
    private final Map<Vector2d, Grass> grasses = new HashMap<>();
    protected final Rect mapArea;
    protected final Rect jungleArea;

    public AbstractJungleMap(Rect mapArea, Rect jungleArea) {
        if (!mapArea.contains(jungleArea)) {
            throw new IllegalArgumentException("Jungle area must be contained in map area");
        }
        this.mapArea = mapArea;
        this.jungleArea = jungleArea;
    }

    @Override
    public synchronized void registerAnimal(Animal animal) {
        animals.putIfAbsent(animal.getPosition(), new ArrayList<>());
        animals.get(animal.getPosition()).add(animal);
        animal.addMapElementObserver(this);
    }

    @Override
    public synchronized void registerGrass(Grass grass) {
        grasses.put(grass.getPosition(), grass);
        grass.addMapElementObserver(this);
    }

    @Override
    public Rect getMapArea() {
        return mapArea;
    }

    @Override
    public synchronized List<Animal> getAnimalsAt(Vector2d pos) {
        List<Animal> set = animals.get(pos);
        if (set == null) {
            return new ArrayList<>();
        }
        set.sort(Comparator.comparingInt(Animal::getFood).reversed());
        return set;
    }

    @Override
    public synchronized int getAnimalCount() {
        int count = 0;
        for (List<Animal> set : animals.values()) {
            count += set.size();
        }
        return count;
    }

    @Override
    public synchronized Grass getGrassAt(Vector2d pos) {
        return grasses.get(pos);
    }

    @Override
    public synchronized int getGrassCount() {
        return grasses.size();
    }

    @Override
    public synchronized void growGrass() {
        List<Vector2d> availableJungleFields = new ArrayList<>();
        List<Vector2d> availableDesertFields = new ArrayList<>();
        for (int x = mapArea.left(); x < mapArea.right(); x++) {
            for (int y = mapArea.bottom(); y < mapArea.top(); y++) {
                Vector2d pos = new Vector2d(x, y);
                if (getGrassAt(pos) == null && getAnimalsAt(pos).isEmpty()) {
                    if (jungleArea.contains(pos)) {
                        availableJungleFields.add(pos);
                    }
                    else {
                        availableDesertFields.add(pos);
                    }
                }
            }
        }

        if (!availableJungleFields.isEmpty()) {
            Vector2d pos = availableJungleFields.get(new Random().nextInt(availableJungleFields.size()));
            new Grass(this, pos);
        }

        if (!availableDesertFields.isEmpty()) {
            Vector2d pos = availableDesertFields.get(new Random().nextInt(availableDesertFields.size()));
            new Grass(this, pos);
        }
    }

    @Override
    public synchronized void mapElementMoved(IMapElement object, Vector2d oldPosition) {
        if (object instanceof Animal) {
            Animal animal = (Animal) object;
            if (!animals.get(oldPosition).remove(animal)) {
                throw new IllegalArgumentException("Animal was not found at old position");
            }
            animals.putIfAbsent(animal.getPosition(), new ArrayList<>());
            animals.get(animal.getPosition()).add(animal);
        }
        else if (object instanceof Grass) {
            throw new RuntimeException("Grass should not move!");
        }
        else {
            throw new IllegalArgumentException("Invalid map element type: " + object.getClass());
        }
    }

    @Override
    public synchronized void mapElementRemoved(IMapElement object) {
        if (object instanceof Animal) {
            if (!animals.get(object.getPosition()).remove(object)) {
                throw new IllegalArgumentException("Animal was not found at old position");
            }
        }
        else if (object instanceof Grass) {
            grasses.remove(object.getPosition());
        }
        else {
            throw new IllegalArgumentException("Invalid map element type: " + object.getClass());
        }
    }

    @Override
    public void mapElementRotated(IMapElement object, MapDirection oldDirection) {}

    @Override
    public synchronized List<IDrawable> getDrawablesAt(Vector2d pos) {
        List<IDrawable> drawables = new ArrayList<>();

        if (jungleArea.contains(pos)) {
            drawables.add(new JungleField());
        }
        else if (mapArea.contains(pos)) {
            drawables.add(new DesertField());
        }
        else {
            throw new IllegalArgumentException("Position is outside of map: " + pos);
        }

        Grass grass = grasses.get(pos);
        if (grass != null) {
            drawables.add(grass);
        }

        List<Animal> animalSet = animals.get(pos);
        if (animalSet != null && !animalSet.isEmpty()) {
            // first animal with most energy
            Animal animal = animalSet.get(0);
            for (Animal a : animalSet) {
                // isSelected has priority
                if (a.isSelected()) {
                    animal = a;
                    break;
                }
            }
            drawables.add(animal);
        }

        return drawables;
    }
}
