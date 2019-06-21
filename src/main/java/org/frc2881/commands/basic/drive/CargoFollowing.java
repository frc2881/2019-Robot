package org.frc2881.commands.basic.drive;

import org.frc2881.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class CargoFollowing extends Command {

    @Override
    protected void initialize() {
        Robot.logInitialize(this);
        Robot.log("Turn to POV has started: " + angle);
        Robot.driveSubsystem.initializeTurnToHeading(angle);
        //int angle = getCargoY(

        );  
        
    }

    private int getCargoY() {
        return 0;
    }

    @Override
    protected void execute() {         
        //Calls to the subsystem to update the angle if controller value has changed
        double rotateToAngleRate = Robot.drive.getRotateToAngleRate();
        Robot.drive.cargoDanceRotate(rotateToAngleRate, -rotateToAngleRate);
        Robot.drive.changeHeadingTurnToHeading(getDriverPOVAngle());

        
       // double rotateToAngleRate = utils.NTValues;
    }

    @Override
    protected boolean isFinished() {
        return false;
    }


}