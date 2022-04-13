package cz.ptw.crossroads.engine;

import lombok.Getter;

import java.util.PriorityQueue;

/**
 * Simple abstract definition of simulator
 */
public abstract class AbstractSimulator {

    @Getter
    private double time;
    @Getter
    private final PriorityQueue<Event> events = new PriorityQueue<>();

    /**
     * Method for inserting events to the priority queue.
     * @param event Simulation event.
     */
    public void insert(Event event) {
        events.add(event);
    }

    /**
     * Main simulation loop with sleep method for interactivness.
     * @param durationInterruptAt duration value, when should be application interrupted.
     * @param sleepTimeInMillis for interactive mode it is good to sleep application for some millis.
     * @throws InterruptedException interrupt exception, because of sleep.
     */
    public void doAllEvents(double durationInterruptAt, int sleepTimeInMillis) throws InterruptedException {
        Event event;
        while ((event = events.poll()) != null) {
            time = event.getTime();
            event.execute(this);

            Thread.sleep(sleepTimeInMillis);

            if (time >= durationInterruptAt)
                return;
        }
    }
}
