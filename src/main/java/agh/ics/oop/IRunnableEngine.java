package agh.ics.oop;

public interface IRunnableEngine extends IEngine, Runnable {

    void addObserver(ISimulationObserver o);

    void removeObserver(ISimulationObserver o);
}
