package kcl.teamIndexZero.traffic.simulator.data.features;

import kcl.teamIndexZero.traffic.log.Logger;
import kcl.teamIndexZero.traffic.log.Logger_Interface;
import kcl.teamIndexZero.traffic.simulator.data.ID;
import kcl.teamIndexZero.traffic.simulator.data.SimulationTick;
import kcl.teamIndexZero.traffic.simulator.data.links.Link;

/**
 * Lane within a set of directed lanes within a road
 */
public class Lane extends Feature {
    private static Logger_Interface LOG = Logger.getLoggerInstance(Lane.class.getSimpleName());
    private RoadSpecs roadSpecs;
    private final DirectedLanes lanes;
    private Link nextLink;

    /**
     * Constructor
     *
     * @param road_specs Road specifications
     */
    public Lane(ID id, RoadSpecs road_specs, DirectedLanes parent) {
        super(id);
        this.lanes = parent;
        this.roadSpecs = road_specs;
        LOG.log("New lane (", id.toString(), ") created: length=", this.roadSpecs.length, "m, width=", this.roadSpecs.width, "m.");
    }

    /**
     * Gets the width of the lane
     *
     * @return Width in meters
     */
    public double getWidth() {
        return this.roadSpecs.width;
    }

    /**
     * Gets the length of the lane
     *
     * @return Length in meters
     */
    public double getLength() {
        return this.roadSpecs.length;
    }

    /**
     * Gets the Lane's ID
     *
     * @return ID tag for the lane
     */
    public ID getID() {
        return super.getID();
    }

    /**
     * Gets the Road ID tag the lane belongs to
     *
     * @return Road's ID tag
     */
    public ID getRoadID() {
        return this.lanes.getRoad().getID();
    }

    /**
     * Gets the Parent road it belongs to
     *
     * @return Road
     */
    public Road getRoad() {
        return this.lanes.getRoad();
    }

    /**
     * Gets the next link in the direction of the lane
     *
     * @return Next link
     */
    public Link getNextLink() {
        return this.nextLink;
    }

    /**
     * Connects the directed end of the lane to a link
     *
     * @param link Link to connect to
     */
    public void connect(Link link) {
        this.nextLink = link;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void tick(SimulationTick tick) {

    }
}