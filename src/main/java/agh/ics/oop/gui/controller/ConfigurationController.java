package agh.ics.oop.gui.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

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
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("Yes");
    }
}
