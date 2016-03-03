package kcl.teamIndexZero.traffic.gui;

import kcl.teamIndexZero.traffic.gui.components.MainWindow;
import kcl.teamIndexZero.traffic.gui.components.simulationChooserDialog.ChooserDialog;
import kcl.teamIndexZero.traffic.gui.mvc.GuiController;
import kcl.teamIndexZero.traffic.gui.mvc.GuiModel;
import kcl.teamIndexZero.traffic.log.Logger;
import kcl.teamIndexZero.traffic.log.Logger_Interface;
import kcl.teamIndexZero.traffic.simulator.Simulator;
import kcl.teamIndexZero.traffic.simulator.data.GraphConstructor;
import kcl.teamIndexZero.traffic.simulator.data.ID;
import kcl.teamIndexZero.traffic.simulator.data.SimulationMap;
import kcl.teamIndexZero.traffic.simulator.data.SimulationParams;
import kcl.teamIndexZero.traffic.simulator.data.descriptors.LinkDescription;
import kcl.teamIndexZero.traffic.simulator.data.descriptors.RoadDescription;
import kcl.teamIndexZero.traffic.simulator.data.features.Junction;
import kcl.teamIndexZero.traffic.simulator.data.links.LinkType;
import kcl.teamIndexZero.traffic.simulator.data.mapObjects.MapPosition;
import kcl.teamIndexZero.traffic.simulator.data.mapObjects.Vehicle;
import kcl.teamIndexZero.traffic.simulator.exeptions.MapIntegrityException;
import kcl.teamIndexZero.traffic.simulator.osm.OsmParseResult;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;


/**
 * Main class (entry poing for simulator Graphical User Interface).
 */
public class SimulatorGui {

    protected static Logger_Interface LOG = Logger.getLoggerInstance(SimulatorGui.class.getSimpleName());

    private GuiModel model;

    /**
     * Entry point.
     *
     * @param args CLI parameters
     */
    public static void main(String[] args) {
        new SimulatorGui();
    }

    /**
     * Default constructor.
     */
    public SimulatorGui() {
        ChooserDialog.showForOSMLoadResult(this::startSimulatorWindow);
    }

    private void startSimulatorWindow(OsmParseResult result) {
        try {
            //TODO factory then pass the stuff below to graph constructor
            java.util.List<Junction> junctions = new ArrayList<>();
            java.util.List<LinkDescription> links = new ArrayList<>();
            java.util.List<RoadDescription> roads = result.descriptionList;

            links.add(new LinkDescription(roads.get(0).getId(), roads.get(1).getId(), LinkType.SYNC_TL, new ID("Link1")));
            junctions.add(new Junction(new ID("Junction1")));

            GraphConstructor graph = new GraphConstructor(junctions, roads, links); //TODO temp stuff. need to take care of the exceptions too

            SimulationMap map = new SimulationMap(4, 400, graph);
            map.latStart = result.boundingBox.start.latitude;
            map.latEnd = result.boundingBox.end.latitude;
            map.lonStart = result.boundingBox.start.longitude;
            map.lonEnd = result.boundingBox.end.longitude;

            model = new GuiModel();

            SimulationImageProducer imageProducer = new SimulationImageProducer(map, model);

            GuiController controller = new GuiController(model, () -> {

                SimulationDelay delay = new SimulationDelay(50);
                CarAdder adder = new CarAdder(map);
                CarRemover remover = new CarRemover(map);

                map.addMapObject(new Vehicle("Ferrari ES3 4FF", new MapPosition(0, 0, 2, 1), 0.05f, 0));
                map.addMapObject(new Vehicle("Taxi TT1", new MapPosition(400, 5, 2, 1), -0.1f, 0));

                return new Simulator(
                        new SimulationParams(LocalDateTime.now(), 10, 1000),
                        Arrays.asList(
                                map,
                                imageProducer,
                                adder,
                                remover,
                                delay)
                );
            });

            // that's where we reset model into default state - before the simulation is started.

            model.reset();
            MainWindow window = new MainWindow(model, controller);
            window.setVisible(true);
        } catch (MapIntegrityException e) {
            LOG.log_Fatal("Map integrity compromised.");
            LOG.log_Exception(e);
        }
    }

}
