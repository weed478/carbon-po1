package agh.ics.oop.map;

import agh.ics.oop.core.IMapElementObserver;
import agh.ics.oop.core.Rect;
import agh.ics.oop.core.Vector2d;
import agh.ics.oop.gui.IDrawableElement;
import agh.ics.oop.gui.IDrawableMap;
import agh.ics.oop.objects.Animal;
import agh.ics.oop.objects.Grass;
import agh.ics.oop.objects.IMapElement;

import java.util.*;

public class RectangularMap implements IAnimalAndGrassMap, IMapElementObserver, IDrawableMap {
    private final Map<Vector2d, Set<Animal>> animals = new HashMap<>();
    private final Map<Vector2d, Grass> grasses = new HashMap<>();
    private final Rect mapArea;
    private final Rect jungleArea;
    private int jungleGrassCount = 0;
    private int desertGrassCount = 0;

    public RectangularMap(Rect mapArea, Rect jungleArea) {
        if (!mapArea.contains(jungleArea)) {
            throw new IllegalArgumentException("Jungle area must be contained in map area");
        }
        this.mapArea = mapArea;
        this.jungleArea = jungleArea;
    }

    @Override
    public void registerAnimal(Animal animal) {
        animals.putIfAbsent(animal.getPosition(), new HashSet<>());
        animals.get(animal.getPosition()).add(animal);
        animal.addMapElementObserver(this);
    }

    @Override
    public void registerGrass(Grass grass) {
        grasses.put(grass.getPosition(), grass);
        grass.addMapElementObserver(this);
    }

    @Override
    public Set<Animal> getAnimalsAt(Vector2d pos) {
        Set<Animal> set = animals.get(pos);
        return set != null ? set : new HashSet<>();
    }

    @Override
    public Grass getGrassAt(Vector2d pos) {
        return grasses.get(pos);
    }

    @Override
    public void growGrass() {
        Random r = new Random();
        Vector2d p;

        if (desertGrassCount < mapArea.area() - jungleArea.area()) {
            do {
                p = mapArea.getBL().add(
                        new Vector2d(
                                r.nextInt(mapArea.width()),
                                r.nextInt(mapArea.height())
                        )
                );
            } while (jungleArea.contains(p) || getGrassAt(p) != null);
            new Grass(this, p);
            desertGrassCount++;
        }

        if (jungleGrassCount < jungleArea.area()) {
            do {
                p = jungleArea.getBL().add(
                        new Vector2d(
                                r.nextInt(jungleArea.width()),
                                r.nextInt(jungleArea.height())
                        )
                );
            } while (getGrassAt(p) != null);
            new Grass(this, p);
            jungleGrassCount++;
        }
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        return mapArea.contains(position);
    }

    @Override
    public void mapElementMoved(IMapElement object, Vector2d oldPosition) {
        if (object instanceof Animal) {
            Animal animal = (Animal) object;
            if (!animals.get(oldPosition).remove(animal)) {
                throw new IllegalArgumentException("Animal was not found at old position");
            }
            animals.putIfAbsent(animal.getPosition(), new HashSet<>());
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
    public void mapElementRemoved(IMapElement object) {
        if (object instanceof Animal) {
            Animal animal = (Animal) object;
            if (!animals.get(animal.getPosition()).remove(animal)) {
                throw new IllegalArgumentException("Animal was not found at old position");
            }
            animal.removeMapElementObserver(this);
        }
        else if (object instanceof Grass) {
            Grass grass = (Grass) object;
            grasses.remove(grass.getPosition());
            grass.removeMapElementObserver(this);
            if (jungleArea.contains(grass.getPosition())) {
                jungleGrassCount--;
            }
            else if (mapArea.contains(grass.getPosition())) {
                desertGrassCount--;
            }
            else {
                throw new IllegalStateException("Grass is outside of map!");
            }
        }
        else {
            throw new IllegalArgumentException("Invalid map element type: " + object.getClass());
        }
    }

    @Override
    public void mapElementRotated(IMapElement object, MapDirection oldDirection) {}

    @Override
    public Rect getDrawingBounds() {
        return mapArea;
    }

    @Override
    public IDrawableElement getDrawableElementAt(Vector2d pos) {
        Set<Animal> animalSet = animals.get(pos);
        if (animalSet == null || animalSet.isEmpty()) {
            return grasses.get(pos);
        }
        else {
            return animalSet.stream().findAny().get();
        }
    }
}
