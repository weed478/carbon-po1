package agh.ics.oop.sim;

import agh.ics.oop.core.SimulationConfig;
import agh.ics.oop.core.Vector2d;
import agh.ics.oop.map.IWorldMap;
import agh.ics.oop.map.MapDirection;
import agh.ics.oop.objects.Animal;
import agh.ics.oop.objects.Grass;

import java.util.*;

public class SimulationEngine implements Runnable {

    private final IWorldMap map;
    private final List<Animal> animals;
    private int simulationDelay;
    private final Set<ISimulationStateObserver> observers = new HashSet<>();
    private boolean isRunning = true;
    private int averageDeadLifetimeSum = 0;
    private int allDeadAnimalsCount = 0;
    private int day = 0;
    private final SimulationConfig config;
    private int magicLeft = 3;

    public SimulationEngine(int simulationDelay, IWorldMap map, List<Animal> animals, SimulationConfig config) {
        this.simulationDelay = simulationDelay;
        this.map = map;
        this.animals = animals;
        this.config = config;
    }

    public synchronized void addSimulationStateObserver(ISimulationStateObserver observer) {
        observers.add(observer);
    }

    public synchronized void removeSimulationStateObserver(ISimulationStateObserver observer) {
        observers.remove(observer);
    }

    public synchronized void setSimulationDelay(int simulationDelay) {
        this.simulationDelay = simulationDelay;
    }

    public synchronized void pause() {
        isRunning = false;
    }

    public synchronized void resume() {
        isRunning = true;
        notifyAll();
    }

    public synchronized boolean isRunning() {
        return isRunning;
    }

    public synchronized int getDay() {
        return day;
    }

    private void simulateDay() {
        removeDeadAnimals();
        moveAnimals();
        processEating();
        processReproduction();
        doMagic();
        growPlants();
        passDay();
    }

    private void doMagic() {
        if (!config.isMagic || magicLeft <= 0 || animals.size() != 5) return;
        for (int i = 0; i < 5; i++) {
            List<Vector2d> availablePos = new ArrayList<>();
            for (int x = map.getMapArea().left(); x < map.getMapArea().right(); x++) {
                for (int y = map.getMapArea().bottom(); y < map.getMapArea().top(); y++) {
                    Vector2d pos = new Vector2d(x, y);
                    if (map.getAnimalsAt(pos).isEmpty() && map.getGrassAt(pos) == null) {
                        availablePos.add(pos);
                    }
                }
            }
            if (availablePos.size() < 1) {
                break;
            }
            Vector2d pos = availablePos.get(new Random().nextInt(availablePos.size()));
            animals.add(new Animal(animals.get(new Random().nextInt(animals.size())), pos, MapDirection.N));
        }
        magicLeft--;
        magicHappened();
    }

    private void removeDeadAnimals() {
        Iterator<Animal> i = animals.iterator();
        while (i.hasNext()) {
            Animal animal = i.next();
            if (animal.isAlive()) continue;
            animal.elementRemoved();
            i.remove();
            averageDeadLifetimeSum += animal.getAge();
            allDeadAnimalsCount++;
        }
    }

    private void moveAnimals() {
        for (Animal animal : animals) {
            int turn = animal.decideMovement();
            animal.move(turn);
        }
    }

    private void processEating() {
        for (Animal animal : animals) {
            Vector2d pos = animal.getPosition();
            Grass grass = map.getGrassAt(pos);
            if (grass != null) {
                animal = map.getAnimalsAt(pos).get(0);
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

            List<Animal> parents = map.getAnimalsAt(pos);
            if (parents.size() < 2 || !parents.get(0).canBreed() || !parents.get(1).canBreed())
                continue;
            Animal child = parents.get(0).breed(parents.get(1));
            newAnimals.add(child);
        }
        animals.addAll(newAnimals);
    }

    private void growPlants() {
        map.growGrass();
    }

    private void passDay() {
        for (Animal animal : animals) {
            animal.passDay();
        }
        day++;
    }

    @Override
    public void run() {
        for (;;) {
            try {
                int delay;
                synchronized (this) {
                    while (!isRunning) wait();
                    delay = simulationDelay;
                }
                Thread.sleep(delay);
                synchronized (map) {
                    simulateDay();
                }
                simulationStateChanged();
            } catch (InterruptedException e) {
                break;
            }
        }
        simulationEnded();
    }

    public List<Animal> getAnimals() {
        synchronized (map) {
            return animals;
        }
    }

    public SimulationStatistics getStatistics() {
        synchronized (map) {
            double averageFood = 0;
            double averageChildren = 0;
            Map<int[], Integer> genomeCounts = new HashMap<>();

            for (Animal a : animals) {
                averageFood += a.getFood();
                averageChildren += a.getNumChildren();
                int[] genome = a.getGenome();
                genomeCounts.putIfAbsent(genome, 0);
                genomeCounts.replace(genome, genomeCounts.get(genome) + 1);
            }

            averageFood /= animals.size();
            averageChildren /= animals.size();

            int[] dominantGenome = null;
            int dominantGenomeCount = 0;
            for (Map.Entry<int[], Integer> e : genomeCounts.entrySet()) {
                if (e.getValue() > dominantGenomeCount) {
                    dominantGenome = e.getKey();
                    dominantGenomeCount = e.getValue();
                }
            }

            return new SimulationStatistics(
                    day,
                    map.getAnimalCount(),
                    map.getGrassCount(),
                    averageFood,
                    allDeadAnimalsCount == 0 ? 0 : ((double) averageDeadLifetimeSum / allDeadAnimalsCount),
                    averageChildren,
                    dominantGenome
            );
        }
    }

    private void simulationStateChanged() {
        for (ISimulationStateObserver observer : observers) {
            observer.simulationStateChanged();
        }
    }

    private void magicHappened() {
        for (ISimulationStateObserver observer : observers) {
            observer.magicHappened();
        }
    }

    private void simulationEnded() {
        for (ISimulationStateObserver observer : observers) {
            observer.simulationEnded();
        }
    }
}
