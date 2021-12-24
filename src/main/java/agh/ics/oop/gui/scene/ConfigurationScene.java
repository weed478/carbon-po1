package agh.ics.oop.gui.scene;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ConfigurationScene {

    public ConfigurationScene(Stage stage) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("configuration.fxml"));
        Scene scene;
        try {
            scene = new Scene(loader.load(), 600, 400);
        } catch (Exception e) {
            throw new RuntimeException("Error loading configuration scene");
        }

        stage.setScene(scene);
        stage.show();
    }
}
