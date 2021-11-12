package agh.ics.oop;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

public class SimulationEngine implements IEngine {

    private final List<Animal> animals = new ArrayList<>();
    private final Stream<MoveDirection> moves;
    private final IWorldMap map;
    private final GUIVisualizer gui;

    public SimulationEngine(Stream<MoveDirection> moves,
                            IWorldMap map,
                            List<Vector2d> initialPositions) {
        this.moves = moves;
        this.map = map;

        gui = new GUIVisualizer(map.toString());

        for (Vector2d pos : initialPositions) {
            Animal animal = new Animal(map, pos);
            animals.add(animal);
            map.place(animal);
        }
    }

    @Override
    public void run() {
        gui.pushMap(map.toString());
        gui.drawNext();

        int currentAnimal = 0;
        Iterator<MoveDirection> moveIter = moves.iterator();
        while (moveIter.hasNext()) {
            MoveDirection m = moveIter.next();
            animals.get(currentAnimal).move(m);
            currentAnimal = (currentAnimal + 1) % animals.size();

            gui.pushMap(map.toString());
        }
    }
}
