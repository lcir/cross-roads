package cz.ptw.crossroads.engine;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Abstract representation of event.
 * Every event has time, when is planned, execute method - the event business logic. And must implement comparable interface.
 * <p>
 * Comparable interface is here because of PriorityQueue in simulator.
 */
@ToString
public abstract class Event implements Comparable<Event> {

    @Getter
    @Setter
    protected Double time;

    /**
     * Execute method - the event business logic
     *
     * @param simulator simulator instance
     */
    public abstract void execute(AbstractSimulator simulator);

    /**
     * compareTo is here because of PriorityQueue in simulator.
     *
     * @param anotherEvent another event.
     * @return -1 this &lt y, 0 equals, 1 this &gt y
     */
    @Override
    public int compareTo(Event anotherEvent) {
        return this.time.compareTo(anotherEvent.time);
    }

}