package agh.ics.oop.gui.scene;

import agh.ics.oop.core.SimulationConfig;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ConfigurationScene {

    private final Stage stage;
    private final TextField mapWidthTF = new TextField("30");
    private final TextField mapHeightTF = new TextField("30");
    private final TextField jungleRatioTF = new TextField("30");
    private final TextField initialAnimalsTF = new TextField("30");
    private final TextField startEnergyTF = new TextField("50");
    private final TextField moveEnergyTF = new TextField("1");
    private final TextField plantEnergyTF = new TextField("10");
    private final CheckBox isMagicCB = new CheckBox();

    public ConfigurationScene(Stage stage) {
        this.stage = stage;

        Button startBtn = new Button("Start");
        startBtn.setOnAction(this::onPressStart);

        VBox root = new VBox(
                10,
                labeledConfig(mapWidthTF, "Map width"),
                labeledConfig(mapHeightTF, "Map height"),
                labeledConfig(jungleRatioTF, "Jungle ratio [%]"),
                labeledConfig(initialAnimalsTF, "Initial animals"),
                labeledConfig(startEnergyTF, "Start energy"),
                labeledConfig(moveEnergyTF, "Move energy"),
                labeledConfig(plantEnergyTF, "Plant energy"),
                labeledConfig(isMagicCB, "Is magic"),
                startBtn
        );
        root.setAlignment(Pos.CENTER);

        Scene scene = new Scene(root, 720, 480);
        stage.setScene(scene);
        stage.show();
    }

    private void onPressStart(ActionEvent e) {
        try {
            SimulationConfig config = SimulationConfig.parse(
                    mapWidthTF.getText(),
                    mapHeightTF.getText(),
                    jungleRatioTF.getText(),
                    initialAnimalsTF.getText(),
                    startEnergyTF.getText(),
                    moveEnergyTF.getText(),
                    plantEnergyTF.getText(),
                    isMagicCB.isSelected()
            );
            new SimulationScene(stage, config);
        }
        catch (Exception ex) {
            alertException(ex);
        }
    }

    private void alertException(Exception e) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error starting simulation");
        alert.setHeaderText("Invalid configuration");
        alert.setContentText(e.getMessage());
        alert.show();
    }

    private static Node labeledConfig(Node tf, String title) {
        Label label = new Label(title);
        HBox box = new HBox(10, label, tf);
        box.setAlignment(Pos.CENTER);
        return box;
    }
}
