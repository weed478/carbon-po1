package agh.ics.oop.gui.controller;

import agh.ics.oop.core.SimulationConfig;
import agh.ics.oop.gui.scene.SimulationScene;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ConfigurationController {

    @FXML
    public TextField mapWidthTF;
    @FXML
    public TextField mapHeightTF;
    @FXML
    public TextField initialAnimalsTF;
    @FXML
    public TextField jungleRatioTF;
    @FXML
    public TextField initialEnergyTF;
    @FXML
    public TextField moveEnergyTF;
    @FXML
    public TextField plantEnergyTF;
    @FXML
    public CheckBox magicalLeftCB;
    @FXML
    public CheckBox magicalRightCB;

    @FXML
    public void onPressStart(ActionEvent e) {
        try {
            SimulationConfig configLeft = SimulationConfig.parse(
                    mapWidthTF.getText(),
                    mapHeightTF.getText(),
                    jungleRatioTF.getText(),
                    initialAnimalsTF.getText(),
                    initialEnergyTF.getText(),
                    moveEnergyTF.getText(),
                    plantEnergyTF.getText(),
                    magicalLeftCB.isSelected(),
                    true
            );
            SimulationConfig configRight = SimulationConfig.parse(
                    mapWidthTF.getText(),
                    mapHeightTF.getText(),
                    jungleRatioTF.getText(),
                    initialAnimalsTF.getText(),
                    initialEnergyTF.getText(),
                    moveEnergyTF.getText(),
                    plantEnergyTF.getText(),
                    magicalRightCB.isSelected(),
                    false
            );
            new SimulationScene((Stage) mapWidthTF.getScene().getWindow(), configLeft, configRight);
        }
        catch (Exception ex) {
            alertException(ex);
        }
    }

    private void alertException(Exception e) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error starting simulation");
        alert.setHeaderText(e.toString());
        alert.setContentText(e.getMessage());
        alert.show();
    }
}
