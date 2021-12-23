package agh.ics.oop.gui.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class SimulationController {

    @FXML
    public Label simulationSpeedLabel;

    @FXML
    public void onResumeButtonClick(ActionEvent e) {
        simulationSpeedLabel.setText("XD");
    }

    @FXML
    public void onPauseButtonClick(ActionEvent e) {
        simulationSpeedLabel.setText("DX");
    }
}
