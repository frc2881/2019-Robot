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

import edu.wpi.first.wpilibj.CounterBase.EncodingType;

import org.frc2881.commands.scoring.arm.ArmControl;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

/**
 *
 */
public class Arm extends PIDSubsystem {

    public enum WristState {UP, DOWN, BUTTON}
    public static double HIGH_GOAL_HEIGHT = 3;
    public static double MEDIUM_GOAL_HEIGHT = 2;
    public static double ILLEGAL_HEIGHT = 1.5;
    public static double LOW_GOAL_HEIGHT = 1;
    public static double FLOOR = 0;
    
    private static final double topLimit = 7;
    private static final double bottomLimit = 0;
    private static final double topThreshold = 5;
    private static final double bottomThreshold = 3;

    private Spark armMotor;
    private AnalogInput armPotentiometer;
    private boolean isArmCalibrated;
    private Solenoid wristSolenoid;

    // Initialize your subsystem here
    public Arm() {
        super("Arm", 1.0, 0.0, 0.0);
        setAbsoluteTolerance(0.2);
        getPIDController().setContinuous(false);
        getPIDController().setName("Arm", "PIDSubsystem Controller");
        LiveWindow.add(getPIDController());

        armMotor = new Spark(0);
        addChild("Arm Motor",armMotor);
        armMotor.setInverted(false);
        
        armPotentiometer = new AnalogInput(1);
        addChild("Arm Potentiometer", armPotentiometer);
        armPotentiometer.setPIDSourceType(PIDSourceType.kRate);
        
        wristSolenoid = new Solenoid(11, 4);
        addChild("Wrist Solenoid",wristSolenoid);

        // Use these to get going:
        // setSetpoint() -  Sets where the PID controller should move the system
        //                  to
        // enable() - Enables the PID controller.
    }
    public void reset() {
        isArmCalibrated = false;
        armMotor.setSafetyEnabled(false);  // wait for calibration before enabling motor safety
        getPIDController().reset();
        armPotentiometer.resetAccumulator();
    }
            
    @Override
    public void initDefaultCommand() {
        setDefaultCommand(new ArmControl());
    }

    public void moveWrist(WristState state){
        if (state == WristState.BUTTON) {
            wristSolenoid.set(!wristSolenoid.get());

        } else {
            wristSolenoid.set(state == WristState.UP);
        }
    }

    @Override
    protected double returnPIDInput() {
        // Return your input value for the PID loop
        // e.g. a sensor, like a potentiometer:
        // yourPot.getAverageVoltage() / kYourMaxVoltage;

        return armPotentiometer.pidGet();

    }

    @Override
    protected void usePIDOutput(double output) {
        // Use output to drive your system, like a motor
        // e.g. yourMotor.set(output);

        armMotor.pidWrite(output);

    }

    public boolean isSpeedReallySmall() {
        return Math.abs(armPotentiometer.pidGet()) < .05;
    }

    public Double getArmRate(){
        return armPotentiometer.pidGet();
    }

    public Double getArmPosition(){
        return armPotentiometer.pidGet();
    }

    public void setArmMotorSpeed(double speed) {
        // Make sure the motor doesn't move too fast when it's close to the top & bottom limits
        double min = getArmMotorMin();
        double max = getArmMotorMax();

        if (speed < min) {
            speed = min;
        }
        if (speed > max) {
            speed = max;
        }

        armMotor.set(speed);
    }
    private double getArmMotorMin() {
        double position = armPotentiometer.pidGet();
        double min = -1;
       
            if (position <= bottomLimit) {
                min = -0.3;
            } else if (position <= bottomThreshold) {
                min = -(.3 + (.7 * (position - bottomLimit) / (bottomThreshold - bottomLimit)));
            }
          
        
        return min;
    }

    private double getArmMotorMax() {
        double position = armPotentiometer.pidGet();
        double max = 1;
        if (!isArmCalibrated) {
            max = 0;
        } else {
            if (position >= topLimit) {
                max = 0.3;
            } else if (position >= topThreshold) {
                max = 1 - (.7 * (position - topThreshold) / (topLimit - topThreshold));
            }
            
         
        }
        return max;
    }
    public void resetArmPotentiometer() {
        armPotentiometer.resetAccumulator();
        isArmCalibrated = true;
//        armMotor.setExpiration(0.1);
//        armMotor.setSafetyEnabled(true);
    }

}
