package agh.ics.oop.gui.controller;

import agh.ics.oop.core.IAnimalObserver;
import agh.ics.oop.core.Rect;
import agh.ics.oop.core.SimulationConfig;
import agh.ics.oop.core.Vector2d;
import agh.ics.oop.gui.IDrawable;
import agh.ics.oop.gui.MapPainter;
import agh.ics.oop.gui.StatsSaver;
import agh.ics.oop.map.IWorldMap;
import agh.ics.oop.map.MapDirection;
import agh.ics.oop.map.RectangularMap;
import agh.ics.oop.map.ToroidalMap;
import agh.ics.oop.objects.Animal;
import agh.ics.oop.sim.ISimulationStateObserver;
import agh.ics.oop.sim.SimulationEngine;
import agh.ics.oop.sim.SimulationStatistics;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.*;

public class SimulationController implements ISimulationStateObserver, IAnimalObserver {

    private final SimulationEngine simulationEngine;

    private final IWorldMap map;

    private final IDrawable drawableMap;

    private final List<SimulationStatistics> allStats = new ArrayList<>();
    private int numDrawnStats = 0;

    @FXML
    public Canvas mapCanvas;

    @FXML
    public Button resumeButton;

    @FXML
    public Slider simulationSpeedSlider;

    @FXML
    public Label simulationSpeedLabel;

    @FXML
    public ChoiceBox<String> selectChartDropdown;

    @FXML
    public LineChart<Number, Number> animalsChart;
    private final XYChart.Series<Number, Number> numAnimalsSeries = new XYChart.Series<>();

    @FXML
    public LineChart<Number, Number> plantsChart;
    private final XYChart.Series<Number, Number> numPlantsSeries = new XYChart.Series<>();

    @FXML
    public LineChart<Number, Number> averageFoodChart;
    private final XYChart.Series<Number, Number> averageFoodSeries = new XYChart.Series<>();

    @FXML
    public LineChart<Number, Number> averageLifetimeChart;
    private final XYChart.Series<Number, Number> averageLifetimeSeries = new XYChart.Series<>();

    @FXML
    public LineChart<Number, Number> averageChildrenChart;
    private final XYChart.Series<Number, Number> averageChildrenSeries = new XYChart.Series<>();

    @FXML
    public Label worldAgeLabel;
    private String worldAgeLabelBuffer = "?";

    @FXML
    public Label dominantGenomeLabel;
    private String dominantGenomeLabelBuffer = "?";

    private Animal trackedAnimal = null;

    private final Set<Animal> trackedAnimalAliveDescendants = new HashSet<>();

    private int trackedAnimalNumDescendants = 0;

    private int trackedAnimalNumChildren = 0;

    @FXML
    public Label trackedAnimalGenomeLabel;
    private String trackedAnimalGenomeLabelBuffer = "?";

    @FXML
    public Label trackedAnimalEnergyLabel;
    private String trackedAnimalEnergyLabelBuffer = "?";

    @FXML
    public Label trackedAnimalAgeLabel;
    private String trackedAnimalAgeLabelBuffer = "?";

    @FXML
    public Label trackedAnimalChildrenLabel;
    private String trackedAnimalChildrenLabelBuffer = "?";

    @FXML
    public Label trackedAnimalDeathLabel;
    private String trackedAnimalDeathLabelBuffer = "?";

    @FXML
    public Label trackedAnimalDescendantsLabel;
    private String trackedAnimalDescendantsLabelBuffer = "?";

    // Buffered UI update system.
    // To update UI:
    // - lock buffers and state flags with synchronized(map)
    // - update buffers
    // - set uiNeedsUpdate = true
    // Map object is used as lock to avoid synchronizing
    // on both "this" and map (would cause deadlock).
    // Access to uiNeedsUpdate and uiUpdateDone is locked with map object.
    private boolean uiNeedsUpdate = true;
    private boolean uiUpdateDone = true;

    private void uiUpdaterTask() {
        for (;;) {
            try {
                // lock access to uiNeedsUpdate and uiUpdateDone
                synchronized (map) {
                    // wait for uiNeedsUpdate && uiUpdateDone
                    // lock is released upon wait() call
                    while (!uiNeedsUpdate || !uiUpdateDone) map.wait();
                    // lock is reacquired
                    // mark UI update as in progress
                    uiUpdateDone = false;
                }
            } catch (InterruptedException e) {
                break;
            }
            Platform.runLater(() -> {
                // lock buffers, map and state flags
                synchronized (map) {
                    // update on UI thread while labels and buffers are locked
                    updateUI();
                    // map and buffers were locked so now UI is up-to-date
                    uiNeedsUpdate = false;
                    uiUpdateDone = true;
                    map.notifyAll();
                }
            });
        }
        System.out.println("UI updater stopped!");
    }

    /**
     * Assumes all locks are acquired.
     */
    private void updateUI() {
        updateLabels();
        drawMap();
        updateCharts();
    }

    private void updateLabels() {
        worldAgeLabel.setText(worldAgeLabelBuffer);
        dominantGenomeLabel.setText(dominantGenomeLabelBuffer);
        trackedAnimalGenomeLabel.setText(trackedAnimalGenomeLabelBuffer);
        trackedAnimalEnergyLabel.setText(trackedAnimalEnergyLabelBuffer);
        trackedAnimalAgeLabel.setText(trackedAnimalAgeLabelBuffer);
        trackedAnimalChildrenLabel.setText(trackedAnimalChildrenLabelBuffer);
        trackedAnimalDeathLabel.setText(trackedAnimalDeathLabelBuffer);
        trackedAnimalDescendantsLabel.setText(trackedAnimalDescendantsLabelBuffer);
    }

    private void drawMap() {
        GraphicsContext gc = mapCanvas.getGraphicsContext2D();
        gc.save();
        Rect area = getCanvasArea();
        gc.translate(area.left(), area.bottom());
        gc.scale(area.width(), area.height());
        gc.setFill(Color.rgb(0, 0, 0));
        gc.fillRect(0, 0, 1, 1);
        drawableMap.draw(gc);
        gc.restore();
    }

    /**
     * Adds all new stats to charts (based on numDrawnStats value).
     */
    private void updateCharts() {
        for (int i = numDrawnStats; i < allStats.size(); i++) {
            SimulationStatistics stats = allStats.get(i);
            numAnimalsSeries.getData().add(new XYChart.Data<>(stats.day, stats.numAnimals));
            numPlantsSeries.getData().add(new XYChart.Data<>(stats.day, stats.numGrass));
            averageFoodSeries.getData().add(new XYChart.Data<>(stats.day, stats.averageFood));
            averageLifetimeSeries.getData().add(new XYChart.Data<>(stats.day, stats.averageLifetime));
            averageChildrenSeries.getData().add(new XYChart.Data<>(stats.day, stats.averageChildren));
        }
        numDrawnStats = allStats.size();
    }

    private void markUIStale() {
        synchronized (map) {
            // state flags, buffers and map are locked
            // mark UI as stale
            uiNeedsUpdate = true;
            map.notifyAll();
        }
    }

    public SimulationController(SimulationConfig config) {
        if (config.toroidalMap) {
            map = new ToroidalMap(
                    config.mapArea,
                    config.jungleArea
            );
        }
        else {
            map = new RectangularMap(
                    config.mapArea,
                    config.jungleArea
            );
        }

        List<Animal> animals = new ArrayList<>();
        for (int i = 0; i < config.initialAnimals; i++) {
            List<Vector2d> availablePos = new ArrayList<>();
            for (int x = config.mapArea.left(); x < config.mapArea.right(); x++) {
                for (int y = config.mapArea.bottom(); y < config.mapArea.top(); y++) {
                    Vector2d pos = new Vector2d(x, y);
                    if (map.getAnimalsAt(pos).isEmpty()) {
                        availablePos.add(pos);
                    }
                }
            }
            if (availablePos.size() < 1) {
                throw new IllegalArgumentException("Animals cannot fit on map");
            }
            Vector2d pos = availablePos.get(new Random().nextInt(availablePos.size()));
            animals.add(new Animal(config, map, pos, MapDirection.N));
        }

        simulationEngine = new SimulationEngine(500, map, animals, config);
        simulationEngine.addSimulationStateObserver(this);
        Thread simulationThread = new Thread(simulationEngine);
        simulationThread.setDaemon(true);

        drawableMap = new MapPainter(map);

        simulationThread.start();

        Thread uiUpdaterThread = new Thread(this::uiUpdaterTask);
        uiUpdaterThread.setDaemon(true);
        uiUpdaterThread.start();
    }

    @FXML
    public void initialize() {
        simulationSpeedLabel.textProperty().bind(Bindings.createStringBinding(() -> {
            int val = Math.round((float) simulationSpeedSlider.getValue());
            return val + "%";
        }, simulationSpeedSlider.valueProperty()));

        simulationSpeedSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            double val = newValue.doubleValue();
            double delay = 1000 - val / 100 * 990;
            simulationEngine.setSimulationDelay((int) delay);
        });

        animalsChart.getData().add(numAnimalsSeries);
        animalsChart.visibleProperty().bind(selectChartDropdown.valueProperty().isEqualTo("Animals"));

        plantsChart.getData().add(numPlantsSeries);
        plantsChart.visibleProperty().bind(selectChartDropdown.valueProperty().isEqualTo("Plants"));

        averageFoodChart.getData().add(averageFoodSeries);
        averageFoodChart.visibleProperty().bind(selectChartDropdown.valueProperty().isEqualTo("Average energy"));

        averageLifetimeChart.getData().add(averageLifetimeSeries);
        averageLifetimeChart.visibleProperty().bind(selectChartDropdown.valueProperty().isEqualTo("Average lifetime"));

        averageChildrenChart.getData().add(averageChildrenSeries);
        averageChildrenChart.visibleProperty().bind(selectChartDropdown.valueProperty().isEqualTo("Average children"));

        selectChartDropdown.getItems().addAll(
                "Animals",
                "Plants",
                "Average energy",
                "Average lifetime",
                "Average children");
        selectChartDropdown.setValue("Animals");

        synchronized (map) {
            worldAgeLabelBuffer = String.valueOf(simulationEngine.getDay());
            markUIStale();
        }
    }

    @FXML
    public void onCanvasClicked(MouseEvent e) {
        synchronized (map) {
            deselectAnimals();

            Vector2d pos = canvasToPos(e.getX(), e.getY());
            List<Animal> animals = map.getAnimalsAt(pos);

            if (!animals.isEmpty()) {
                trackedAnimal = animals.get(0);
                trackedAnimal.select();
                trackedAnimalGenomeLabelBuffer = genomeToString(trackedAnimal.getGenome());
                trackedAnimalEnergyLabelBuffer = String.valueOf(trackedAnimal.getFood());
                trackedAnimalAgeLabelBuffer = String.valueOf(trackedAnimal.getAge());
                trackedAnimalChildrenLabelBuffer = "0";
                trackedAnimalDescendantsLabelBuffer = "0";
                markUIStale();
                trackedAnimal.addAnimalObserver(this);
            }
        }
    }

    private void deselectAnimals() {
        synchronized (map) {
            if (trackedAnimal != null) {
                trackedAnimal.removeAnimalObserver(this);
                trackedAnimal.deselect();
                trackedAnimal = null;
            }

            if (!trackedAnimalAliveDescendants.isEmpty()) {
                for (Animal a : trackedAnimalAliveDescendants) {
                    a.removeAnimalObserver(this);
                    a.deselect();
                }
                trackedAnimalAliveDescendants.clear();
            }

            for (Animal a : simulationEngine.getAnimals()) {
                // clear dominant genome highlight
                a.deselect();
            }

            trackedAnimalNumDescendants = 0;
            trackedAnimalNumChildren = 0;

            trackedAnimalGenomeLabelBuffer = "?";
            trackedAnimalEnergyLabelBuffer = "?";
            trackedAnimalAgeLabelBuffer = "?";
            trackedAnimalChildrenLabelBuffer = "?";
            trackedAnimalDeathLabelBuffer = "?";
            trackedAnimalDescendantsLabelBuffer = "?";
            markUIStale();
        }
    }

    private static String genomeToString(int[] genome) {
        String str = "";
        for (int gene : genome) {
            str += gene;
        }
        return str;
    }

    private Vector2d canvasToPos(double x, double y) {
        Rect area = getCanvasArea();
        x -= area.left();
        y -= area.bottom();
        x /= (double) area.width() / map.getMapArea().width();
        y /= (double) area.height() / map.getMapArea().height();
        return new Vector2d((int) x, map.getMapArea().height() - 1 - (int) y);
    }

    private Rect getCanvasArea() {
        double cW = mapCanvas.getWidth();
        double cH = mapCanvas.getHeight();
        double mW = map.getMapArea().width();
        double mH = map.getMapArea().height();
        double cA = cW / cH;
        double mA = mW / mH;
        if (mA > cA) {
            double w = cW;
            double h = cW / mA;
            return new Rect(0, 0, (int) w, (int) h);
        }
        else {
            double h = cH;
            double w = cH * mA;
            Vector2d tl = new Vector2d(0, 0);
            Vector2d br = new Vector2d((int) w, (int) h);
            Vector2d shift = new Vector2d((int) ((cW - w) / 2), 0);
            return new Rect(tl.add(shift), br.add(shift));
        }
    }

    @Override
    public void simulationStateChanged() {
        synchronized (map) {
            SimulationStatistics stats = simulationEngine.getStatistics();
            allStats.add(stats);
            worldAgeLabelBuffer = String.valueOf(stats.day);
            dominantGenomeLabelBuffer = genomeToString(stats.dominantGenome);
            markUIStale();
        }
    }

    @Override
    public void magicHappened() {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Magic happened");
            alert.setHeaderText("Some animals appeared");
            alert.show();
        });
    }

    @Override
    public void simulationEnded() {
        System.out.println("Simulation ended");
    }

    @FXML
    public void onResumeButtonClick(ActionEvent e) {
        if (simulationEngine.isRunning()) {
            simulationEngine.pause();
            resumeButton.setText("Resume");
        }
        else {
            simulationEngine.resume();
            resumeButton.setText("Pause");
        }
    }

    @FXML
    public void onDominantGenomeClick(ActionEvent e) {
        synchronized (map) {
            deselectAnimals();
            int[] dominantGenome = simulationEngine.getStatistics().dominantGenome;
            for (Animal a : simulationEngine.getAnimals()) {
                if (Arrays.equals(a.getGenome(), dominantGenome)) {
                    a.select();
                }
            }
            // animal selections changed
            markUIStale();
        }
    }

    @FXML
    public void onDeselectClick(ActionEvent e) {
        deselectAnimals();
    }

    @FXML
    public void onSaveClick(ActionEvent e) {
        Stage stage = (Stage) mapCanvas.getScene().getWindow();
        StatsSaver saver = new StatsSaver(stage);
        synchronized (map) {
            saver.saveStats(allStats);
        }
    }

    @Override
    public void onAnimalEnergyChanged(Animal animal) {
        synchronized (map) {
            if (animal == trackedAnimal) {
                trackedAnimalEnergyLabelBuffer = String.valueOf(animal.getFood());
                markUIStale();
            }
        }
    }

    @Override
    public void onAnimalAgeChanged(Animal animal) {
        synchronized (map) {
            if (animal == trackedAnimal) {
                trackedAnimalAgeLabelBuffer = String.valueOf(animal.getAge());
                markUIStale();
            }
        }
    }

    @Override
    public void onAnimalHadChild(Animal parent, Animal child) {
        synchronized (map) {
            if (trackedAnimalAliveDescendants.add(child)) {
                child.addAnimalObserver(this);
                child.select();
                trackedAnimalNumDescendants++;
                trackedAnimalDescendantsLabelBuffer = String.valueOf(trackedAnimalNumDescendants);
                markUIStale();
            }

            if (parent == trackedAnimal) {
                trackedAnimalNumChildren++;
                trackedAnimalChildrenLabelBuffer = String.valueOf(trackedAnimalNumChildren);
                // lock was constantly acquired
                // so UI updater thread could not start
                // previous UI update meaning UI gets
                // updated only once
                markUIStale();
            }
        }
    }

    @Override
    public void onAnimalDied(Animal animal) {
        synchronized (map) {
            if (animal == trackedAnimal) {
                trackedAnimalEnergyLabelBuffer = "0";
                trackedAnimalDeathLabelBuffer = String.valueOf(simulationEngine.getDay());
                trackedAnimal = null;
                markUIStale();
            }
            else if (!trackedAnimalAliveDescendants.remove(animal)) {
                throw new IllegalStateException("Dead animal was not tracked");
            }
        }
    }
}
