package agh.ics.oop.gui;

import agh.ics.oop.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Arrays;
import java.util.List;

public class App extends Application {

    private final AbstractWorldMap map = new GrassField(10);
    private IEngine engine;
    private final Gridalator gridalator = new Gridalator(map);

    private GridPane gridPane;
    private Pane rootPane;

    @Override
    public void init() {
        List<Vector2d> initialPositions = Arrays.asList(
                new Vector2d(2,2),
                new Vector2d(3,4)
        );

        AppLaunchIntent intent = AppLaunchIntent.parse(getParameters().getRaw());
        if (intent instanceof SkynetLaunchIntent) {
            engine = new SkynetEngine(map, initialPositions);
        }
        else if (intent instanceof ManualLaunchIntent) {
            ManualLaunchIntent manualIntent = (ManualLaunchIntent) intent;
            engine = new SimulationEngine(manualIntent.directions, map, initialPositions);
        }
        else {
            throw new IllegalArgumentException("Unknown intent: " + intent.getClass());
        }
    }

    @Override
    public void start(Stage primaryStage) {
        VBox root = new VBox();
        rootPane = root;

        Button stepButton = new Button("Move");
        stepButton.setOnAction(e -> simulateStep());
        root.getChildren().add(stepButton);

        drawGrid();

        Scene scene = new Scene(root, 400, 400);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void simulateStep() {
        engine.simulateStep();
        drawGrid();
    }

    private void drawGrid() {
        if (gridPane != null) {
            rootPane.getChildren().remove(gridPane);
        }
        gridPane = gridalator.makeGrid();
        rootPane.getChildren().add(gridPane);
    }
}
