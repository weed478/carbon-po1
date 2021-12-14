package agh.ics.oop;

/**
 * IEngine that can be observed and run on a Thread.
 */
public interface IRunnableEngine extends IEngine, Runnable {

    /**
     * Add simulation state observer.
     */
    void addObserver(ISimulationObserver o);

    /**
     * Remove simulation state observer.
     */
    void removeObserver(ISimulationObserver o);
}
