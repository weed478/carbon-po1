package agh.ics.oop.map;

import agh.ics.oop.core.Vector2d;
import agh.ics.oop.objects.Animal;

import java.util.List;

public interface IAnimalMap {

    List<Animal> getAnimalsAt(Vector2d pos);
}
