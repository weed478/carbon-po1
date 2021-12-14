package agh.ics.oop;

import java.util.List;

/**
 * Infinite simulation with animal AIs.
 */
public class SkynetEngine extends AbstractSimEngine {

    private final List<HungryBot> terminators;

    public SkynetEngine(List<HungryBot> animals,
                        int moveDelay) {
        super(moveDelay);
        terminators = animals;
    }

    @Override
    public void simulateStep() {
        for (HungryBot terminator : terminators) {
            MoveDirection m = terminator.decideNextMove();
            terminator.move(m);
        }
    }
}
