package agh.ics.oop;

import java.util.ArrayList;
import java.util.List;

public class SkynetEngine implements IEngine {

    private final List<HungryBot> terminators = new ArrayList<>();
    private final IWorldMap map;
    private final GUIVisualizer gui;
    private final int numMoves;

    public SkynetEngine(int numMoves,
                        IWorldMap map,
                        List<Vector2d> initialPositions) {
        this.map = map;
        this.numMoves = numMoves;
        gui = new GUIVisualizer(map.toString());

        for (Vector2d pos : initialPositions) {
            HungryBot terminator = new HungryBot(map, pos);
            terminators.add(terminator);
            map.place(terminator);
        }
    }

    @Override
    public void run() {
        gui.pushMap(map.toString());
        gui.drawNext();

        for (int i = 0; i < numMoves; i++) {
            for (HungryBot terminator : terminators) {
                MoveDirection m = terminator.decideNextMove();
                terminator.move(m);
                gui.pushMap(map.toString());
            }
        }
    }
}
