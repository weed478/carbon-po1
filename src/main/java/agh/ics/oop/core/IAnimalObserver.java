package agh.ics.oop.core;

import agh.ics.oop.objects.Animal;

public interface IAnimalObserver {

    void onAnimalEnergyChanged(Animal animal);

    void onAnimalAgeChanged(Animal animal);

    void onAnimalDied(Animal animal);
}
