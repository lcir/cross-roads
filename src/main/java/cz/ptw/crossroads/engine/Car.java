package cz.ptw.crossroads.engine;

import lombok.RequiredArgsConstructor;
import lombok.ToString;

@ToString(callSuper = true)
@RequiredArgsConstructor
public class Car extends Event {

    private final int number;

    @Override
    public void execute(AbstractSimulator simulator) {

    }
}
