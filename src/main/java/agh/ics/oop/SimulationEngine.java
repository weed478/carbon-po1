package agh.ics.oop;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

public class SimulationEngine implements IEngine {

    private final List<Animal> animals = new ArrayList<>();
    private Iterator<MoveDirection> moves;
    private int currentAnimal = 0;

    public SimulationEngine(Stream<MoveDirection> moves,
                            IWorldMap map,
                            List<Vector2d> initialPositions) {
        this.moves = moves.iterator();

        for (Vector2d pos : initialPositions) {
            Animal animal = new Animal(map, pos);
            animals.add(animal);
            map.place(animal);
        }
    }

    @Override
    public void simulateStep() {
        if (!moves.hasNext()) return;
        MoveDirection m = moves.next();
        animals.get(currentAnimal).move(m);
        currentAnimal = (currentAnimal + 1) % animals.size();
    }
}
