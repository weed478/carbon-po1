package agh.ics.oop.gui.controller;

import agh.ics.oop.core.Rect;
import agh.ics.oop.core.SimulationConfig;
import agh.ics.oop.core.Vector2d;
import agh.ics.oop.gui.IDrawable;
import agh.ics.oop.gui.MapPainter;
import agh.ics.oop.map.IAnimalAndGrassDrawableMap;
import agh.ics.oop.map.MapDirection;
import agh.ics.oop.map.ToroidalMap;
import agh.ics.oop.objects.Animal;
import agh.ics.oop.sim.ISimulationStateObserver;
import agh.ics.oop.sim.SimulationEngine;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

public class SimulationController implements ISimulationStateObserver {

    private final SimulationEngine simulationEngine;

    private final IAnimalAndGrassDrawableMap map;

    private final IDrawable drawableMap;

    private final Semaphore drawingDone = new Semaphore(1);

    @FXML
    public Canvas mapCanvas;

    @FXML
    public Button resumeButton;

    @FXML
    public Slider simulationSpeedSlider;

    @FXML
    public Label simulationSpeedLabel;

    public SimulationController(SimulationConfig config) {
        map = new ToroidalMap(
                config.mapArea,
                config.jungleArea
        );

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
        Thread simulationThread = new Thread(simulationEngine);
        simulationThread.setDaemon(true);

        drawableMap = new MapPainter(map);

        simulationThread.start();
    }

    @FXML
    public void initialize() {
        simulationSpeedLabel.textProperty().bind(Bindings.createStringBinding(() -> {
            int val = Math.round((float) simulationSpeedSlider.getValue());
            return val + "%";
        }, simulationSpeedSlider.valueProperty()));

        simulationSpeedSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            double val = newValue.doubleValue();
            double delay = 1000 - val / 100 * 1000;
            simulationEngine.setSimulationDelay((int) delay);
        });
    }

    private Rect getCanvasArea() {
        double cW = mapCanvas.getWidth();
        double cH = mapCanvas.getHeight();
        double mW = map.getDrawingBounds().width();
        double mH = map.getDrawingBounds().height();
        double cA = cW / cH;
        double mA = mW / mH;
        if (mA > cA) {
            double w = cW;
            double h = cW / mA;
            return new Rect(0, 0, (int) w, (int) h);
        }
        else {
            double h = cH;
            double w = cH * mA;
            Vector2d tl = new Vector2d(0, 0);
            Vector2d br = new Vector2d((int) w, (int) h);
            Vector2d shift = new Vector2d((int) ((cW - w) / 2), 0);
            return new Rect(tl.add(shift), br.add(shift));
        }
    }

    private void scheduleDrawMap() {
        if (drawingDone.tryAcquire()) {
            Platform.runLater(() -> {
                try {
                    GraphicsContext gc = mapCanvas.getGraphicsContext2D();
                    gc.save();
                    Rect area = getCanvasArea();
                    gc.translate(area.left(), area.bottom());
                    gc.scale(area.width(), area.height());
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

    @FXML
    public void onResumeButtonClick(ActionEvent e) {
        if (simulationEngine.isRunning()) {
            simulationEngine.pause();
            resumeButton.setText("Resume");
        }
        else {
            simulationEngine.resume();
            resumeButton.setText("Pause");
        }
    }
}
