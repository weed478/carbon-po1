package agh.ics.oop.map;

import agh.ics.oop.core.Rect;
import agh.ics.oop.core.Vector2d;
import agh.ics.oop.gui.IDrawable;
import agh.ics.oop.objects.Animal;
import agh.ics.oop.objects.Grass;

import java.util.List;

/**
 * Describes a limited area map with animals and grass.
 */
public interface IWorldMap {

    /**
     * Defines how movement takes place on a map.
     * Maps position to new position based on given move direction.
     * Used to implement wrapping and bounded maps.
     * @param position Initial position.
     * @param direction Move direction.
     * @return Position after movement.
     */
    Vector2d moveFrom(Vector2d position, MapDirection direction);

    /**
     * @return Total map area.
     */
    Rect getMapArea();

    /**
     * Returns grass at given position.
     * @param pos Position to check for grass.
     * @return Grass or null if no grass exists at pos.
     */
    Grass getGrassAt(Vector2d pos);

    /**
     * Spawn new grass at unoccupied position.
     */
    void growGrass();

    /**
     * Add grass to map.
     */
    void registerGrass(Grass grass);

    /**
     * @return Number of plants on map.
     */
    int getGrassCount();

    /**
     * Get a list of animals at position sorted by energy (descending).
     * @return List of animals (can be empty but not null).
     */
    List<Animal> getAnimalsAt(Vector2d pos);

    /**
     * Add animal to map.
     */
    void registerAnimal(Animal animal);

    /**
     * @return Number of animals on map.
     */
    int getAnimalCount();

    /**
     * Returns drawable elements that visualize given position.
     * @return Drawables at position.
     */
    List<IDrawable> getDrawablesAt(Vector2d pos);
}
