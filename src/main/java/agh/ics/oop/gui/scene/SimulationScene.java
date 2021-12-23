package agh.ics.oop.gui.scene;

import agh.ics.oop.core.SimulationConfig;
import agh.ics.oop.gui.manager.SimulationManager;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.stage.Stage;

public class SimulationScene implements IScene {

    public Button resumeButton;
    public Button pauseButton;
    public Slider simulationSpeedSlider;
    public Canvas canvasLeft;
    public Canvas canvasRight;
    public LineChart lineChartLeft;
    public LineChart lineChartRight;
    private final SimulationConfig config;

    public SimulationScene(SimulationConfig config) {
        this.config = config;
    }

    @Override
    public void showOnStage(Stage stage) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("simulation.fxml"));
        loader.setController(this);
        Scene scene;
        try {
            scene = new Scene(loader.load(), 1280, 720);
        } catch (Exception e) {
            throw new RuntimeException("Could not load simulation layout FXML: " + e.getMessage());
        }
        new SimulationManager(config, canvasLeft, true);
        new SimulationManager(config, canvasRight, false);
        stage.setScene(scene);
        stage.show();
    }
}
