package cz.ptw.crossroads.engine;

import lombok.RequiredArgsConstructor;
import lombok.ToString;

/***
 * This event is representing car generator.
 * Generator is configured by constructor with three parameters:
 * numberOfCars - How many cars you need to generate in period - generatingPeriodSeconds.
 * And the last param is trafficLight
 */
@ToString(callSuper = true, of = {"carNumber", "numberOfCars", "generatingPeriodSeconds"})
@RequiredArgsConstructor
public class Generator extends Event {

    private final int numberOfCars;
    private final TrafficLight trafficLight;
    private final double generatingPeriodSeconds;
    private int carNumber = 0;

    /**
     * How generator work is obvious, but short note.
     * Generator generate cars in "batch" - it runs once for generatingPeriodSeconds and generates numberOfCars.
     * Every car has number, and a time when arrive to the crossroad.
     * After the car is created, is the car inserted to the TrafficLight vector...
     * After generator is with creation of cars done, then re-plan self to the next run.
     *
     * @param simulator simulator instance
     */
    @Override
    public void execute(AbstractSimulator simulator) {

        double deltaCar = generatingPeriodSeconds / numberOfCars;

        for (int i = 0; i < numberOfCars; i++) {
            Car car = new Car(carNumber++);
            car.setTime(time + (i * deltaCar));
            trafficLight.insert(car);
        }
        time += generatingPeriodSeconds;
        simulator.insert(this);
    }
}