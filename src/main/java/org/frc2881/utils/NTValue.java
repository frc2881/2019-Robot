package org.frc2881.utils;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;


public class NTValue {
    private static NetworkTableInstance ntinst;
    private static NetworkTable table;
    private static NetworkTableEntry cameraForward;
    private static NetworkTableEntry cargoInfo;
    private static double[] cargoInfoArray;
    private static double[] defaultValue = new double[0];
    private Boolean first;

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

    public static Boolean biggerCargoFirst() {
        cargoInfoArray = cargoInfo.getDoubleArray(defaultValue);

        if (cargoInfoArray.length == 0) {
            return true;
        }
        else if (cargoInfoArray.length > 3 && cargoInfoArray[2] < cargoInfoArray[5]) {
            return false;
        }
        else {
            return true;
        }
    }

    public static double getCargoX() {
        //find one with largest radius first
        if (biggerCargoFirst()){
            if (cargoInfoArray.length == 0) {
                return -1;
            }
            return cargoInfoArray[0];
        }
        else {
            return cargoInfoArray[3];
        }
    }

    public static double getCargoY() {
        //find one with largest radius first
        if (biggerCargoFirst()){
            if (cargoInfoArray.length == 0) {
                return -1;
            }
            return cargoInfoArray[1];
        }
        else {
            return cargoInfoArray[4];
        }
    }

    public static double getCargoR() {
        //find one with largest radius first
        if (biggerCargoFirst()){
            if (cargoInfoArray.length == 0) {
                return -1;
            }
            return cargoInfoArray[2];
        }
        else {
            return cargoInfoArray[5];
        }
    }
}
