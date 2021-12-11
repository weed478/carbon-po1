package agh.ics.oop.sim;

import agh.ics.oop.core.Vector2d;
import agh.ics.oop.map.IAnimalAndGrassMap;
import agh.ics.oop.objects.Animal;
import agh.ics.oop.objects.Grass;

import java.util.*;
import java.util.stream.Collectors;

public class SimulationEngine implements Runnable {

    private final IAnimalAndGrassMap map;
    private final List<Animal> animals;
    private final int simulationDelay;
    private final Set<ISimulationStateObserver> observers = new HashSet<>();

    public SimulationEngine(int simulationDelay, IAnimalAndGrassMap map, List<Animal> animals) {
        this.simulationDelay = simulationDelay;
        this.map = map;
        this.animals = animals;
    }

    public void addSimulationStateObserver(ISimulationStateObserver observer) {
        observers.add(observer);
    }

    public void removeSimulationStateObserver(ISimulationStateObserver observer) {
        observers.remove(observer);
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
        Iterator<Animal> i = animals.iterator();
        while (i.hasNext()) {
            Animal animal = i.next();
            if (animal.isAlive()) continue;
            animal.elementRemoved();
            i.remove();
        }
    }

    private void moveAnimals() {
        for (Animal animal : animals) {
            int turn = animal.decideMovement();
            animal.move(turn);
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
        Set<Vector2d> processedPositions = new HashSet<>();
        List<Animal> newAnimals = new ArrayList<>();
        for (Animal animal : animals) {
            Vector2d pos = animal.getPosition();
            if (processedPositions.contains(pos)) continue;
            processedPositions.add(pos);

            Set<Animal> candidates = map.getAnimalsAt(pos);
            List<Animal> parents = candidates.stream()
                    .sorted(Comparator.comparingInt(Animal::getFood).reversed())
                    .limit(2)
                    .collect(Collectors.toList());
            if (parents.size() != 2 || !parents.get(0).canBreed() || !parents.get(1).canBreed())
                continue;
            Animal child = parents.get(0).breed(parents.get(1));
            newAnimals.add(child);
        }
        animals.addAll(newAnimals);
    }

    private void growPlants() {
        map.growGrass();
    }

    private void decrementAnimalEnergy() {
        for (Animal animal : animals) {
            animal.decrementFood();
        }
    }

    @Override
    public void run() {
        for (;;) {
            try {
                Thread.sleep(simulationDelay);
                simulateDay();
                simulationStateChanged();
            } catch (InterruptedException e) {
                break;
            }
        }
        simulationEnded();
    }

    private void simulationStateChanged() {
        for (ISimulationStateObserver observer : observers) {
            observer.simulationStateChanged();
        }
    }

    private void simulationEnded() {
        for (ISimulationStateObserver observer : observers) {
            observer.simulationEnded();
        }
    }
}
