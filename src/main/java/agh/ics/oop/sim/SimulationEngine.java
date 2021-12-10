package agh.ics.oop.sim;

import agh.ics.oop.core.MoveDirection;
import agh.ics.oop.core.Vector2d;
import agh.ics.oop.map.IAnimalAndGrassWorldMap;
import agh.ics.oop.objects.Animal;
import agh.ics.oop.objects.Grass;

import java.util.List;

public class SimulationEngine {

    private final IAnimalAndGrassWorldMap map;
    private final List<Animal> animals;

    public SimulationEngine(IAnimalAndGrassWorldMap map, List<Animal> animals) {
        this.map = map;
        this.animals = animals;
    }

    private void simulateDay() {
        removeDeadAnimals();
        moveAnimals();
        processEating();
        processReproduction();
        growPlants();
        decrementAnimalEnergy();
    }

    private void removeDeadAnimals() {
        animals.removeIf(a -> !a.isAlive());
    }

    private void moveAnimals() {
        for (Animal animal : animals) {
            MoveDirection move = animal.decideMovement();
            animal.move(move);
        }
    }

    private void processEating() {
        // TODO fight for food
        for (Animal animal : animals) {
            Vector2d pos = animal.getPosition();
            Grass grass = map.getGrassAt(pos);
            if (grass != null) {
                animal.eatGrass(grass);
            }
        }
    }

    private void processReproduction() {
        for (Animal animal : animals) {
            Vector2d pos = animal.getPosition();
            List<Animal> candidates = map.getAnimalsAt(pos);
        }
    }

    private void growPlants() {
        map.growGrass();
    }

    private void decrementAnimalEnergy() {
        for (Animal animal : animals) {
            animal.decrementFood();
        }
    }
}
