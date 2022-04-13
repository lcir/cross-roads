package cz.ptw.crossroads.engine;

import cz.ptw.crossroads.configuration.RoadConfiguration;
import cz.ptw.crossroads.configuration.SimulationConfiguration;
import org.springframework.stereotype.Component;

/**
 * Implementation of simulator.
 */
@Component
public class CrossroadSimulator extends AbstractSimulator {

    /**
     * Main start method of simulation.
     * @param configuration it is necessary to provide configuration of simulation.
     * @throws InterruptedException Because of sleep in while cycle...
     */
    public void start(SimulationConfiguration configuration) throws InterruptedException {

        configuration.crossRoadConfiguration()
                .roadConfigurations()
                .forEach(roadConfiguration ->
                        registerDirection(configuration.generatingPeriodSeconds(), roadConfiguration));

        doAllEvents(configuration.interruptAt(), configuration.sleepTimeInMillis());
    }

    private void registerDirection(int generatingPeriodSeconds, RoadConfiguration roadConfiguration) {
        TrafficLight trafficLight = new TrafficLight(roadConfiguration.direction(), roadConfiguration.initialState(), roadConfiguration.greenDuration(), roadConfiguration.redDuration());
        Generator generator = new Generator(roadConfiguration.numberOfCars(), trafficLight, generatingPeriodSeconds);
        generator.setTime(0.0);
        insert(generator);
        insert(trafficLight);
    }
}
