package agh.ics.oop.gui;

import agh.ics.oop.sim.SimulationStatistics;
import javafx.scene.control.Alert;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class StatsSaver {

    private final Stage stage;

    public StatsSaver(Stage stage) {
        this.stage = stage;
    }

    public void saveStats(List<SimulationStatistics> stats) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save statistics");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV (*.csv)", "*.csv"));
        File file = fileChooser.showSaveDialog(stage);
        if (file != null) {
            try {
                saveFile(file, stats);
            }
            catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Error saving statistics");
                alert.setContentText(e + "\n" + e.getMessage());
                alert.show();
            }
        }
    }

    private String genomeToString(int[] genome) {
        String str = "";
        for (int gene : genome) {
            str += gene;
        }
        return str;
    }

    private String roundDigits(double x) {
        String first = String.valueOf((int) x);
        String second = String.valueOf((int) ((x - Math.floor(x)) * 100));
        return first + "." + second;
    }

    private void saveFile(File f, List<SimulationStatistics> stats) throws IOException {
        FileWriter writer = new FileWriter(f);
        writer.write("day,animals,plants,average energy,average lifetime,average children\n");
        for (SimulationStatistics s : stats) {
            writer.write(String.valueOf(s.day));
            writer.write(",");

            writer.write(String.valueOf(s.numAnimals));
            writer.write(",");

            writer.write(String.valueOf(s.numGrass));
            writer.write(",");

            writer.write(roundDigits(s.averageFood));
            writer.write(",");

            writer.write(roundDigits(s.averageLifetime));
            writer.write(",");

            writer.write(roundDigits(s.averageChildren));
            writer.write("\n");
        }



        writer.close();
    }
}
