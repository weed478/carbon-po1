package agh.ics.oop;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

public class SimulationEngine implements IEngine {

    private final List<Animal> animals = new ArrayList<>();
    private final Stream<MoveDirection> moves;

    public SimulationEngine(Stream<MoveDirection> moves,
                            IWorldMap map,
                            List<Vector2d> initialPositions) {
        this.moves = moves;

        for (Vector2d pos : initialPositions) {
            Animal animal = new Animal(map, pos);
            animals.add(animal);
            map.place(animal);
        }
    }

    @Override
    public void run() {
        int currentAnimal = 0;
        Iterator<MoveDirection> moveIter = moves.iterator();
        while (moveIter.hasNext()) {
            MoveDirection m = moveIter.next();
            animals.get(currentAnimal).move(m);
            currentAnimal = (currentAnimal + 1) % animals.size();
        }
    }
}
