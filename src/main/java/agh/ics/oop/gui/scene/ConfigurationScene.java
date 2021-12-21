package agh.ics.oop.gui.scene;

import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ConfigurationScene implements IScene {

    private final Stage stage;

    public ConfigurationScene(Stage stage) {
        this.stage = stage;
    }


    @Override
    public void show() {
        Button startBtn = new Button("Start");
        startBtn.setOnAction(this::onPressStart);

        VBox root = new VBox();
        root.getChildren().add(startBtn);

        Scene scene = new Scene(root, 720, 480);
        stage.setScene(scene);
        stage.show();
    }

    private void onPressStart(ActionEvent e) {
        new SimulationScene(stage).show();
    }
}
