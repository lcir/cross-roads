package cz.ptw.crossroads.engine;

import cz.ptw.crossroads.configuration.CrossroadConfiguration;
import cz.ptw.crossroads.configuration.RoadConfiguration;
import cz.ptw.crossroads.configuration.SimulationConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SimulatorTest {

    private CrossroadSimulator simulator;

    @BeforeEach
    void setUp() {
        simulator = new CrossroadSimulator();
    }

    @Test
    void shouldStartAndStopAfter10SecondsWith6EventsInQueueWhenConfigurationWith50CarsAnd10SecondsToInterrupt() throws InterruptedException {
        var southRoadConfiguration = new RoadConfiguration(Direction.SOUTH, TrafficLightState.GREEN, 5, 4, 50);

        var simulationConfiguration = new SimulationConfiguration(10, 0, 60,
                new CrossroadConfiguration(Collections.singletonList(southRoadConfiguration)));

        simulator.start(simulationConfiguration);

        assertEquals(simulationConfiguration.interruptAt(), simulator.getTime());
        assertEquals(6, simulator.getEvents().size());
    }
}
