package cz.ptw.crossroads.engine;

import cz.ptw.crossroads.configuration.RoadConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class GeneratorTest {

    private AbstractSimulator simulator;

    @BeforeEach
    void setUp() {
        simulator = new AbstractSimulator() {
        };
    }

    @Test
    void shouldGenerateTwoCarsOneInZeroSecondsAndTheSecondIn30SecondsWhenPeriodIs60SecondsAndINeed2Cars() {
        var roadConfiguration = new RoadConfiguration(Direction.NORTH, TrafficLightState.GREEN, 5, 5, 2);
        var trafficLight = new TrafficLight(roadConfiguration.direction(), roadConfiguration.initialState(), roadConfiguration.greenDuration(), roadConfiguration.redDuration());

        Generator generator = new Generator(roadConfiguration.numberOfCars(), trafficLight, 60);
        generator.setTime(0.0);

        generator.execute(simulator);
        trafficLight.execute(simulator);

        Event actual;
        while (!((actual = simulator.getEvents().remove()) instanceof TrafficLight)) {
        }

        assertNotNull(actual);
        assertEquals(TrafficLight.class, actual.getClass());
        assertEquals("TrafficLight(super=Event(time=5.0), cars=[Car(super=Event(time=30.0), number=1)], direction=NORTH, greenDuration=5.0, redDuration=5.0, state=RED)", ((TrafficLight) actual).toString());

        var generatorFromSimulation = simulator.getEvents().remove();
        assertNotNull(generatorFromSimulation);
        assertEquals(Generator.class, generatorFromSimulation.getClass());
        assertEquals("Generator(super=Event(time=60.0), numberOfCars=2, generatingPeriodSeconds=60.0, carNumber=2)", ((Generator) generatorFromSimulation).toString());
    }

    @Test
    void shouldTheFourthCarBeGeneratedIn46SecondsWhenPeriodIs60SecondsAndINeed4Cars() {
        var roadConfiguration = new RoadConfiguration(Direction.NORTH, TrafficLightState.GREEN, 100, 100, 4);
        var trafficLight = new TrafficLight(roadConfiguration.direction(), roadConfiguration.initialState(), roadConfiguration.greenDuration(), roadConfiguration.redDuration());

        Generator generator = new Generator(roadConfiguration.numberOfCars(), trafficLight, 60);
        generator.setTime(0.0);

        generator.execute(simulator);
        trafficLight.execute(simulator);

        simulator.getEvents().remove();
        simulator.getEvents().remove();
        simulator.getEvents().remove();

        Event actual = simulator.getEvents().remove();

        assertNotNull(actual);
        assertEquals(CarDeparture.class, actual.getClass());
        assertEquals(46.0, actual.getTime());
        assertEquals("CarDeparture(super=Event(time=46.0), car=Car(super=Event(time=45.0), number=3), direction=NORTH)", ((CarDeparture) actual).toString());
    }
}
