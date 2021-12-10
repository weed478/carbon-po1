package agh.ics.oop.gui;

import agh.ics.oop.core.Rect;
import agh.ics.oop.core.Vector2d;
import agh.ics.oop.map.MapDirection;
import agh.ics.oop.map.RectangularMap;
import agh.ics.oop.objects.Animal;
import agh.ics.oop.sim.SimulationEngine;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;


public class App extends Application implements Runnable {

    private GridBuilder gridBuilder;
    private VBox root;
    private SimulationEngine simulationEngine;

    @Override
    public void init() {
        RectangularMap map = new RectangularMap(
                new Rect(-5, -5, 6, 6),
                new Rect(-2, -2, 3, 3)
        );

        List<Animal> animals = new ArrayList<>();
        animals.add(new Animal(map, new Vector2d(0, 0), MapDirection.NORTH, 100));

        simulationEngine = new SimulationEngine(map, animals);
        gridBuilder = new GridBuilder(map);
    }

    @Override
    public void start(Stage primaryStage) {
        root = new VBox();

        root.getChildren().add(gridBuilder.buildGrid());

        Scene scene = new Scene(root, 400, 400);
        primaryStage.setScene(scene);
        primaryStage.show();

        new Thread(this).start();
    }

    @Override
    public void run() {
        for (;;) {
            try {
                Thread.sleep(500);
                simulationEngine.simulateDay();
                Platform.runLater(this::updateGrid);
            } catch (InterruptedException e) {
                break;
            }
        }
    }

    private void updateGrid() {
        root.getChildren().remove(root.getChildren().size() - 1);
        root.getChildren().add(gridBuilder.buildGrid());
    }
}
