package cz.ptw.crossroads.configuration;

import cz.ptw.crossroads.engine.Direction;
import cz.ptw.crossroads.engine.TrafficLightState;

public record RoadConfiguration(Direction direction, TrafficLightState initialState, double greenDuration,
                                double redDuration, int numberOfCars) {
}
