package agh.ics.oop.gui.scene;

import agh.ics.oop.core.SimulationConfig;
import agh.ics.oop.gui.controller.SimulationController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class SimulationScene {

    public SimulationScene(Stage stage, SimulationConfig config) {
        HBox root = new HBox(20);

        FXMLLoader loaderLeft = new FXMLLoader(getClass().getResource("simulation.fxml"));
        FXMLLoader loaderRight = new FXMLLoader(getClass().getResource("simulation.fxml"));

        loaderLeft.setControllerFactory(t -> new SimulationController(config));
        loaderRight.setControllerFactory(t -> new SimulationController(config));

        try {
            root.getChildren().add(loaderLeft.load());
            root.getChildren().add(loaderRight.load());
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        Scene scene = new Scene(root, 1280, 720);

        stage.setScene(scene);
        stage.show();
    }
}
