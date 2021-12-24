package agh.ics.oop.sim;

public class SimulationStatistics {

    public final int numAnimals;
    public final int numGrass;
    public final double averageFood;

    public SimulationStatistics(int numAnimals, int numGrass, double averageFood) {
        this.numAnimals = numAnimals;
        this.numGrass = numGrass;
        this.averageFood = averageFood;
    }
}
