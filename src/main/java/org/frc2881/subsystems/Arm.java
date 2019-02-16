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

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import org.frc2881.RobotType;
import org.frc2881.commands.scoring.arm.ArmControl;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.Sendable;
import edu.wpi.first.wpilibj.SendableBase;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;

/**
 *
 */
public class Arm extends PIDSubsystem {

    /** From the pivot point to the plane of the hatch panel is about 44 inches. */
    private static final double ARM_LENGTH = 44.0;
    /** Height of the gripper midpoint when the arm is horizontal. */
    private static final double HEIGHT_AT_HORIZONTAL = 50.0;
    /** The potentiometer reads about v_in/vcc=0.463 when the arm is horizontal. */
    private static final double POTENTIOMETER_AT_HORIZONTAL = 0.463;

    public enum WristState {UP, DOWN, BUTTON}
    public static double HIGH_GOAL_HEIGHT = 75;
    public static double MEDIUM_GOAL_HEIGHT = 60;
    public static double ILLEGAL_HEIGHT = 45;
    public static double LOW_GOAL_HEIGHT = 30;
    public static double HP_RELEASE_HEIGHT = 15;
    public static double FLOOR = 0;
    
    private static final double topLimit = 7;
    private static final double bottomLimit = 0;
    private static final double topThreshold = 5;
    private static final double bottomThreshold = 3;

    private SpeedController armMotor;
    private boolean isArmCalibrated;
    private Solenoid wristSolenoid;
    private AnalogInput armPotentiometer;
    private Encoder armEncoder;

    // Initialize your subsystem here
    public Arm() {
        super("Arm", 1.0, 0.0, 0.0);
        setAbsoluteTolerance(0.2);
        getPIDController().setContinuous(false);
        getPIDController().setName("Arm", "PIDSubsystem Controller");
        LiveWindow.add(getPIDController());

        if (RobotType.get() == RobotType.COMPETITION_BOT) {
            armMotor = addDevice ("Arm Motor", new CANSparkMax(5, MotorType.kBrushless));
        } else {
            armMotor = addDevice ("Arm Motor", new Spark(0));
        }
        armMotor.setInverted(true);

        
        armEncoder = new Encoder(6, 7, false, EncodingType.k4X);
        addChild("Arm Encoder",armEncoder);
        armEncoder.setDistancePerPulse(1.0);
        armEncoder.setPIDSourceType(PIDSourceType.kRate);
        
        wristSolenoid = new Solenoid(11, 4);
        addChild("Wrist Solenoid",wristSolenoid);

        armPotentiometer = new AnalogInput(1);
        addChild("Arm Potentiometer", armPotentiometer);

        // Use these to get going:
        // setSetpoint() -  Sets where the PID controller should move the system
        //                  to
        // enable() - Enables the PID controller.
    }

    @Override
    public void initSendable(SendableBuilder builder) {
        super.initSendable(builder);
        builder.addDoubleProperty("Height", this::getArmHeight, null);
        builder.addDoubleProperty("Angle", this::getArmAngleDegrees, null);
    }
            
    @Override
    public void initDefaultCommand() {
        setDefaultCommand(new ArmControl());
    }

    public void moveWrist(WristState state){
        if (state == WristState.BUTTON) {
            wristSolenoid.set(!wristSolenoid.get());

        } else {
            wristSolenoid.set(state == WristState.DOWN);
        }
    }

    @Override
    protected double returnPIDInput() {
        // Return your input value for the PID loop
        // e.g. a sensor, like a potentiometer:
      
        return getArmAngleRadians();

    }

    @Override
    protected void usePIDOutput(double output) {
        // Use output to drive your system, like a motor
        // e.g. yourMotor.set(output);

        armMotor.pidWrite(output);
    }

    public void setArmDesiredHeight(double height) {
        setSetpoint(angleFromHeight(height));
    }

    public double getArmHeight() {
        // From the pivot point to the plane of the hatch panel is about 44 inches
        return heightFromAngle(getArmAngleRadians());
    }

    /** Returns the approximate angle of the arm relative to horizontal, in radians. */
    private double getArmAngleRadians() {
        double value = armPotentiometer.getVoltage() / RobotController.getVoltage5V();
        return 4.345 * (POTENTIOMETER_AT_HORIZONTAL - value);
    }

    private double getArmAngleDegrees() {
        return getArmAngleRadians() * 180 / Math.PI;
    }

    private static double heightFromAngle(double radians) {
        return ARM_LENGTH * Math.sin(radians) + HEIGHT_AT_HORIZONTAL;
    }

    private static double angleFromHeight(double height) {
        return Math.asin((height - HEIGHT_AT_HORIZONTAL) / ARM_LENGTH);
    }
    
    public boolean isSpeedReallySmall() {
        return Math.abs(armEncoder.getRate()) < .05;
    }

    public Double getArmRate(){
        return armEncoder.getRate();
    }

    public void setArmMotorSpeed(double speed) {
        // Make sure the motor doesn't move too fast when it's close to the top & bottom limits
        /*double min = getArmMotorMin();
        double max = getArmMotorMax();

        if (speed < min) {
            speed = min;
        }
        if (speed > max) {
            speed = max;
        }
        */
        armMotor.set(speed);
    }

    private double getArmMotorMin() {

        double position = getArmHeight();

        double min = -1;
       
            if (position <= bottomLimit) {
                min = -0.3;
            } else if (position <= bottomThreshold) {
                min = -(.3 + (.7 * (position - bottomLimit) / (bottomThreshold - bottomLimit)));
            }
          
        
        return min;
    }

    private double getArmMotorMax() {

        double position = getArmHeight();

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

    public void resetArmEncoder() {
        armEncoder.reset();

        isArmCalibrated = true;
//        armMotor.setExpiration(0.1);
//        armMotor.setSafetyEnabled(true);
    }

    private <T extends Sendable> T addDevice(String name, T device) {
        super.addChild(name, device);
        return device;
    }


    private CANSparkMax addDevice(String name, CANSparkMax spark) {
        addDevice(name, new SendableBase() {
            public void initSendable(SendableBuilder builder) {
                builder.setSmartDashboardType("Speed Controller");
                builder.setActuator(true);
                builder.setSafeState(spark::disable);
                builder.addDoubleProperty("Value", spark::get, spark::set);
            }
        });
        return spark;
    }

}
