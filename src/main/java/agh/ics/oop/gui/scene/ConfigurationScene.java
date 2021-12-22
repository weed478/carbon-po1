package agh.ics.oop.gui.scene;

import agh.ics.oop.core.SimulationConfig;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ConfigurationScene implements IScene {

    private final Stage stage;
    private final TextField mapWidthTF = new TextField("30");
    private final TextField mapHeightTF = new TextField("30");
    private final TextField jungleRatioTF = new TextField("30");
    private final TextField initialAnimalsTF = new TextField("30");
    private final TextField startEnergyTF = new TextField("50");
    private final TextField moveEnergyTF = new TextField("1");
    private final TextField plantEnergyTF = new TextField("10");
    private final CheckBox isMagicCB = new CheckBox("Use magic");

    public ConfigurationScene(Stage stage) {
        this.stage = stage;
    }

    @Override
    public void show() {
        Button startBtn = new Button("Start");
        startBtn.setOnAction(this::onPressStart);

        VBox root = new VBox();
        root.getChildren().addAll(
                new HBox(
                        new Label("Map width"),
                        mapWidthTF
                ),
                new HBox(
                        new Label("Map height"),
                        mapHeightTF
                ),
                new HBox(
                        new Label("Jungle ratio [%]"),
                        jungleRatioTF
                ),
                new HBox(
                        new Label("Initial animals"),
                        initialAnimalsTF
                ),
                new HBox(
                        new Label("Start energy"),
                        startEnergyTF
                ),
                new HBox(
                        new Label("Move energy"),
                        moveEnergyTF
                ),
                new HBox(
                        new Label("Plant energy"),
                        plantEnergyTF
                ),
                isMagicCB,
                startBtn
        );

        Scene scene = new Scene(root, 720, 480);
        stage.setScene(scene);
        stage.show();
    }

    private void onPressStart(ActionEvent e) {
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
        new SimulationScene(
                stage,
                config
        ).show();
    }
}
