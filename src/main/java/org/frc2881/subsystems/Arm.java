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

import java.util.function.DoubleSupplier;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import org.frc2881.RobotType;
import org.frc2881.commands.scoring.arm.ArmControl;
import org.frc2881.utils.frc4048.Logging;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.Sendable;
import edu.wpi.first.wpilibj.SendableBase;
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
    private static final double POTENTIOMETER_AT_HORIZONTAL = 0.463; //0.19;

    //ground: angle: -60.3 read: 11.71, actual: 14.5
    //low: -54.6, 14.2, 18.3
    //middle: -7.1, 44.5, 46.3
    //high: 32.7, 73.8, 74.3

    //HP Middle Score 45.1, -6.3
    //Cargo Middle Score 
    //HP High Score 76.2, 36.7 
    //Cargo High Score 61.2, 14.4; 60.9, 12.6

    public enum WristState {UP, DOWN, BUTTON}
    public enum ArmValue {BUTTON, VALUE}
    public static double HP_HIGH_GOAL_HEIGHT = 76.2;
    public static double HP_MEDIUM_GOAL_HEIGHT = 56;//45.1;
    public static double HP_LOW_GOAL_HEIGHT = 14.2;
    public static double CARGO_HIGH_GOAL_HEIGHT = 62.8;
    public static double CARGO_MEDIUM_GOAL_HEIGHT = 56;//35.4;
    public static double CARGO_LOW_GOAL_HEIGHT = 11.71;
    public static double ILLEGAL_HEIGHT = 13;
    public static double FLOOR = 11.71;
    public static double HIGH_GOAL = 3;
    public static double MEDIUM_GOAL = 2;
    public static double LOW_GOAL = 1;
    
    private double distancePerPulse;

    private static double topLimit = 100;
    private static double bottomLimit = 11;
    private static double topThreshold = topLimit - 10;
    private static double bottomThreshold = bottomLimit + 10;

    public SpeedController armMotor;
    private boolean isArmCalibrated;
    private AnalogInput armPotentiometer;
    private DoubleSupplier armEncoderPosition;
    private DoubleSupplier armEncoderVelocity;
    private double beginningPosition = 0;

    //ENCODER
    private static final double armKc = 0.2;
    private static final double armPc = 1;  // period of oscillation
    private static final double armP = 0.2 * armKc;
    private static final double armI = 0.4 * armKc / armPc;
    private static final double armD = armKc * armPc / 15;

    /*//POTENTIOMETER
    private static final double armKc = 0.36;
    private static final double armPc = 0.7;  // period of oscillation
    private static final double armP = 0.6 * armKc;
    private static final double armI = 0;
    private static final double armD = 0.125 * armP * armPc / 0.05;*/

    // Initialize your subsystem here
    public Arm() {
        //NEED TO ADD ENCODER INTEGRATION B/C ARM WILL DRIFT WHEN ARMTOMIDDLE AND POT WILL NOT READ CHANGES IN ANGLE UNTIL ~6IN
        super("Arm", armP, armI, armD); //1, 0.05, 0
        setAbsoluteTolerance(0.05);
        //setInputRange(Math.toRadians(-65), Math.toRadians(40));
        setInputRange(0, 76);
        getPIDController().setContinuous(false);
        getPIDController().setName("Arm", "PIDSubsystem Controller");
        LiveWindow.add(getPIDController());

        armPotentiometer = new AnalogInput(1);
        addChild("Arm Potentiometer", armPotentiometer);

        if (RobotType.get() == RobotType.COMPETITION_BOT) {
            distancePerPulse = -55/83.0;
            CANSparkMax sparkMax = new CANSparkMax(5, MotorType.kBrushless);
            armMotor = addDevice("Arm Motor", sparkMax);
            sparkMax.setRampRate(0.5);
            
            CANEncoder encoder = sparkMax.getEncoder();
            //final double armAngleRadians = 4.345 * (POTENTIOMETER_AT_HORIZONTAL - armPotentiometer.getVoltage() / RobotController.getVoltage5V());
            final double potHeight = 11;//getArmPotHeight();//ARM_LENGTH * Math.sin(armAngleRadians) + HEIGHT_AT_HORIZONTAL - 11.7;//value @ 0
            beginningPosition = encoder.getPosition() * distancePerPulse;
            armEncoderPosition = () -> encoder.getPosition() * distancePerPulse - beginningPosition + potHeight;
            armEncoderVelocity = () -> encoder.getVelocity() * distancePerPulse;
        } else {
            distancePerPulse = 1.2345;
            armMotor = addDevice ("Arm Motor", new Spark(0));
            Encoder armEncoder = new Encoder(6, 7, false, EncodingType.k4X);
            beginningPosition = armEncoder.getDistance() * distancePerPulse;
            armEncoderPosition = () -> armEncoder.getDistance() * distancePerPulse - beginningPosition;
            armEncoderVelocity = () -> armEncoder.getRate() * distancePerPulse;
        }

        armMotor.setInverted(true);

        // Use these to get going:
        // setSetpoint() -  Sets where the PID controller should move the system
        //                  to
        // enable() - Enables the PID controller.
    }

    public final Logging.LoggingContext loggingContext = new Logging.LoggingContext(Logging.Subsystems.ARM) {

		@Override
		protected void addAll() {
            add("Arm Encoder Height", getArmEncoderHeight());
            add("Arm Potentiometer Height", getArmPotHeight());
            add("Arm Potentiometer Angle", getArmAngleDegrees());
            add("Arm Speed", armMotor.get());
		}
    	
    };

    @Override
    public void initSendable(SendableBuilder builder) {
        super.initSendable(builder);
        builder.addDoubleProperty("Pot Height", this::getArmPotHeight, null);
        builder.addDoubleProperty("Angle", this::getArmAngleDegrees, null);
        builder.addDoubleProperty("Encoder Height", this::getArmEncoderHeight, null);
    }
            
    @Override
    public void initDefaultCommand() {
        setDefaultCommand(new ArmControl());
    }

    @Override
    protected double returnPIDInput() {
        // Return your input value for the PID loop
        // e.g. a sensor, like a potentiometer:
        //return getArmAngleRadians();
        return armEncoderPosition.getAsDouble();
    }

    @Override
    protected void usePIDOutput(double output) {
        // Use output to drive your system, like a motor
        // e.g. yourMotor.set(output);

        armMotor.pidWrite(output);
    }

    public void setArmDesiredHeight(double height) {
        //setSetpoint(angleFromHeight(height));
        setSetpoint(height);
    }

    public double getArmPotHeight() {
        // From the pivot point to the plane of the hatch panel is about 44 inches
        return heightFromAngle(getArmAngleRadians());
    }

    public double getArmEncoderHeight(){
        if (armEncoderPosition.getAsDouble() <= -0.1) {
            return 0;
        } else {
            return armEncoderPosition.getAsDouble();
        }
    }

    /** Returns the approximate angle of the arm relative to horizontal, in radians. */
    private double getArmAngleRadians() {
        double value = armPotentiometer.getVoltage() / RobotController.getVoltage5V();//armPotentiometer.getVoltage() / RobotController.getVoltage5V();
        return 4.345 * (POTENTIOMETER_AT_HORIZONTAL - value) - Math.toRadians(64);
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
        return Math.abs(armEncoderVelocity.getAsDouble()) < .05;
    }

    public Double getArmRate(){
        return armEncoderVelocity.getAsDouble();
    }

    public void armToHeight(double setpoint){
        setArmMotorSpeed(1, setpoint);
    }

    public void setArmMotorSpeed(double speed, double setpoint) {
        // Make sure the motor doesn't move too fast when it's close to the top & bottom limits
        double min = getArmMotorMin(setpoint);
        double max = getArmMotorMax(setpoint);

        if (speed < min) {
            speed = min;
        }
        if (speed > max) {
            speed = max;
        }

        armMotor.set(speed);
    }

    private double getArmMotorMin(double setpoint) {

        double position = getArmEncoderHeight();

        if (setpoint < 10) {
            bottomLimit = setpoint;
            bottomThreshold = bottomLimit + 10;
        }

        double min = -1;
        
        if (position <= bottomLimit) {
            min = 0.3;
        } else if (position <= bottomThreshold) {
            min = -(.3 + (.7 * (position - bottomLimit) / (bottomThreshold - bottomLimit)));
        }
        
        return -min;
    }

    private double getArmMotorMax(double setpoint) {

        double position = getArmPotHeight();

        if (setpoint > 100) {
            topLimit = setpoint;
            topThreshold = topLimit - 10;
        }

        double max = 1;

        if (position >= topLimit) {
            max = -0.3;
        } else if (position >= topThreshold) {
            max = 1 - (.7 * (position - topThreshold) / (topLimit - topThreshold));
        }

        return -max;
    }

    public void resetArmEncoder() {
        //armEncoder.reset();

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

    /*public WristState getWristState(){
        if (wristSolenoid.get()){
            return WristState.DOWN;
        }
        else {
            return WristState.UP;
        }
    }*/

}
