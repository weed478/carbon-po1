package agh.ics.oop.gui;

import agh.ics.oop.core.Rect;
import agh.ics.oop.core.Vector2d;
import agh.ics.oop.map.IAnimalAndGrassDrawableMap;
import agh.ics.oop.map.MapDirection;
import agh.ics.oop.map.CylindricalMap;
import agh.ics.oop.objects.Animal;
import agh.ics.oop.sim.ISimulationStateObserver;
import agh.ics.oop.sim.SimulationEngine;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;


public class App extends Application implements ISimulationStateObserver {

    private GridBuilder gridBuilder;
    private VBox root;
    private SimulationEngine simulationEngine;

    @Override
    public void init() {
        IAnimalAndGrassDrawableMap map = new CylindricalMap(
                new Rect(-5, -5, 6, 6),
                new Rect(-2, -2, 3, 3)
        );

        List<Animal> animals = new ArrayList<>();
        animals.add(new Animal(map, new Vector2d(0, 0), MapDirection.N, 100));
        animals.add(new Animal(map, new Vector2d(0, 0), MapDirection.N, 100));
        animals.add(new Animal(map, new Vector2d(0, 0), MapDirection.N, 100));
        animals.add(new Animal(map, new Vector2d(0, 0), MapDirection.N, 100));
        animals.add(new Animal(map, new Vector2d(0, 0), MapDirection.N, 100));

        simulationEngine = new SimulationEngine(500, map, animals);
        simulationEngine.addSimulationStateObserver(this);

        gridBuilder = new GridBuilder(map);
    }

    @Override
    public void start(Stage primaryStage) {
        root = new VBox();

        root.getChildren().add(gridBuilder.buildGrid());

        Scene scene = new Scene(root, 400, 400);
        primaryStage.setScene(scene);
        primaryStage.show();

        Thread simulationThread = new Thread(simulationEngine);
        primaryStage.setOnCloseRequest(e -> simulationThread.interrupt());
        simulationThread.start();
    }

    private void updateGrid() {
        root.getChildren().remove(root.getChildren().size() - 1);
        root.getChildren().add(gridBuilder.buildGrid());
    }

    @Override
    public void simulationStateChanged() {
        Platform.runLater(this::updateGrid);
    }

    @Override
    public void simulationEnded() {
        System.out.println("Simulation ended");
    }
}
