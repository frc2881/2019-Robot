package org.frc2881.commands.basic.drive;

import org.frc2881.Robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveWithJoysticks extends Command {

    public DriveWithJoysticks() {
        requires(Robot.drive);
    }

    @Override
    protected void initialize() {
        Robot.logInitialize(this);
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        //if the joysticks move, it moves the corrisponding side of the robot \/
        if (Math.abs(Robot.oi.driver.getY(GenericHID.Hand.kLeft)) < 0.06 &&
                Math.abs(Robot.oi.driver.getY(GenericHID.Hand.kRight)) < 0.06) {
            Robot.drive.getLeftDistance();
            Robot.drive.getRightDistance();
        }
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        Robot.logEnd(this);
    }

    @Override
    protected void interrupted() {
        Robot.logInterrupted(this);
    }
}
