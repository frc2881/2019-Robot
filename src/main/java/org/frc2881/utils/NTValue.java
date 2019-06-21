package org.frc2881.utils;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;


public class NTValue {
    private static NetworkTableInstance ntinst;
    private static NetworkTable table;
    private static NetworkTableEntry cameraForward;
    private static NetworkTableEntry cargoInfo;
    private double[] defaultValue = new double[0];

    static {
        ntinst = NetworkTableInstance.getDefault();
        table = ntinst.getTable("RPi");
        cameraForward = table.getEntry("cameraForward");
        cargoInfo = table.getEntry("cargoInfo");
    }

    public static void setCameraForward(boolean forward) {
        cameraForward.setBoolean(forward);
    }

    public static boolean getCameraForward() {
        return cameraForward.getBoolean(true);
    } 

    public double getCargoY() {
        //find one with largest radius first
        cargoInfo.getDoubleArray(defaultValue);
        return 2;
    }
 
}
