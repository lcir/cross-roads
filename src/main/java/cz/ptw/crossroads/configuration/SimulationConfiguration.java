package cz.ptw.crossroads.configuration;

public record SimulationConfiguration(double interruptAt, int sleepTimeInMillis, int generatingPeriodSeconds,
                                      CrossroadConfiguration crossRoadConfiguration) {
}
