package agh.ics.oop.sim;

public class SimulationStatistics {

    public final int day;
    public final int numAnimals;
    public final int numGrass;
    public final double averageFood;
    public final double averageLifetime;

    public SimulationStatistics(int day,
                                int numAnimals,
                                int numGrass,
                                double averageFood,
                                double averageLifetime) {
        this.day = day;
        this.numAnimals = numAnimals;
        this.numGrass = numGrass;
        this.averageFood = averageFood;
        this.averageLifetime = averageLifetime;
    }
}
