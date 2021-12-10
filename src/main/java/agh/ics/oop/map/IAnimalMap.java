package agh.ics.oop.map;

import agh.ics.oop.core.Vector2d;
import agh.ics.oop.objects.Animal;

import java.util.Set;

public interface IAnimalMap extends IWorldMap {

    Set<Animal> getAnimalsAt(Vector2d pos);

    void registerAnimal(Animal animal);
}
