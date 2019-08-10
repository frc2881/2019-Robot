package org.frc2881.utils;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;


public class NTValue {
    private static NetworkTableInstance ntinst;
    private static NetworkTable table;
    private static NetworkTableEntry cameraForward;
    private static NetworkTableEntry cargoInfo;
    private static NetworkTableEntry targetInfo;
    private static double[] cargoInfoArray;
    private static Double[][] cargo;
    private static double[] targetInfoArray;
    private static Double[][] target;
    private static double[] targetInfo1;
    private static double[] targetInfo2;
    private static double[] targetInfo3;
    private static double[] targetInfo4;
    private static double[] defaultValue = new double[0];
    private Boolean first;

    static {
        ntinst = NetworkTableInstance.getDefault();
        table = ntinst.getTable("RPi");
        cameraForward = table.getEntry("cameraForward");
        cargoInfo = table.getEntry("cargoInfo");
        targetInfo = table.getEntry("targetInfo");
    }

    public static void setCameraForward(boolean forward) {
        cameraForward.setBoolean(forward);
    }

    public static boolean getCameraForward() {
        return cameraForward.getBoolean(true);
    } 

    public static int biggestCargoIndex() {
        cargoInfoArray = cargoInfo.getDoubleArray(defaultValue);
        int totalCargo = cargoInfoArray.length / 3;
        cargo = new Double[totalCargo][3];
        int biggestCargo = 0;

        for (int i = 0; i < totalCargo; i++) {
            cargo[i][0] = cargoInfoArray[i * 3];//x
            cargo[i][1] = cargoInfoArray[i * 3 + 1];//y
            cargo[i][2] = cargoInfoArray[i * 3 + 2];//size
        }

        for (int i = 0; i < totalCargo; i++) {
            if (cargo[i][3] > cargo[biggestCargo][3]){  
                biggestCargo = i;
            }  
        }

        return biggestCargo;

    }

    public static void targetLocation() {
        targetInfoArray = targetInfo.getDoubleArray(defaultValue);
        int totalTargets = targetInfoArray.length / 3;
        target = new Double[totalTargets][3];
        
        for(int i = 0; i < totalTargets; i++) {
            target[i][0] = targetInfoArray[i * 3];
            target[i][1] = targetInfoArray[i * 3 + 1];
            target[i][2] = targetInfoArray[i * 3 + 2];
        }

    }

    private static void groupLines() {
        targetInfoArray = targetInfo.getDoubleArray(defaultValue);

        Double[][] line = new Double[targetInfoArray.length / 4][4];
        Double x1;
        Double y1;
        Double x2;
        Double y2;
        Double angle;
        
        for (int i = 0; i < targetInfoArray.length / 4; i++) {
            x1 = line[i][0] = cargoInfoArray[i * 4];//x1
            y1 = line[i][1] = cargoInfoArray[i * 4 + 1];//y1
            x2 = line[i][2] = cargoInfoArray[i * 4 + 2];//x2
            y2 = line[i][3] = cargoInfoArray[i * 4 + 3];//y2
            angle = line[i][4] = Math.atan((x2-x1)/(y2-y1));//angle
        }
        
    }

    public static double getCargoX() {
        //find one with largest radius first
        return cargo[biggestCargoIndex()][0];
    }

    public static double getCargoY() {
        //find one with largest radius first
        return cargo[biggestCargoIndex()][1];
    }

    public static double getCargoR() {
        //find one with largest radius first
        return cargo[biggestCargoIndex()][2];
    }
}
