package agh.ics.oop.gui;

import agh.ics.oop.core.Rect;
import agh.ics.oop.core.Vector2d;
import agh.ics.oop.map.IAnimalAndGrassDrawableMap;
import agh.ics.oop.map.MapDirection;
import agh.ics.oop.map.ToroidalMap;
import agh.ics.oop.objects.Animal;
import agh.ics.oop.sim.ISimulationStateObserver;
import agh.ics.oop.sim.SimulationEngine;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;


public class App extends Application implements ISimulationStateObserver {

    private final SimulationEngine simulationEngine;
    private final Canvas mapCanvas;
    private final IDrawable drawableMap;
    private final Semaphore drawingDone = new Semaphore(1);

    public App() {
        IAnimalAndGrassDrawableMap map = new ToroidalMap(
                new Rect(0, 0, 100, 30),
                new Rect(45, 10, 55, 20)
        );

        List<Animal> animals = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            animals.add(new Animal(map, new Vector2d(50, 15), MapDirection.N, 100));
        }

        simulationEngine = new SimulationEngine(500, map, animals);
        simulationEngine.addSimulationStateObserver(this);

        drawableMap = new MapPainter(map);

        mapCanvas = new Canvas(1000, 300);
    }

    @Override
    public void start(Stage primaryStage) {
        VBox root = new VBox();

        root.getChildren().add(mapCanvas);

        Scene scene = new Scene(root, 1000, 300);
        primaryStage.setScene(scene);
        primaryStage.show();

        Thread simulationThread = new Thread(simulationEngine);
        primaryStage.setOnCloseRequest(e -> simulationThread.interrupt());
        simulationThread.start();
    }

    private void drawMap() {
        GraphicsContext gc = mapCanvas.getGraphicsContext2D();
        gc.save();
        gc.scale(mapCanvas.getWidth(), mapCanvas.getHeight());
        gc.setFill(Color.rgb(0, 0, 0));
        gc.fillRect(0, 0, 1, 1);
        drawableMap.draw(gc);
        gc.restore();

        drawingDone.release();
    }

    @Override
    public void simulationStateChanged() {
        if (drawingDone.tryAcquire()) {
            Platform.runLater(this::drawMap);
        }
    }

    @Override
    public void simulationEnded() {
        System.out.println("Simulation ended");
    }
}
