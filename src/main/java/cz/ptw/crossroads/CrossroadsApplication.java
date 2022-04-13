package cz.ptw.crossroads;

import cz.ptw.crossroads.configuration.CrossroadConfiguration;
import cz.ptw.crossroads.configuration.RoadConfiguration;
import cz.ptw.crossroads.configuration.SimulationConfiguration;
import cz.ptw.crossroads.engine.CrossroadSimulator;
import cz.ptw.crossroads.engine.Direction;
import cz.ptw.crossroads.engine.TrafficLightState;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

/**
 * The main class for application.
 * It is not necessary run application as SpringBootApplication, but why not :-)
 */
@SpringBootApplication
public class CrossroadsApplication {

    public static void main(String[] args) throws InterruptedException {

        var northRoadConfiguration = new RoadConfiguration(Direction.NORTH, TrafficLightState.GREEN, 60, 60, 5);
        var eastRoadConfiguration = new RoadConfiguration(Direction.EAST, TrafficLightState.RED, 60, 60, 5);
        var westRoadConfiguration = new RoadConfiguration(Direction.WEST, TrafficLightState.RED, 60, 60, 15);
        var southRoadConfiguration = new RoadConfiguration(Direction.SOUTH, TrafficLightState.GREEN, 60, 60, 10);

        var simulationConfiguration = new SimulationConfiguration(200, 50, 60,
                new CrossroadConfiguration(List.of(northRoadConfiguration, eastRoadConfiguration, westRoadConfiguration, southRoadConfiguration)));

        var applicationContext = SpringApplication.run(CrossroadsApplication.class, args);
        var crossroadSimulator = applicationContext.getBean(CrossroadSimulator.class);

        crossroadSimulator.start(simulationConfiguration);
    }

}
