package kcl.teamIndexZero.traffic.gui.mvc;

import kcl.teamIndexZero.traffic.simulator.data.SimulationParams;
import kcl.teamIndexZero.traffic.simulator.data.SimulationTick;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/**
 * GUI model of our UI application. Basically it is different from simulation model (map, vehicles, etc) since it contains
 * only things relevant on the abstraction level of UI. For example: as soon as we are only drawing cars as dots on the
 * map we don't need to care about their velocity, color or driving strategy (whether they are cautious or not), we only
 * care about their position. More generally, we don't care about map either - we're good to have a new image to draw which
 * is derived from map.
 */
public class GuiModel {

    /**
     * Model update interface. Other components want to implement it in order to receive updates from model when something
     * important changes.
     */
    @FunctionalInterface
    public interface ChangeListener {
        /**
         * Callback interface, invoked by model when its internals change.
         */
        void onModelChanged();
    }

    /**
     * Status of the simulation process.
     */
    public enum SimulationStatus {
        OFF,
        PAUSED,
        INPROGRESS
    }

    private List<ChangeListener> listeners = new ArrayList<>();

    private SimulationTick tick;
    private SimulationStatus status;
    private SimulationParams params;
    private BufferedImage lastImage;

    /**
     * Default constructor.
     */
    public GuiModel() {
        reset();
    }

    /**
     * Reset method gets model to the default state (same as it was right after running constructor.
     */
    public void reset() {
        tick = null;
        status = SimulationStatus.OFF;
        lastImage = null;
        params = null;
        fireChangeEvent();
    }

    public BufferedImage getLastImage() {
        return lastImage;
    }

    public void setLastSimulationTickAndImage(BufferedImage lastImage, SimulationTick tick) {
        this.lastImage = lastImage;
        this.tick = tick;
        fireChangeEvent();
    }

    public SimulationTick getTick() {
        return tick;
    }

    public SimulationStatus getStatus() {
        return status;
    }

    public void setStatus(SimulationStatus status) {
        this.status = status;
        fireChangeEvent();
    }

    public SimulationParams getParams() {
        return params;
    }

    public void setParams(SimulationParams params) {
        this.params = params;
        fireChangeEvent();
    }

    public void addChangeListener(ChangeListener listener) {
        listeners.add(listener);
    }

    public void removeChangeListener(ChangeListener listener) {
        listeners.remove(listener);
    }

    private void fireChangeEvent() {
        listeners.forEach(ChangeListener::onModelChanged);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GuiModel guiModel = (GuiModel) o;

        if (tick != null ? !tick.equals(guiModel.tick) : guiModel.tick != null) return false;
        if (status != guiModel.status) return false;
        if (params != null ? !params.equals(guiModel.params) : guiModel.params != null) return false;
        return lastImage != null ? lastImage.equals(guiModel.lastImage) : guiModel.lastImage == null;

    }

    @Override
    public int hashCode() {
        int result = tick != null ? tick.hashCode() : 0;
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (params != null ? params.hashCode() : 0);
        result = 31 * result + (lastImage != null ? lastImage.hashCode() : 0);
        return result;
    }
}