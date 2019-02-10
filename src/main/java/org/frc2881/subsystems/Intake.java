// RobotBuilder Version: 2.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.

package org.frc2881.subsystems;

import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Intake extends Subsystem {

    public enum GripperState {OPEN, CLOSED}
    public enum SuctionState {OPEN, CLOSED, BUTTON}
    public enum RollerState {INTAKE, EJECT, BUTTON}

    private final PowerDistributionPanel pdp = new PowerDistributionPanel(10);
    private Spark cargoIntakeMotor;
    private int intakecargoRollerPdpChannel = 1;
    private Solenoid hPSuctionCup;
    private Solenoid hPGripper;
    private Solenoid hPIntakePlanB;
    private Ultrasonic hPDistanceEcholocation;
    private Spark hPIntakeMotor;

    private int intakeHPRollerPdpChannel = 1;

    public Intake() {
        
//        cargoDistanceEcholocation = new Ultrasonic(4, 5);
//        addChild("cargo Distance Echolocation",cargoDistanceEcholocation);


        cargoIntakeMotor = new Spark(4);
        addChild("Cargo Intake Motor",cargoIntakeMotor);
        cargoIntakeMotor.setInverted(false);


        hPSuctionCup = new Solenoid(11, 1);
        addChild("HP Suction Cup Solenoid",hPSuctionCup);

        hPGripper = new Solenoid(11, 2);
        addChild("HP Gripper Solenoid",hPGripper);
        
        
        hPIntakePlanB = new Solenoid(11, 3);
        addChild("HP Intake Plan B Solenoid",hPIntakePlanB);


//        hPDistanceEcholocation = new Ultrasonic(4, 5);
//        addChild("HP Distance Echolocation",hPDistanceEcholocation);
        
        
        hPIntakeMotor = new Spark(3);
        addChild("HP Intake Motor",hPIntakeMotor);
        hPIntakeMotor.setInverted(false);
    }

    @Override
    public void initDefaultCommand() {

        // Set the default command for a subsystem here.
        // setDefaultCommand(new MySpecialCommand());
    }

    public void suction(SuctionState state) {
        if (state == SuctionState.BUTTON) {
            hPSuctionCup.set(!hPSuctionCup.get());

        } else {
            hPSuctionCup.set(state == SuctionState.OPEN);
        }
    }

    @Override
    public void periodic() {
        // Put code here to be run every loop

    }

    public double getCargoRollerCurrent(){
        return pdp.getCurrent(intakecargoRollerPdpChannel);
    }

    public void cargoRollers(double speed, RollerState state) {
        
        //POSITIVE IS EJECTING
        if (state == RollerState.EJECT) {
            cargoIntakeMotor.set(speed);
        } else if (state == RollerState.INTAKE){
            cargoIntakeMotor.set(-speed);
        }
        else {
            cargoIntakeMotor.set(speed);
        }
    }

    public boolean getCargoRollers(){
        return (cargoIntakeMotor.get() >= 0.05);
    }

    //Stops the rollers (put at the end of the command)
    public void stopCargoRollers() {
        cargoIntakeMotor.set(0);
    }

    public double getHPRollerCurrent(){
        return pdp.getCurrent(intakeHPRollerPdpChannel);
    }

    public void HPRollers(double speed) {
        hPIntakeMotor.set(speed);
    }

    public boolean getHPRollers(){
        return (hPIntakeMotor.get() >= 0.05);
    }

    //Stops the rollers (put at the end of the command)
    public void stopHPRollers() {
        hPIntakeMotor.set(0);
    }

    public void setHPGripper(GripperState state) {
        hPGripper.set(state == GripperState.CLOSED);
    }
}
