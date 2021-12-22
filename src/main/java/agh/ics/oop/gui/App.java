package agh.ics.oop.gui;

import agh.ics.oop.gui.scene.ConfigurationScene;
import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) {
        new ConfigurationScene().showOnStage(primaryStage);
    }
}
