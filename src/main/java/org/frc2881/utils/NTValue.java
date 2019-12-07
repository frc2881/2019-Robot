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
    private static double[] defaultValue = new double[0];
    private static double[] cargoInfoArray = cargoInfo.getDoubleArray(defaultValue);
    private static Double[][] cargo = new Double[cargoInfoArray.length / 3][3];
    private static double[] targetInfoArray;
    private static Double[][] target = new Double[targetInfoArray.length / 3][2];
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
        int totalCargo = cargoInfoArray.length / 3;
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

    public static Double[][] findTarget() {
        targetInfoArray = targetInfo.getDoubleArray(defaultValue);
        int totalCargo = targetInfoArray.length/ 3;
        Double[][] rocketTarget = new Double[2][2];
        
        for (int i = 0; i < targetInfoArray.length / 3; i++) {
            target[i][0] = targetInfoArray[i * 3];//x
            target[i][1] = targetInfoArray[i * 3 + 1];//y
        }

        int j = 0;
        for (int i = 0; i < totalCargo; i++) {
            if (target[i][1] < 60){  //TODO change this (60) when we get new values from positioned camera
                target[i][0] = rocketTarget[j][0];//x
                target[i][1] = rocketTarget[j][1];//y
                j++;
            }  
        }
        return rocketTarget;
    }

    public static double distanceToMove(){
        Double[][] target = findTarget();
        double pxpIn = (4.8132 + 5.6098)/2; // pixels per inch; from slopes in "numbers" spreadsheet
        double targetHeightIn = (60 - 49.85) * pxpIn; //TODO Find "actual" target HEIGHT for camera purposes

        //Calculations with height (y) for distance from robot (depth) (z)
        double maxInV = 60/pxpIn; // 60 max pixels (vertically)
        double maxRadV = Math.atan2(maxInV, 30); // y/x
        double radpPx = maxRadV/60; // radians per pixel
        double avgHeightPx = (target[0][1] + target[1][1])/2;// avg height (pixels) of targets
        double distanceZFromRocket = targetHeightIn/Math.tan(radpPx * avgHeightPx); //y / tan(theta) = x

        //Calculations with depth (z) for horizontal distance from target (x)
        double avgXPx = (target[0][0] + target[1][0])/2;// avg distance (pixels) from robot x axis
        double distanceXFromRocket = Math.tan(radpPx * avgXPx) * distanceZFromRocket; // x * tan(theta) = y
        return distanceXFromRocket;
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

    public double[] targetPick(){
        //double[] = targetInfoArray
        if(targetInfoArray.length == 9 || targetInfoArray.length == 6){
            return targetInfoArray;
            /*if(targetInfoArray.length == 9){
                for(int j = 0; j < targetInfoArray.length; j++){

                }
            }*/
            
        }
        else if(targetInfoArray.length > 9){
            System.out.println("Too many targets");
            return null;
        }

        //look at spreadsheet: which is left and which is right(115 and 50)?
        for(int i = 0; i < targetInfoArray.length;i+=3){
            if(targetInfoArray.length == 3){
                if(targetInfoArray[i + 1] < 50)
                    System.out.println("left");
                else if(targetInfoArray[i + 1] > 115)
                    System.out.println("right");
            }
        }
        return null;
    }

    //targetInfoArray --> x, y, r
    public double[] visionConvert(){
        double coordinate = 0;
        double[] newTargetInfoArray = new double[6];
        for(int i = 0; i < targetInfoArray.length; i++){
            coordinate = targetInfoArray[i];
            newTargetInfoArray[i] = coordinate/6;
        }
        return newTargetInfoArray;
    }
}
