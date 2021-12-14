package agh.ics.oop.gui;

import agh.ics.oop.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Arrays;
import java.util.List;

/**
 * Main application class
 */
public class App extends Application implements ISimulationObserver {

    private final IDrawableMap map = new GrassField(10);
    private IRunnableEngine engine;
    private final Gridalator gridalator = new Gridalator(map);

    private GridPane gridPane;
    private Pane rootPane;

    @Override
    public void init() {
        List<Vector2d> initialPositions = Arrays.asList(
                new Vector2d(2,2),
                new Vector2d(3,4)
        );

        // create an engine based on supplied launch intent
        AppLaunchIntent intent = AppLaunchIntent.parse(getParameters().getRaw());
        if (intent instanceof SkynetLaunchIntent) {
            engine = new SkynetEngine(map, initialPositions, 500);
        }
        else if (intent instanceof ManualLaunchIntent) {
            ManualLaunchIntent manualIntent = (ManualLaunchIntent) intent;
            engine = new SimulationEngine(manualIntent.directions, map, initialPositions, 500);
        }
        else {
            throw new IllegalArgumentException("Unknown intent: " + intent.getClass());
        }

        engine.addObserver(this);
    }

    @Override
    public void start(Stage primaryStage) {
        VBox root = new VBox();
        rootPane = root;

        drawGrid();

        Scene scene = new Scene(root, 400, 400);

        primaryStage.setScene(scene);
        primaryStage.show();

        Thread simulationThread = new Thread(engine);
        // stop simulation thread when user closes app
        primaryStage.setOnCloseRequest(e -> simulationThread.interrupt());
        simulationThread.start();
    }

    /**
     * Rebuild GridPane based on current map state.
     */
    private synchronized void drawGrid() {
        if (gridPane != null) {
            // remove old grid from scene
            rootPane.getChildren().remove(gridPane);
        }
        // create new grid
        gridPane = gridalator.makeGrid();
        rootPane.getChildren().add(gridPane);
    }

    @Override
    public void simulationStateChanged() {
        Platform.runLater(this::drawGrid);
    }

    @Override
    public void simulationEnded() {
        System.out.println("Simulation ended");
    }
}
