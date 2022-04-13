package cz.ptw.crossroads.engine;

import lombok.ToString;
import lombok.extern.apachecommons.CommonsLog;

/**
 * Event representing car departure from a crossroad.
 */
@ToString(callSuper = true)
@CommonsLog
public class CarDeparture extends Event {

    private final Car car;
    private final Direction direction;

    public CarDeparture(double time, Car car, Direction direction) {
        super();
        this.direction = direction;
        super.time = time;
        this.car = car;
    }

    @Override
    public void execute(AbstractSimulator simulator) {
        log.info(car + " departure in: " + time + " direction: " + direction);
    }
}
