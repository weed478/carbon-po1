package agh.ics.oop.gui;

import agh.ics.oop.*;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Arrays;
import java.util.List;

public class App extends Application {

    private final AbstractWorldMap map = new GrassField(10);
    private IEngine engine;

    private GridPane gridPane;

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

        Button stepButton = new Button("Move");
        stepButton.setOnAction(e -> simulateStep());
        root.getChildren().add(stepButton);

        gridPane = new GridPane();
        gridPane.setGridLinesVisible(true);
        drawGrid();
        root.getChildren().add(gridPane);

        Scene scene = new Scene(root, 400, 400);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void clearGrid() {
        Node node = gridPane.getChildren().get(0);
        gridPane.getChildren().clear();
        gridPane.getChildren().add(0, node);
    }

    private void simulateStep() {
        engine.simulateStep();
        drawGrid();
    }

    private void drawGrid() {
        clearGrid();

        Rect bounds = map.getDrawingBounds();

        for (int x = bounds.getBL().x; x <= bounds.getTR().x; x++) {
            for (int y = bounds.getBL().y; y <= bounds.getTR().y; y++) {
                Vector2d mapPos = new Vector2d(x, y);
                Vector2d gridPos = mapToGrid(mapPos, bounds);
                Object o = map.objectAt(mapPos);
                Label field;
                if (o != null) {
                    field = new Label(o.toString());
                }
                else {
                    field = new Label("    ");
                }
                gridPane.add(field, gridPos.x, gridPos.y);
            }
        }
    }

    private static Vector2d mapToGrid(Vector2d v, Rect bounds) {
        v = v.subtract(bounds.getBL());
        v = new Vector2d(v.x, bounds.getTR().y - bounds.getBL().y - v.y);
        return v;
    }
}
