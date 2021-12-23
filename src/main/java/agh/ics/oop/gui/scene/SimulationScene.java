package agh.ics.oop.gui.scene;

import agh.ics.oop.core.SimulationConfig;
import agh.ics.oop.gui.view.SimulationView;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class SimulationScene implements IScene {

    private final Pane simLeft;
    private final Pane simRight;

    public SimulationScene(SimulationConfig config) {
        simLeft = new SimulationView(config).buildView();
        simRight = new SimulationView(config).buildView();
    }

    @Override
    public void showOnStage(Stage stage) {
        HBox root = new HBox(10, simLeft, simRight);
        Scene scene = new Scene(root, 1280, 720);
        stage.setScene(scene);
        stage.show();
    }
}
