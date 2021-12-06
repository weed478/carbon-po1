package agh.ics.oop;

import java.util.ArrayList;
import java.util.List;

public class SkynetEngine implements IEngine {

    private final List<HungryBot> terminators = new ArrayList<>();

    public SkynetEngine(IWorldMap map,
                        List<Vector2d> initialPositions) {
        for (Vector2d pos : initialPositions) {
            HungryBot terminator = new HungryBot(map, pos);
            terminators.add(terminator);
            map.place(terminator);
        }
    }

    @Override
    public void simulateStep() {
        for (HungryBot terminator : terminators) {
            MoveDirection m = terminator.decideNextMove();
            terminator.move(m);
        }
    }
}
