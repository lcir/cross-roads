package cz.ptw.crossroads.engine;

import lombok.ToString;
import lombok.extern.apachecommons.CommonsLog;

import java.util.Vector;

/**
 * Event representing traffic light on crossroad.
 * Every traffic light needs to have direction, must be configured how long will shine green and red bulb.
 * And also initial state.
 */
@ToString(callSuper = true)
@CommonsLog
public class TrafficLight extends Event {
    private final Vector<Car> cars = new Vector<>();
    private final Direction direction;
    private final double greenDuration;
    private final double redDuration;
    private TrafficLightState state;


    /**
     * Constructor of event
     *
     * @param direction     Something like identification of TrafficLight
     * @param initialState  In the initial state, must light shine green or red.
     * @param greenDuration How long will stay here green.
     * @param redDuration   How long will stay here red.
     */
    public TrafficLight(Direction direction, TrafficLightState initialState, double greenDuration, double redDuration) {
        this.direction = direction;
        this.state = initialState;
        this.greenDuration = greenDuration;
        this.redDuration = redDuration;
        this.time = 0.0;
    }

    /**
     * Method with traffic light business logic.
     * If is green and event logic is executed, are processed cars located in vector. Because if we know, that every car departure
     * from crossroad take 1 second and green light shines for limited time, number of processed cars is limited.
     * <p>
     * If is in vector 20 cars and green light shines only for 10 seconds, the event process only 10 cars.
     * <p>
     * After that is generated event for the traffic light switch and re-plan in simulator.
     *
     * @param simulator simulator instance
     */
    public void execute(AbstractSimulator simulator) {
        log.info("Traffic light switch is " + state + " time: " + this.getTime() + " direction: " + direction);
        if (isGreen()) {
            fireNewCarDepartureEvents(simulator, greenDuration);
            state = TrafficLightState.RED;
            time += greenDuration;
        } else {
            state = TrafficLightState.GREEN;
            time += redDuration;
        }
        simulator.insert(this);
    }

    private void fireNewCarDepartureEvents(AbstractSimulator simulator, double serviceTime) {
        var v = serviceTime > cars.size() ? cars.size() : serviceTime;
        for (double i = 0; i < v; i++) {

            Car remove = cars.get(0);
            if (remove.getTime() < this.time + serviceTime) {
                remove = cars.remove(0);
                double timeToOut = (remove.getTime() > this.time + 1 + i) ? remove.getTime() + 1 : this.time + 1 + i;
                simulator.insert(new CarDeparture(timeToOut, remove, direction));
            }
        }
    }

    private boolean isGreen() {
        return TrafficLightState.GREEN.equals(state);
    }

    /**
     * Method for inserting cars to the traffic light vector.
     *
     * @param car Car event.
     */
    public void insert(Car car) {
        cars.add(car);
    }
}