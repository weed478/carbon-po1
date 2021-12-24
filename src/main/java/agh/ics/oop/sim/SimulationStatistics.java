package agh.ics.oop.sim;

public class SimulationStatistics {

    public final int numAnimals;
    public final int numGrass;
    public final double averageFood;
    public final double averageLifetime;

    public SimulationStatistics(int numAnimals,
                                int numGrass,
                                double averageFood,
                                double averageLifetime) {
        this.numAnimals = numAnimals;
        this.numGrass = numGrass;
        this.averageFood = averageFood;
        this.averageLifetime = averageLifetime;
    }
}
