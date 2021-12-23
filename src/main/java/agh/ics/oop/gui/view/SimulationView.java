package agh.ics.oop.gui.view;

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
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

public class SimulationView implements IView, ISimulationStateObserver {

    private final Canvas mapCanvas;
    private final IDrawable drawableMap;
    private final IDrawableMap map;
    private final Semaphore drawingDone = new Semaphore(1);

    public SimulationView(SimulationConfig config) {
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

        SimulationEngine simulationEngine = new SimulationEngine(500, map, animals);
        simulationEngine.addSimulationStateObserver(this);
        Thread simulationThread = new Thread(simulationEngine);

        drawableMap = new MapPainter(map);
        mapCanvas = new Canvas(360, 10);

        simulationThread.start();
    }

    @Override
    public Pane buildView() {
        VBox root = new VBox(mapCanvas);

        mapCanvas.heightProperty().bind(
                mapCanvas.widthProperty()
                        .multiply(map.getDrawingBounds().height() / map.getDrawingBounds().width())
        );

        scheduleDrawMap();

        return root;
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
