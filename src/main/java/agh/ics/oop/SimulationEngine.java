package agh.ics.oop;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

/**
 * Can simulate a given Stream of moves.
 */
public class SimulationEngine extends AbstractSimEngine {

    private final List<Animal> animals;
    private final Iterator<MoveDirection> moves;
    private int currentAnimal = 0;

    public SimulationEngine(Stream<MoveDirection> moves,
                            List<Animal> animals,
                            int moveDelay) {
        super(moveDelay);
        this.moves = moves.iterator();
        this.animals = animals;
    }

    @Override
    public void simulateStep() {
        if (!moves.hasNext()) return;
        MoveDirection m = moves.next();
        animals.get(currentAnimal).move(m);
        currentAnimal = (currentAnimal + 1) % animals.size();
    }
}
