package kcl.teamIndexZero.traffic.simulator;

/**
 * MapFactory for simulator object.
 */
public interface SimulatorFactory {
    /**
     * Create a new, or get an existing one, simulator.
     *
     * @return simulator instance.
     */
    Simulator createSimulator();
}
