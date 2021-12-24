package agh.ics.oop.sim;

public class SimulationStatistics {

    public final int day;
    public final int numAnimals;
    public final int numGrass;
    public final double averageFood;
    public final double averageLifetime;
    public final double averageChildren;
    public final int[] dominantGenome;

    public SimulationStatistics(int day,
                                int numAnimals,
                                int numGrass,
                                double averageFood,
                                double averageLifetime,
                                double averageChildren,
                                int[] dominantGenome) {
        this.day = day;
        this.numAnimals = numAnimals;
        this.numGrass = numGrass;
        this.averageFood = averageFood;
        this.averageLifetime = averageLifetime;
        this.averageChildren = averageChildren;
        this.dominantGenome = dominantGenome;
    }
}
