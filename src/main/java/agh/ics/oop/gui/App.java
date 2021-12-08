package agh.ics.oop.gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;


public class App extends Application {

    @Override
    public void init() {

    }

    @Override
    public void start(Stage primaryStage) {
        Scene scene = new Scene(new Label("Test"), 400, 400);

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
