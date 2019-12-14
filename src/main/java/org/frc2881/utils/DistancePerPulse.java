package org.frc2881.utils;

/**
 * Helper for monitoring for conditions where motors suck too much current, usually because the motor isn't turning.
 */
public class DistancePerPulse {

    public static double get(double highestGearTeethNumber, double lowestGearTeethNumber, double encoderCountsPerRevolution, double wheelDiameter) {
        double ticksPerRevolution = encoderCountsPerRevolution;
        double gearRatio = highestGearTeethNumber/lowestGearTeethNumber;
        double wheelCircumference = wheelDiameter * Math.PI; 
        return wheelCircumference / ticksPerRevolution / gearRatio;
    }
}
