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

import org.frc2881.utils.frc4048.Logging;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Lift extends Subsystem {

    /* LIFT SEQUENCE:
        1. Lower wrist
        2. lock wrist & drop intake
        3. raise arm ~50%
        4. drive to platform (hit bumpers)
        5. lower arm to platform
            amp monitor?
        6. raise rear until front wheels touch (angle TBD ~10º?)
        7. raise to full extension
        8. drive forward
        9. raise rear leg*/
    
    public static double HAB_TWO_HEIGHT = 1;
    public static double HAB_THREE_HEIGHT = 2;

    private final PowerDistributionPanel pdp = new PowerDistributionPanel(10);
    private Encoder liftEncoderLeft;
    private Encoder liftEncoderRight;
    private SpeedControllerGroup liftMotors;
    private Spark liftMotorLeft;
    private Spark liftMotorRight;

    // Initialize your subsystem here
    public Lift() {

        liftEncoderLeft = new Encoder(0, 1, false, EncodingType.k4X);
        addChild("Lift Encoder Left",liftEncoderLeft);
        liftEncoderLeft.setDistancePerPulse(1.0);
        
        liftEncoderRight = new Encoder(2, 3, false, EncodingType.k4X);
        addChild("Lift Encoder Right",liftEncoderRight);
        liftEncoderRight.setDistancePerPulse(1.0);

        liftMotorLeft = new Spark(2);
        addChild("Lift Motor Left",liftMotorLeft);
        
        liftMotorRight = new Spark(1);
        addChild("Lift Motor Right",liftMotorRight);

        liftMotors = new SpeedControllerGroup(liftMotorLeft, liftMotorRight);
        addChild("Lift Motors",liftMotors);
        liftMotors.setInverted(false);


        // Use these to get going:
        // setSetpoint() -  Sets where the PID controller should move the system
        //                  to
        // enable() - Enables the PID controller.
    }

    public final Logging.LoggingContext loggingContext = new Logging.LoggingContext(Logging.Subsystems.INTAKE) {

        @Override
        protected void addAll() {
            
        }
    };

    public void setLiftLeft(double speed){
        liftMotorLeft.setSpeed(speed);
    }
    
    public void setLiftRight(double speed){
        liftMotorRight.setSpeed(speed);
    }

    public void setLiftMotors(double speed) {
        liftMotors.set(speed);
    }

    public double getLiftMotorCurrent(){
        return Math.max(pdp.getCurrent(2), pdp.getCurrent(1)) ;
    }

    @Override
    protected void initDefaultCommand() {

    }
}
