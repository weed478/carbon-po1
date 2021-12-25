package agh.ics.oop.core;

import agh.ics.oop.objects.Animal;

public interface IAnimalObserver {

    void onAnimalEnergyChanged(Animal animal);

    void onAnimalAgeChanged(Animal animal);

    void onAnimalHadChild(Animal parent, Animal child);

    void onAnimalDied(Animal animal);
}
