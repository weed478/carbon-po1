package agh.ics.oop.gui;

import agh.ics.oop.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Main application class
 */
public class App extends Application implements ISimulationObserver {

    private final List<HungryBot> animals = new ArrayList<>();
    private final Gridalator gridalator;
    private IRunnableEngine engine;
    private Thread simulationThread;

    Stage primaryStage;
    private GridPane gridPane;
    private Pane gridContainer;
    TextField directionsTF;

    /**
     * Initialize world with GrassField and 2 animals
     */
    public App() {
        IDrawableMap map = new GrassField(10);
        gridalator = new Gridalator(map);
        animals.add(new HungryBot(map, new Vector2d(2, 2)));
        animals.add(new HungryBot(map, new Vector2d(3, 4)));
        map.place(animals.get(0));
        map.place(animals.get(1));
    }

    /**
     * Create new simulation thread running simulation
     * specified by args.
     * @param args Program arguments
     */
    private void runWithArgs(List<String> args) {
        stopSim();

        // create an engine based on supplied launch intent
        AppLaunchIntent intent = AppLaunchIntent.parse(args);
        if (intent instanceof SkynetLaunchIntent) {
            engine = new SkynetEngine(animals, 500);
        }
        else if (intent instanceof ManualLaunchIntent) {
            ManualLaunchIntent manualIntent = (ManualLaunchIntent) intent;
            engine = new SimulationEngine(
                    manualIntent.directions,
                    animals.stream()
                            .map(a -> (Animal) a)
                            .collect(Collectors.toList()),
                    500);
        }
        else {
            throw new IllegalArgumentException("Unknown intent: " + intent.getClass());
        }

        engine.addObserver(this);

        simulationThread = new Thread(engine);
        // stop simulation thread when user closes app
        primaryStage.setOnCloseRequest(e -> simulationThread.interrupt());
        simulationThread.start();
    }

    private void stopSim() {
        if (engine != null) {
            engine.removeObserver(this);
        }

        if (simulationThread != null) {
            simulationThread.interrupt();
            try {
                simulationThread.join();
            } catch (InterruptedException e) {
                System.err.println("Interrupted");
            }
        }
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;

        gridContainer = new VBox();
        drawGrid();

        directionsTF = new TextField();
        Button goButton = new Button("GO!");
        goButton.setOnAction(this::onGoButtonPress);
        Button autoButton = new Button("Auto");
        autoButton.setOnAction(this::onAutoButtonPress);
        Button stopButton = new Button("Stop");
        stopButton.setOnAction(this::onStopButtonPress);
        HBox controls = new HBox(directionsTF, goButton, autoButton, stopButton);
        VBox root = new VBox(controls, gridContainer);

        Scene scene = new Scene(root, 400, 400);

        primaryStage.setScene(scene);
        primaryStage.show();

        if (getParameters().getRaw().size() > 0) {
            runWithArgs(getParameters().getRaw());
        }
    }

    private void onStopButtonPress(ActionEvent e) {
        stopSim();
    }

    private void onAutoButtonPress(ActionEvent e) {
        runWithArgs(Collections.singletonList("skynet"));
    }

    private void onGoButtonPress(ActionEvent e) {
        String directionsText = directionsTF.getText();
        runWithArgs(Arrays.asList(directionsText.split(" ")));
    }

    /**
     * Rebuild GridPane based on current map state.
     */
    private synchronized void drawGrid() {
        if (gridPane != null) {
            // remove old grid from scene
            gridContainer.getChildren().remove(gridPane);
        }
        // create new grid
        gridPane = gridalator.makeGrid();
        gridContainer.getChildren().add(gridPane);
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
