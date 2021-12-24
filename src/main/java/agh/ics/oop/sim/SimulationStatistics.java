package agh.ics.oop.sim;

public class SimulationStatistics {

    public final int day;
    public final int numAnimals;
    public final int numGrass;
    public final double averageFood;
    public final double averageLifetime;
    public final double averageChildren;

    public SimulationStatistics(int day,
                                int numAnimals,
                                int numGrass,
                                double averageFood,
                                double averageLifetime,
                                double averageChildren) {
        this.day = day;
        this.numAnimals = numAnimals;
        this.numGrass = numGrass;
        this.averageFood = averageFood;
        this.averageLifetime = averageLifetime;
        this.averageChildren = averageChildren;
    }
}
