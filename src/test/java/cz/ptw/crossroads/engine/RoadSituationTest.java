package cz.ptw.crossroads.engine;

import cz.ptw.crossroads.configuration.RoadConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class RoadSituationTest {

    private AbstractSimulator simulator;

    @BeforeEach
    void setUp() {
        simulator = new AbstractSimulator() {
        };
    }

    @Test
    void theNextEventShouldBeTheFirstCarWhenTrafficLightEventWasExecuted() {
        var roadConfiguration = new RoadConfiguration(Direction.NORTH, TrafficLightState.GREEN, 5, 5, 1);
        initializeSituationForTest(roadConfiguration, roadConfiguration.numberOfCars());

        var actual = simulator.getEvents().peek();
        assertNotNull(actual);
        assertEquals(CarDeparture.class, actual.getClass());
        assertEquals("CarDeparture(super=Event(time=1.0), car=Car(super=Event(time=0.0), number=0), direction=NORTH)", ((CarDeparture) actual).toString());
    }

    @Test
    void theNextEventShouldBeRedTrafficLight() {
        var roadConfiguration = new RoadConfiguration(Direction.NORTH, TrafficLightState.GREEN, 5, 5, 3);
        initializeSituationForTest(roadConfiguration, 0);

        var actual = simulator.getEvents().peek();
        assertNotNull(actual);
        assertEquals(TrafficLight.class, actual.getClass());
        assertEquals("TrafficLight(super=Event(time=5.0), cars=[], direction=NORTH, greenDuration=5.0, redDuration=5.0, state=RED)", ((TrafficLight) actual).toString());
    }

    @Test
    void theTrafficLightShouldHaveEmptyVectorBecauseAllCarsAreGone() {
        var roadConfiguration = new RoadConfiguration(Direction.NORTH, TrafficLightState.GREEN, 5, 5, 0);
        initializeSituationForTest(roadConfiguration, 5);

        Event actual;
        while (!((actual = simulator.getEvents().remove()) instanceof TrafficLight)) {
        }

        assertNotNull(actual);
        assertEquals(TrafficLight.class, actual.getClass());
        assertEquals("TrafficLight(super=Event(time=5.0), cars=[], direction=NORTH, greenDuration=5.0, redDuration=5.0, state=RED)", ((TrafficLight) actual).toString());
    }


    @Test
    void theNextEventShouldBeTrafficLightChangeWithOneCarInVector() {
        var roadConfiguration = new RoadConfiguration(Direction.NORTH, TrafficLightState.RED, 5, 5, 0);
        initializeSituationForTest(roadConfiguration, 1);

        Event actual = simulator.getEvents().peek();
        assertNotNull(actual);
        assertEquals(TrafficLight.class, actual.getClass());
        assertEquals("TrafficLight(super=Event(time=5.0), cars=[Car(super=Event(time=0.0), number=0)], direction=NORTH, greenDuration=5.0, redDuration=5.0, state=GREEN)", ((TrafficLight) actual).toString());
    }

    @Test
    void theNextEventShouldBeSwitchToGreenWithFourCarsWhenIsProcessStartedWith12Cars() {
        var roadConfiguration = new RoadConfiguration(Direction.NORTH, TrafficLightState.GREEN, 4, 5, 0);
        initializeSituationForTest(roadConfiguration, 12);

        IntStream.range(0, 3).forEach(number -> {
            Event actual;
            while (!((actual = simulator.getEvents().remove()) instanceof TrafficLight)) {
            }

            actual.execute(simulator);

        });

        Event actual;
        while (!((actual = simulator.getEvents().remove()) instanceof TrafficLight)) {
        }

        assertNotNull(actual);
        assertEquals(TrafficLight.class, actual.getClass());
        assertEquals("TrafficLight(super=Event(time=18.0), " +
                "cars=[" +
                "Car(super=Event(time=8.0), number=8), " +
                "Car(super=Event(time=9.0), number=9), " +
                "Car(super=Event(time=10.0), number=10), " +
                "Car(super=Event(time=11.0), number=11)" +
                "], direction=NORTH, greenDuration=4.0, redDuration=5.0, state=GREEN)", ((TrafficLight) actual).toString());
    }

    private void initializeSituationForTest(RoadConfiguration roadConfiguration, int countOfCars) {
        var trafficLight = new TrafficLight(roadConfiguration.direction(), roadConfiguration.initialState(), roadConfiguration.greenDuration(), roadConfiguration.redDuration());

        IntStream.range(0, countOfCars).forEach(carNumber -> {
            var car = new Car(carNumber);
            car.setTime((double) carNumber);
            trafficLight.insert(car);
        });

        trafficLight.execute(simulator);
    }
}
