package kcl.teamIndexZero.traffic.simulator.data.features;

import kcl.teamIndexZero.traffic.log.Logger;
import kcl.teamIndexZero.traffic.log.Logger_Interface;
import kcl.teamIndexZero.traffic.simulator.data.ID;
import kcl.teamIndexZero.traffic.simulator.data.geo.GeoPolyline;

/**
 * Road holds the property and description of a road feature
 */
public class Road extends Feature {
    private static Logger_Interface LOG = Logger.getLoggerInstance(Road.class.getSimpleName());
    private final String name;
    private DirectedLanes forwardSide;
    private DirectedLanes backwardSide;
    private ID id;
    private RoadSpecs roadSpecs = new RoadSpecs();
    private GeoPolyline polyline;

    /**
     * Constructor
     *
     * @param id                 Feature ID tag
     * @param outgoingLanesCount Number of ongoing lanes from the start viewpoint of the road
     * @param incomingLanesCount Number of incoming lanes from the start viewpoint of the road
     * @param roadLength         Length of the road
     * @throws IllegalArgumentException when the number of lanes given is less than 1 or the road length is < 0.5
     */
    public Road(ID id, int outgoingLanesCount, int incomingLanesCount, double roadLength, GeoPolyline polyline, String name) throws IllegalArgumentException {
        super(id);
        this.polyline = polyline;
        this.name = name;
        if (outgoingLanesCount < 1 && incomingLanesCount < 1) {
            LOG.log_Error("Total number of lanes given is ", outgoingLanesCount + incomingLanesCount, ".");
            throw new IllegalArgumentException("Number of lanes on road is 0! A road must have at least 1 lane.");
        }

        this.id = id;
        this.roadSpecs.length = roadLength;
        this.forwardSide = new DirectedLanes(new ID(id, "L"), incomingLanesCount, roadSpecs, this);
        this.backwardSide = new DirectedLanes(new ID(id, "R"), outgoingLanesCount, roadSpecs, this);
    }

    /**
     * Gets the name of the road
     *
     * @return Road name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the length of the road
     *
     * @return Length of road
     */
    public double getRoadLength() {
        return this.roadSpecs.length;
    }

    /**
     * Gets the number of incoming lanes
     *
     * @return Number of left lanes
     */
    public int getIncomingLaneCount() {
        return this.backwardSide.getNumberOfLanes();
    }

    /**
     * Gets the number of outgoing lanes
     *
     * @return Number of right lanes
     */
    public int getOutgoingLaneCount() {
        return this.forwardSide.getNumberOfLanes();
    }

    /**
     * Gets the Incoming lanes
     *
     * @return Incoming lanes
     */
    public DirectedLanes getBackwardSide() {
        return backwardSide;
    }

    /**
     * Gets the outgoing lanes
     *
     * @return Outgoing lanes
     */
    public DirectedLanes getForwardSide() {
        return forwardSide;
    }

    /**
     * Gets the polyline of the road
     *
     * @return polyline
     */
    public GeoPolyline getPolyline() {
        return polyline;
    }
}
