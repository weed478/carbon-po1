package agh.ics.oop.gui.scene;

import agh.ics.oop.core.SimulationConfig;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SimulationScene implements IScene {

    public SimulationScene(SimulationConfig config) {

    }

    @Override
    public void showOnStage(Stage stage) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("simulation.fxml"));
        Scene scene;
        try {
            scene = new Scene(loader.load(), 1280, 720);
        } catch (Exception e) {
            throw new RuntimeException("Could not load simulation layout FXML: " + e.getMessage());
        }
        stage.setScene(scene);
        stage.show();
    }
}
