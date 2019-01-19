// RobotBuilder Version: 2.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.


package org.frc2881;

import org.frc2881.commands.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.*;
import org.frc2881.subsystems.*;


/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
    //// CREATING BUTTONS
    // One type of button is a joystick button which is any button on a joystick.
    // You create one by telling it which joystick it's on and which button
    // number it is.
    // Joystick stick = new Joystick(port);
    // Button button = new JoystickButton(stick, buttonNumber);

    // There are a few additional built in buttons you can use. Additionally,
    // by subclassing Button you can create custom triggers and bind those to
    // commands the same as any other Button.

    //// TRIGGERING COMMANDS WITH BUTTONS
    // Once you have a button, it's trivial to bind it to a button in one of
    // three ways:

    // Start the command when the button is pressed and let it run the command
    // until it is finished as determined by it's isFinished method.
    // button.whenPressed(new ExampleCommand());

    // Run the command while the button is being held down and interrupt it once
    // the button is released.
    // button.whileHeld(new ExampleCommand());

    // Start the command when the button is released  and let it run the command
    // until it is finished as determined by it's isFinished method.
    // button.whenReleased(new ExampleCommand());


    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    public JoystickButton driverGreenTriangle;
    public JoystickButton driverBlueX;
    public JoystickButton driverPinkSquare;
    public JoystickButton driverRedCircle;
    public JoystickButton driverPOV;
    public JoystickButton driverOption;
    public JoystickButton driverRightBumper;
    public Joystick driverButtons;
    public JoystickButton driverTriggerRight;
    public JoystickButton driverTriggerLeft;
    public JoystickButton driverLeftJoystick;
    public JoystickButton driverRightJoystick;
    public Joystick driverJoysticks;
    public Joystick manipulatorButtons;

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS

    public OI() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS

        manipulatorButtons = new Joystick(2);
        
        driverJoysticks = new Joystick(1);
        
        driverRightJoystick = new JoystickButton(driverJoysticks, 1);
        driverRightJoystick.whileHeld(new DriveWithJoysticks());
        driverLeftJoystick = new JoystickButton(driverJoysticks, 1);
        driverLeftJoystick.whileHeld(new DriveWithJoysticks());
        driverTriggerLeft = new JoystickButton(driverJoysticks, 1);
        driverTriggerLeft.whileHeld(new ArmControl());
        driverTriggerRight = new JoystickButton(driverJoysticks, 1);
        driverTriggerRight.whileHeld(new LiftControl());
        driverButtons = new Joystick(0);
        
        driverRightBumper = new JoystickButton(driverButtons, 1);
        driverRightBumper.whenPressed(new CameraSwitch());
        driverOption = new JoystickButton(driverButtons, 1);
        driverOption.whileHeld(new LiftSetScrew());
        driverPOV = new JoystickButton(driverButtons, 1);
        driverPOV.whileHeld(new LiftToHeight());
        driverRedCircle = new JoystickButton(driverButtons, 1);
        driverRedCircle.whenPressed(new LiftPin());
        driverPinkSquare = new JoystickButton(driverButtons, 1);
        driverPinkSquare.whenPressed(new LiftCrawler());
        driverBlueX = new JoystickButton(driverButtons, 1);
        driverBlueX.whenPressed(new IntakeSetAsBack());
        driverGreenTriangle = new JoystickButton(driverButtons, 1);
        driverGreenTriangle.whenPressed(new IntakeSetAsFront());


        // SmartDashboard Buttons
        SmartDashboard.putData("Autonomous Command", new AutonomousCommand());
        SmartDashboard.putData("Drive With Joysticks", new DriveWithJoysticks());
        SmartDashboard.putData("Arm Calibrate Encoder", new ArmCalibrateEncoder());
        SmartDashboard.putData("Arm Control", new ArmControl());
        SmartDashboard.putData("Arm To Height", new ArmToHeight());
        SmartDashboard.putData("Cargo Control Rollers", new CargoControlRollers());
        SmartDashboard.putData("Cargo Loaded", new CargoLoaded());
        SmartDashboard.putData("Cargo Set Rollers", new CargoSetRollers());
        SmartDashboard.putData("Cargo Intake", new CargoIntake());
        SmartDashboard.putData("Do Nothing", new DoNothing());
        SmartDashboard.putData("Drive Forward", new DriveForward());
        SmartDashboard.putData("HP Loaded", new HPLoaded());
        SmartDashboard.putData("HP Set Rollers", new HPSetRollers());
        SmartDashboard.putData("HP Control Rollers", new HPControlRollers());
        SmartDashboard.putData("HP Intake Human", new HPIntakeHuman());
        SmartDashboard.putData("HP Intake Ground", new HPIntakeGround());
        SmartDashboard.putData("Lift To Height", new LiftToHeight());
        SmartDashboard.putData("Lift Control", new LiftControl());
        SmartDashboard.putData("Lift Pin", new LiftPin());
        SmartDashboard.putData("Lift Set Screw", new LiftSetScrew());
        SmartDashboard.putData("Lift Crawler", new LiftCrawler());
        SmartDashboard.putData("Robot Prep", new RobotPrep());
        SmartDashboard.putData("NavX Reset", new NavXReset());
        SmartDashboard.putData("Rumble Driver", new RumbleDriver());
        SmartDashboard.putData("Rumble Joysticks", new RumbleJoysticks());
        SmartDashboard.putData("Rumble Yes", new RumbleYes());
        SmartDashboard.putData("Rumble No", new RumbleNo());
        SmartDashboard.putData("Intake Set As Back", new IntakeSetAsBack());
        SmartDashboard.putData("Intake Set As Front", new IntakeSetAsFront());
        SmartDashboard.putData("TWINKLES", new TWINKLES());
        SmartDashboard.putData("Wait Forever", new WaitForever());
        SmartDashboard.putData("Wait For Pressure", new WaitForPressure());
        SmartDashboard.putData("Wait Until HP Detected", new WaitUntilHPDetected());
        SmartDashboard.putData("Wait Until Cargo Detected", new WaitUntilCargoDetected());
        SmartDashboard.putData("Wait Until NavX Detected", new WaitUntilNavXDetected());
        SmartDashboard.putData("Camera Switch", new CameraSwitch());

        // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
    }

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=FUNCTIONS
    public Joystick getDriverButtons() {
        return driverButtons;
    }

    public Joystick getDriverJoysticks() {
        return driverJoysticks;
    }

    public Joystick getManipulatorButtons() {
        return manipulatorButtons;
    }


    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=FUNCTIONS
}

