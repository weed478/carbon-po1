package agh.ics.oop.gui.scene;

import agh.ics.oop.core.SimulationConfig;
import agh.ics.oop.core.Vector2d;
import agh.ics.oop.gui.IDrawable;
import agh.ics.oop.gui.IDrawableMap;
import agh.ics.oop.gui.MapPainter;
import agh.ics.oop.map.IAnimalAndGrassDrawableMap;
import agh.ics.oop.map.MapDirection;
import agh.ics.oop.map.ToroidalMap;
import agh.ics.oop.objects.Animal;
import agh.ics.oop.sim.ISimulationStateObserver;
import agh.ics.oop.sim.SimulationEngine;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

public class SimulationScene implements IScene, ISimulationStateObserver {

    private final SimulationEngine simulationEngine;
    private final Canvas mapCanvas;
    private final IDrawable drawableMap;
    private final IDrawableMap map;
    private final Semaphore drawingDone = new Semaphore(1);

    public SimulationScene(SimulationConfig config) {
        IAnimalAndGrassDrawableMap map = new ToroidalMap(
                config.mapArea,
                config.jungleArea
        );

        this.map = map;

        List<Animal> animals = new ArrayList<>();
        for (int i = 0; i < config.initialAnimals; i++) {
            animals.add(
                    new Animal(
                            config,
                            map,
                            new Vector2d(config.mapArea.width() / 2, config.mapArea.height() / 2),
                            MapDirection.N
                    )
            );
        }

        simulationEngine = new SimulationEngine(500, map, animals);
        simulationEngine.addSimulationStateObserver(this);

        drawableMap = new MapPainter(map);

        mapCanvas = new Canvas();
    }

    @Override
    public void showOnStage(Stage stage) {
        VBox root = new VBox();

        root.getChildren().add(mapCanvas);

        Scene scene = new Scene(root, 720, 480);

        mapCanvas.widthProperty().bind(
                Bindings.createDoubleBinding(() -> {
                    double mapAspect = (double) map.getDrawingBounds().width() / map.getDrawingBounds().height();
                    double sceneAspect = scene.getWidth() / scene.getHeight();
                    if (mapAspect > sceneAspect) {
                        return scene.getWidth();
                    }
                    else {
                        return scene.getHeight() * mapAspect;
                    }
                }, scene.widthProperty(), scene.heightProperty())
        );

        mapCanvas.heightProperty().bind(
                Bindings.createDoubleBinding(() -> {
                    double mapAspect = (double) map.getDrawingBounds().width() / map.getDrawingBounds().height();
                    double sceneAspect = scene.getWidth() / scene.getHeight();
                    if (mapAspect > sceneAspect) {
                        return scene.getWidth() / mapAspect;
                    }
                    else {
                        return scene.getHeight();
                    }
                }, scene.widthProperty(), scene.heightProperty())
        );

        mapCanvas.widthProperty().addListener((observable, oldValue, newValue) -> scheduleDrawMap());
        mapCanvas.heightProperty().addListener((observable, oldValue, newValue) -> scheduleDrawMap());

        Thread simulationThread = new Thread(simulationEngine);
        stage.setOnCloseRequest(e -> simulationThread.interrupt());
        simulationThread.start();

        stage.setScene(scene);
        stage.show();
    }

    private void scheduleDrawMap() {
        if (drawingDone.tryAcquire()) {
            Platform.runLater(() -> {
                try {
                    GraphicsContext gc = mapCanvas.getGraphicsContext2D();
                    gc.save();
                    gc.scale(mapCanvas.getWidth(), mapCanvas.getHeight());
                    gc.setFill(Color.rgb(0, 0, 0));
                    gc.fillRect(0, 0, 1, 1);
                    drawableMap.draw(gc);
                    gc.restore();
                }
                finally {
                    drawingDone.release();
                }
            });
        }
    }

    @Override
    public void simulationStateChanged() {
        scheduleDrawMap();
    }

    @Override
    public void simulationEnded() {
        System.out.println("Simulation ended");
    }
}
