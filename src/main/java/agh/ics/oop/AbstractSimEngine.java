package agh.ics.oop;

import java.util.HashSet;
import java.util.Set;

/**
 * Sleeping loop implementation of IRunnableEngine.
 * Runs IEngine::simulateStep() every moveDelay ms.
 */
public abstract class AbstractSimEngine implements IRunnableEngine {

    private final int moveDelay;
    private final Set<ISimulationObserver> observers = new HashSet<>();

    public AbstractSimEngine(int moveDelay) {
        this.moveDelay = moveDelay;
    }

    @Override
    public void addObserver(ISimulationObserver o) {
        observers.add(o);
    }

    @Override
    public void removeObserver(ISimulationObserver o) {
        observers.remove(o);
    }

    @Override
    public void run() {
        for (;;) {
            try {
                simulateStep();
                simulationStateChanged();
                Thread.sleep(moveDelay);
            }
            catch (InterruptedException e) {
                break;
            }
        }
        simulationEnded();
    }

    /**
     * Notify observers that state changed.
     */
    private void simulationStateChanged() {
        for (ISimulationObserver o : observers) {
            o.simulationStateChanged();
        }
    }

    /**
     * Notify observers that simulation ended.
     */
    private void simulationEnded() {
        for (ISimulationObserver o : observers) {
            o.simulationEnded();
        }
    }
}
