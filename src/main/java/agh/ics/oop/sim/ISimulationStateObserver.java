package agh.ics.oop.sim;

public interface ISimulationStateObserver {

    void simulationStateChanged();

    void magicHappened();

    void simulationEnded();
}
